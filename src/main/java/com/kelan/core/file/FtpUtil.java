package com.kelan.core.file;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Apache commons-net 试用一把，看看FTP客户端工具做的好用不
 *
 * @author : leizhimin，2008-8-20 14:00:38。
 *         <p>
 */
public class FtpUtil {
	public static void main(String[] args) {
		testUpload();
		//testDownload();
	}

	/**
	 * FTP上传单个文件测试
	 */
	public static void testUpload() {
		FTPClient ftpClient = new FTPClient();
		FileInputStream fis = null;

		try {
			ftpClient.connect("192.168.0.135", 21);
			ftpClient.login("administrator", "123456");

			File srcFile = new File("F:\\Docs\\catalina.2015-12-25.log");
			fis = new FileInputStream(srcFile);
			// 设置上传目录
			ftpClient.changeWorkingDirectory("D:/ftptest/log");
			ftpClient.setBufferSize(1024);
			ftpClient.setControlEncoding("GBK");
			// 设置文件类型（二进制）
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.storeFile("catalina.2015-12-25.log", fis);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("FTP客户端出错！", e);
		} finally {
			IOUtils.closeQuietly(fis);
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}
	}

	/**
	 * FTP下载单个文件测试
	 */
	public static void testDownload() {
		FTPClient ftpClient = new FTPClient();
		FileOutputStream fos = null;

		try {
			ftpClient.connect("192.168.14.117");
			ftpClient.login("administrator", "123456");

			String remoteFileName = "/admin/pic/3.gif";
			fos = new FileOutputStream("c:/down.gif");

			ftpClient.setBufferSize(1024);
			// 设置文件类型（二进制）
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.retrieveFile(remoteFileName, fos);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("FTP客户端出错！", e);
		} finally {
			IOUtils.closeQuietly(fos);
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("关闭FTP连接发生异常！", e);
			}
		}
	}
}