package com.kelan.core.util;

import java.lang.reflect.Field;

public class EntityNoNull {
	public static Object getNoNull(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		Field[] superFields = obj.getClass().getSuperclass().getDeclaredFields();
		Field[] addFields = JsonUtils.concatAll(fields, superFields);
		for (Field field : addFields) {
			try {
				@SuppressWarnings("rawtypes")
				Class[] parameterTypes = new Class[1];
				parameterTypes[0] = field.getType();
				if (parameterTypes[0].toString().indexOf("String") != -1) {
					field.setAccessible(true);
					if (field.get(obj) != null) {
						continue;
					}
					field.set(obj, "");
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return obj;
    }
}
