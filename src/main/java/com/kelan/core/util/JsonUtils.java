package com.kelan.core.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.SerializationUtils;

import net.sf.json.JSONArray;

/**
 * Created by Administrator on 2016/10/25.
 */
public class JsonUtils {
	/**
	 * 将Javabean转换为Map
	 *
	 * @param javaBean
	 *            javaBean
	 * @return Map对象
	 */
	private static Logger log = Logger.getLogger(JsonUtils.class);

	public static Map toMap(Object javaBean) {

		Map result = new HashMap();
		Method[] methods = javaBean.getClass().getDeclaredMethods();

		for (Method method : methods) {

			try {

				if (method.getName().startsWith("get")) {

					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);

					Object value = method.invoke(javaBean, (Object[]) null);
					result.put(field, null == value ? "" : value.toString());

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return result;

	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, String> beanToMap(Object obj) {
		Map<String, String> params = new HashMap<String, String>(0);
		try {
			obj = EntityNoNull.getNoNull(obj);
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
			for (int i = 0; i < descriptors.length; i++) {
				String key = descriptors[i].getName();
				Object value = propertyUtilsBean.getNestedProperty(obj, key);
				if (value instanceof List) {
					@SuppressWarnings("unchecked")
					List<Object> objList = (List<Object>) value;
					params.put(key, SerializationUtils.serialize(objList).toString());
					continue;
				}
				if (!"class".equals(key) && value != null) {
					params.put(key, value.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	/**
	 * 将Json对象转换成Map
	 *
	 * json对象
	 * 
	 * @return Map对象
	 * @throws JSONException
	 */
	public static Map toMap(String jsonString) throws JSONException {

		JSONObject jsonObject = new JSONObject(jsonString);

		Map result = new HashMap();
		Iterator iterator = jsonObject.keys();
		String key = null;
		String value = null;

		while (iterator.hasNext()) {

			key = (String) iterator.next();
			value = jsonObject.getString(key);
			result.put(key, value);

		}
		return result;

	}

	/**
	 * 将JavaBean转换成JSONObject（通过Map中转）
	 *
	 * @param bean
	 *            javaBean
	 * @return json对象
	 */
	public static JSONObject toJSON(Object bean) {

		return new JSONObject(toMap(bean));

	}

	/**
	 * @author WYX
	 * 
	 *         将Map转换成Javabean 修复了继承自父类中的保护修饰的属性无法赋值的问题，推荐使用
	 * @param javabean
	 *            javaBean
	 * @param data
	 *            Map数据
	 */
	public static Object toJavaBean(Map data, Object javabean) {

		Method[] methods = javabean.getClass().getDeclaredMethods();
		Method[] superMethods = javabean.getClass().getSuperclass().getDeclaredMethods();
		Method[] addMethods = concatAll(methods, superMethods);
		for (Method method : addMethods) {
			try {
				if (method.getName().startsWith("set")) {
					String field = method.getName();
					field = field.substring(field.indexOf("set") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);
					method.invoke(javabean, new Object[] { data.get(field) });
				}
			} catch (Exception e) {

			}
		}
		return javabean;

	}

	/**
	 * 多个java数组合并成一个数组
	 * 
	 * @param first
	 * @param rest
	 * @return
	 */
	public static <T> T[] concatAll(T[] first, T[]... rest) {
		int totalLength = first.length;
		for (T[] array : rest) {
			totalLength += array.length;
		}
		T[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (T[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}

	/**
	 * 两个java数组合并成一个数组
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	public static Object mapToJavaBean(Object obj, Map data) {
		// 返回Class中所有的字段，包括私有字段
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			String[] fStr = field.toString().split("[.]");
			try {
				if (data.get(fStr[fStr.length - 1]) == null) {
					continue;
				}
				if ("int".matches(field.getGenericType().toString())) {
					field.setInt(obj, Integer.parseInt((String) data.get(data.get(fStr[fStr.length - 1]))));
				}
				field.set(obj, (String) data.get(data.get(fStr[fStr.length - 1])));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return obj;

	}

	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
		if (map == null)
			return null;

		Object obj = beanClass.newInstance();

		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			Method setter = property.getWriteMethod();
			if (setter != null) {
				setter.invoke(obj, map.get(property.getName()));
			}
		}

		return obj;
	}

	/**
	 * JSONObject到JavaBean
	 *
	 * javaBean
	 * 
	 * @return json对象
	 * @throws ParseException
	 *             json解析异常
	 * @throws JSONException
	 */
	public static Object toJavaBean(Object javabean, String jsonString) throws ParseException, JSONException {

		JSONObject jsonObject = new JSONObject(jsonString);

		Map map = toMap(jsonObject.toString());

		return toJavaBean(map, javabean);

	}

	/**
	 * @author 王骏 根据json 数组字符串创建对象
	 *         [{"coordinate_y":"110.32792437837","coordinate_x":"20.038902747077","pType":"1"},
	 *         {"coordinate_y":"110.32792437837","coordinate_x":"20.038902747077","pType":"1"},
	 *         {"coordinate_y":"110.32792437837","coordinate_x":"20.038902747077","pType":"1"},
	 *         {"coordinate_y":"110.32792437837","coordinate_x":"20.038902747077","pType":"1"},
	 *         {"coordinate_y":"110.32792437837","coordinate_x":"20.038902747077","pType":"1"}]
	 * @param targetStr
	 * @param clazz
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static <T> List<T> getObjectFromJsonArray(String targetStr, Class<T> clazz)
			throws InstantiationException, IllegalAccessException {

		JSONArray jsonArr = JSONArray.fromObject(targetStr);
		Field[] fields = clazz.getDeclaredFields();
		List<T> result = new ArrayList<>();
		try {
			if (jsonArr.size() <= 0) {
				return null;
			}

			for (int i = 0; i < jsonArr.size(); i++) {
				T t = clazz.newInstance();
				for (Field field : fields) {
					if (jsonArr.getJSONObject(i).containsKey(field.getName())) {
						field.setAccessible(true);
						field.set(t, jsonArr.getJSONObject(i).getString(field.getName()));
					}
				}
				result.add(t);
			}
		} catch (Exception e) {
			log.error(e);
			return null;
		}
		return result;
	}

	public static <T> List<T> objectFromJsonArray(String targetStr, Class<T> clazz)
			throws InstantiationException, IllegalAccessException {
		Method[] methods = clazz.getDeclaredMethods();
		Method[] superMethods = clazz.getSuperclass().getDeclaredMethods();
		Method[] addMethods = concatAll(methods, superMethods);
		JSONArray jsonArr = JSONArray.fromObject(targetStr);
		// Field[] fields = object.getClass().getDeclaredFields();
		List<T> result = new ArrayList<>();
		try {
			if (jsonArr.size() <= 0) {
				return null;
			}
			for (int i = 0; i < jsonArr.size(); i++) {
				T t = clazz.newInstance();
				for (Method method : addMethods) {
					if (method.getName().startsWith("set")) {
						String field = method.getName();
						field = field.substring(field.indexOf("set") + 3);
						field = field.toLowerCase().charAt(0) + field.substring(1);
						if (jsonArr.getJSONObject(i).get(field)==null){
							continue;
						}
						method.invoke(t, new Object[] { jsonArr.getJSONObject(i).get(field) });
					}
				}
				result.add(t);
			}
		} catch (Exception e) {
			log.error(e);
			return null;
		}
		return result;
	}

	/**
	 * 将map.toString后的串反转成map
	 * 
	 * @param singInfo
	 * @return
	 */
	public static Map<String, String> MapforStrMap(String singInfo) {
		String str1 = singInfo.replaceAll("\\{|\\}", "");// singInfo是一个map
															// toString后的字符串。
		String str2 = str1.replaceAll(" ", "");
		String str3 = str2.replaceAll(",", "&");

		Map<String, String> map = null;
		if ((null != str3) && (!"".equals(str3.trim()))) {
			String[] resArray = str3.split("&");
			if (0 != resArray.length) {
				map = new HashMap(resArray.length);
				for (String arrayStr : resArray) {
					if ((null != arrayStr) && (!"".equals(arrayStr.trim()))) {
						int index = arrayStr.indexOf("=");
						if (-1 != index) {
							map.put(arrayStr.substring(0, index), arrayStr.substring(index + 1));
						}
					}
				}
			}
		}

		return map;
	}
}
