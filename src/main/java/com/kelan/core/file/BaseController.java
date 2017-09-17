package com.kelan.core.file;

import com.kelan.core.util.StringUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 基础控制器
 * 
 * @author
 * 
 */
@Controller
public class BaseController {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	public final static String UPLOAD_ROOT_PATH = "/static/upload/";// 文件存放地址
	public final static String ROOT_PATH = "/static/";// 文件跟地址
	public final static String UPLOAD_PATH = "/upload/";// 上传文件存放地址
	public final static String DOWNLOAD_ROOT_PATH = "/static/download/";// 文件存放地址
	public final static double MAX_SIZE = 100.00; // 图片允许最大空间 单位：KB
	public final static double SCALE = 0.25f; // 压缩比例 范围：0.0~1.0，1为最高质量
	public final static double QUALITY = 0.25f; // 输出的图片质量，范围：0.0~1.0，1为最高质量
	public final static String MINI_NAME = "m";//缩小图前缀
	public final static String BMP = "bmp";
	public final static String PNG = "png";
	public final static String JPEG = "jpeg";
	public final static String JPG = "jpg";

	/**
	 * 单个图片文件上传，若文件大小超过允许最大大小，则生成压缩图片（压缩比例默认0.25，图片质量默认0.25）
	 * 
	 * @param file
	 *            上传图片文件
	 * @return
	 */
	protected ImageInfo uploadImage(MultipartFile file, String realPath) {
		return uploadImage(file, SCALE, QUALITY, realPath,null);
	}

	protected ImageInfo uploadTypeImage(MultipartFile file, String realPath,String type) {
		return uploadImage(file, SCALE, QUALITY, realPath,type);
	}

	/**
	 * 单个图片文件上传，若文件大小超过允许最大大小，则生成压缩图片
	 * 
	 * @param file
	 *            上传图片文件
	 * @param scale
	 *            压缩比例 范围：0.0~1.0，1为最高质量
	 * @param quality
	 *            图片质量 输出的图片质量，范围：0.0~1.0，1为最高质量
	 * @return
	 */
	protected ImageInfo uploadImage(MultipartFile file, double scale, double quality, String realPath,String type) {
		ImageInfo imageInfo = new ImageInfo(); // 图片信息
		BufferedImage uploadBuff = null; // 图片缓冲对象
		BufferedImage newImg = null; // 重绘图片
		BufferedImage cutImg = null; // 缩略图片
		InputStream is = null; // 文件输入流
		String filePath; // 图片存放相对路径（用于返回图片存放路径信息）
		String fileName; // 文件名
		String fileType; // 文件类型
		long fileSize; // 文件大小
		int width; // 文件宽度
		int height; // 文件高度
		imageInfo.setIsError("1"); // 是否上传失败[0:否][1:是]

		// 判断文件是否为空
		if (file == null || file.isEmpty() || file.getSize() <= 0) {
			imageInfo.setMsg("非法请求，文件不存在！"); // 上传失败消息
			return imageInfo;
		}

		// 获取图片信息
		fileName = file.getOriginalFilename();
		fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
		fileSize = file.getSize() / 1024;
		imageInfo.setImgName(fileName);

		// 校验是否指定类型的图片文件
		if (!StringUtil.isImage(fileName.toLowerCase(), true)) {
			imageInfo.setMsg("非法请求，请上传jpeg、jpg、bmp或png类型的图片文件！"); // 上传失败消息
			return imageInfo;
		}
		/**
		 * 校验魔术数字
		 */
		try {
			if (!validateMagicNumber(file.getInputStream(), fileType)) {
				imageInfo.setMsg("非法请求，请上传正确的图片文件！"); // 上传失败消息
				return imageInfo;
			}
		} catch (IOException e) {
			e.printStackTrace();
			imageInfo.setMsg("操作失败，请重试！"); // 上传失败消息
			return imageInfo;
		}

		/**
		 * 通过是否能获取该文件的宽高，来判断当前文件类型是否是图片
		 */
		try {
			is = file.getInputStream();
			uploadBuff = ImageIO.read(is);
			if (uploadBuff == null) {
				is.close(); // 关闭输入流
				imageInfo.setMsg("文件读取失败，请重试！"); // 上传失败消息
				return imageInfo;
			}
			width = uploadBuff.getWidth(null);
			height = uploadBuff.getHeight(null);
			is.close(); // 关闭输入流
			if (width <= 0 || height <= 0) {
				imageInfo.setMsg("非法请求，请上传正确的图片文件！"); // 上传失败消息
				return imageInfo;
			}
		} catch (IOException e) {
			e.printStackTrace();
			imageInfo.setMsg("操作失败，请重试！"); // 上传失败消息
			if (uploadBuff != null) {
				uploadBuff.flush();
				uploadBuff = null;
			}
			return imageInfo;
		}

		// 获取当前时间
		Calendar calendar = Calendar.getInstance();
		// 设置图片存放相对路径（用于返回图片存放路径信息）

		if(!com.kelan.core.util.StringUtils.isEmpty(type)){
			filePath = new StringBuffer(ROOT_PATH).append(type).append(UPLOAD_PATH).append(calendar.get(Calendar.YEAR)).append("/").append((calendar.get(Calendar.MONTH) + 1)).append("/").append(calendar.get(Calendar.DATE))
					.append("/").toString();
		}else{
			filePath = new StringBuffer(UPLOAD_ROOT_PATH).append(calendar.get(Calendar.YEAR)).append("/").append((calendar.get(Calendar.MONTH) + 1)).append("/").append(calendar.get(Calendar.DATE))
					.append("/").toString();
		}
		// 设置图片存放路径
		realPath = new StringBuffer(realPath).append("/").append(filePath).toString();

		try {
			/**
			 * 对图片文件进行resize
			 */
			// 重绘图片
			newImg = Thumbnails.of(uploadBuff).size(width, height).asBufferedImage();
			// 生成新图片文件
			fileName = new StringBuffer("").append(new Date().getTime()).append(".").append(fileType).toString();
			File newFile = new File(realPath, fileName);
			if (!newFile.exists()) {
				newFile.mkdirs();
			}
			// 写入图片
			ImageIO.write(newImg, fileType, newFile);
			imageInfo.setFileName(fileName);
			imageInfo.setFilePath(filePath);
			imageInfo.setImgPath(filePath + fileName);
			imageInfo.setMiniImgPath(filePath + fileName);
			System.out.println(newFile.getCanonicalPath());

			/**
			 * 判断是否生成缩略图
			 */
			if (fileSize > MAX_SIZE) {
				if (scale > 1 || scale <= 0) {
					scale = SCALE;
				}
				if (quality > 1 || quality <= 0) {
					quality = QUALITY;
				}
				// 重绘图片
				cutImg = Thumbnails.of(uploadBuff).scale(scale).outputQuality(quality).asBufferedImage();
				// 生成新图片文件
				//fileName = new StringBuffer("").append(new Date().getTime()).append(".").append(fileType).toString();
				fileName = MINI_NAME+fileName;
				File cutFile = new File(realPath, fileName);
				if (!cutFile.exists()) {
					cutFile.mkdirs();
				}
				// 写入图片
				ImageIO.write(cutImg, fileType, cutFile);// 输出到文件流
				imageInfo.setMiniImgPath(filePath + fileName);
				imageInfo.setIsCut("1");
				System.out.println(cutFile.getCanonicalPath());
			}
		} catch (IOException e) {
			e.printStackTrace();
			imageInfo.setMsg("操作失败，请重试！"); // 上传失败消息
			if (uploadBuff != null) {
				uploadBuff.flush();
				uploadBuff = null;
			}
			return imageInfo;
		} finally {
			if (uploadBuff != null) {
				uploadBuff.flush();
				uploadBuff = null;
			}
			if (cutImg != null) {
				cutImg.flush();
				cutImg = null;
			}
			if (newImg != null) {
				newImg.flush();
				newImg = null;
			}
		}

		imageInfo.setIsError("0");
		return imageInfo;
	}

	/**
	 * 校验魔术数字
	 * 
	 * @param is
	 *            输入流
	 * @param fileType
	 *            文件类型
	 * @return
	 */
	private boolean validateMagicNumber(InputStream is, String fileType) {
		try {
			StringBuilder stringBuilder = new StringBuilder();
			String magicNumber = null;
			byte[] bt = new byte[2];
			is.read(bt); // 读取2字节文件头
			is.close(); // 关闭输入流
			if (bt == null || bt.length <= 0) {
				return false;
			}
			for (int i = 0; i < bt.length; i++) {
				int v = bt[i] & 0xFF;
				String hv = Integer.toHexString(v);
				if (hv.length() < 2) {
					stringBuilder.append(0);
				}
				stringBuilder.append(hv);
			}
			magicNumber = stringBuilder.toString();
			if (!(JPEG.equals(fileType) && "ffd8".equals(magicNumber)) && !(JPG.equals(fileType) && "ffd8".equals(magicNumber)) && !(BMP.equals(fileType) && "424d".equals(magicNumber))
					&& !(PNG.equals(fileType) && "8950".equals(magicNumber))) {
				return false;
			}
			return true;
		} catch (IOException e) {
			logger.debug("输入流字节读取异常，魔术数字校验失败！");
			return false;
		}
	}
	/**
	 * 上传多个文件-返回成功上传存储的文件名 缩略图片
	 *
	 * @param request
	 * @return
	 */
	protected Map<String, Object> uploadFile(MultipartHttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		MultiValueMap<String, MultipartFile> multiValueMap = request.getMultiFileMap();
		String realPath = request.getSession().getServletContext().getRealPath("");
		List<ImageInfo> nameList = null;
		if ((null != multiValueMap) && !multiValueMap.isEmpty()) {
			nameList = new ArrayList<ImageInfo>();
			for (String key : multiValueMap.keySet()) {
				List<MultipartFile> fileList = multiValueMap.get(key);
				if ((null != fileList) && (fileList.size() > 0)) {
					for (int i = 0; i < fileList.size(); i++) {
						MultipartFile file = fileList.get(i);
						if (file != null && file.getSize() > 0) {
							ImageInfo imageInfo = uploadImage(file, realPath);
							if (imageInfo == null) {
								resultMap.put("isError", "1");
								resultMap.put("msg", "操作失败，请重试！");
								return resultMap;
							}
							if (!"0".equals(imageInfo.getIsError())) {
								resultMap.put("isError", imageInfo.getIsError());
								resultMap.put("msg", imageInfo.getMsg());
								return resultMap;
							}
							nameList.add(imageInfo);
						}
					}
				}
			}
		}
		resultMap.put("nameList", nameList);
		resultMap.put("isError", "0");
		return resultMap;
	}

	/**
	 * 上传文件按key分类
	 * @param request
	 * @return
     */
	protected Map<String, Object> uploadFileKey(MultipartHttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		MultiValueMap<String, MultipartFile> multiValueMap = request.getMultiFileMap();
		String realPath = request.getSession().getServletContext().getRealPath("");
		List<ImageInfo> nameList = null;
		if ((null != multiValueMap) && !multiValueMap.isEmpty()) {
			for (String key : multiValueMap.keySet()) {
				List<MultipartFile> fileList = multiValueMap.get(key);
				nameList = new ArrayList<ImageInfo>();
				if ((null != fileList) && (fileList.size() > 0)) {
					for (int i = 0; i < fileList.size(); i++) {
						MultipartFile file = fileList.get(i);
						if (file != null && file.getSize() > 0) {
							ImageInfo imageInfo = uploadImage(file, realPath);
							imageInfo.setKeyName(key);//用于图片划分
							if (imageInfo == null) {
								resultMap.put("isError", "1");
								resultMap.put("msg", "操作失败，请重试！");
								return resultMap;
							}
							if (!"0".equals(imageInfo.getIsError())) {
								resultMap.put("isError", imageInfo.getIsError());
								resultMap.put("msg", imageInfo.getMsg());
								return resultMap;
							}
							nameList.add(imageInfo);
						}
					}
				}
				resultMap.put(key, nameList);
			}
		}
		resultMap.put("isError", "0");
		return resultMap;
	}

	/**
	 * 上传单个文件-返回成功上传存储的文件名 缩略图片
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	protected Map<String, String> uploadFile(MultipartFile file, HttpServletRequest request) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String realPath = request.getSession().getServletContext().getRealPath("");
		String picUrl = "";
		if (file != null && file.getSize() > 0) {
			ImageInfo imageInfo = uploadImage(file, realPath);
			if (imageInfo == null) {
				resultMap.put("isError", "1");
				resultMap.put("msg", "操作失败，请重试！");
				return resultMap;
			}
			if (!"0".equals(imageInfo.getIsError())) {
				resultMap.put("isError", imageInfo.getIsError());
				resultMap.put("msg", imageInfo.getMsg());
				return resultMap;
			}
			picUrl = imageInfo.getImgPath();
		}
		
		resultMap.put("picUrl", picUrl);
		resultMap.put("isError", "0");
		return resultMap;
	}
	/**
	 * 上传文件-返回成功上传存储的文件路径
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	protected String uploadFileOne(MultipartFile file, HttpServletRequest request) {
		Calendar calendar = Calendar.getInstance();// 可以对每个时间域单独修改
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int date = calendar.get(Calendar.DATE);
		// 设置存储的相对目录
		String path = new StringBuffer("").append(UPLOAD_ROOT_PATH).append(year)
				.append("/").append(month).append("/").append(date).append("/").toString();
		String realPath = request.getSession().getServletContext().getRealPath(path);
		String fileName = file.getOriginalFilename();
		if (StringUtils.isNotEmpty(fileName)) {
			String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			fileName = new StringBuffer("").append(new Date().getTime()).append(fileType).toString();
			System.out.println(realPath);
			File targetFile = new File(realPath, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			// 保存文件
			try {
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new StringBuffer(path).append(fileName).toString();
	}
	/**
	 * 上传文件-返回成功上传存储的文件路径
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	protected String[] uploadFileNameNotChange(MultipartFile file, HttpServletRequest request) {
		Calendar calendar = Calendar.getInstance();// 可以对每个时间域单独修改
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int date = calendar.get(Calendar.DATE);
		// 设置存储的相对目录
		String path = new StringBuffer("").append(UPLOAD_ROOT_PATH).append(year)
				.append("/").append(month).append("/").append(date).append("/").toString();
		String realPath = request.getSession().getServletContext().getRealPath(path);
		String fileName = file.getOriginalFilename();
		String oldName = fileName;
		if (StringUtils.isNotEmpty(fileName)) {
			String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			fileName = new StringBuffer("").append(new Date().getTime()).append(fileType).toString();
			System.out.println(realPath);
			File targetFile = new File(realPath, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			// 保存文件
			try {
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String filePath = new StringBuffer(path).append(fileName).toString();
		String[] namearr = {oldName,filePath};
		return namearr;
	}

	protected boolean deleteFiles(List<String> filearr) {
		try {
			for (int i = 0; i < filearr.size(); i++) {
				File file = new File(filearr.get(i));
				file.delete();
				System.out.println(filearr.get(i) + "删除成功");
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
