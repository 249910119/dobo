package com.dobo.common.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * java对象反射bean
 *
 */
public class ReflectHelper {

	/**
	 * 判断属性是否为BigDecimal类型
	 * 
	 * @param clazz 数据类型
	 * @param fieldName 属性名
	 * @return 如果为BigDecimal类型返回true，否则返回false
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	public static <T> String getFieldType(Class<T> clazz, String fieldName) throws NoSuchFieldException, SecurityException {
		Field field = clazz.getDeclaredField(fieldName);
		return field.getGenericType().toString().substring(6);
	}

	/**
	 * 反射调用指定构造方法创建对象
	 * 
	 * @param clazz 对象类型
	 * @param argTypes 参数类型
	 * @param args 构造参数
	 * @return 返回构造后的对象
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * 
	 */
	public static <T> T invokeConstructor(Class<T> clazz, Class<?>[] argTypes,
			Object[] args) throws NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Constructor<T> constructor = clazz.getConstructor(argTypes);
		return constructor.newInstance(args);
	}
	
	/**
	 * javabean是否包含某属性
	 * @param bean
	 * @param propertyName
	 * @return boolean
	 */
	public static boolean containProperty(Object bean, String propertyName) {
		try {
			if (PropertyUtils.getPropertyDescriptor(bean, propertyName) == null)
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 返回javabean中某属性的Field对象
	 * @param obj
	 * @param fieldName
	 * @return Field
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class;) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException localNoSuchFieldException) {
				superClass = superClass.getSuperclass();
			}
		}
		return null;
	}
	
	/**
	 * 返回javabean所有属性字段名
	 * @param obj
	 * @return List<String>
	 */
	public static List<String> getFieldNames(Object obj) {
		List<String> fields = new ArrayList<String>();
		for (Class<?> superClass = obj.getClass(); superClass != Object.class;) {
			try {
				Field[] field = superClass.getDeclaredFields();
				for(Field f : field) {
					fields.add(f.getName());
				}
				return fields;
			} catch (SecurityException e) {
				return fields;
			}
		}
		return fields;
	}

	/**
	 * 返回javabean某属性对应的值
	 * @param obj
	 * @param fieldName
	 * @return Object
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getValueByFieldName(Object obj, String fieldName)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = getFieldByFieldName(obj, fieldName);
		Object value = null;
		if (field != null) {
			if (field.isAccessible()) {
				value = field.get(obj);
			} else {
				field.setAccessible(true);
				value = field.get(obj);
				field.setAccessible(false);
			}
		}
		return value;
	}

	/**
	 * 保存javabean中某属性对应的值
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setValueByFieldName(Object obj, String fieldName, Object value) 
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = getFieldByFieldName(obj, fieldName);
		if (field.isAccessible()) {
			field.set(obj, value);
		} else {
			field.setAccessible(true);
			field.set(obj, value);
			field.setAccessible(false);
		}
	}

	public static void main(String[] args) {
		/*try {
			String[] fieldNames = {"businsortId","businsortName","seq","modifyDate","modifyStaff","remark"};
			Class<?>[] fieldTypes = new Class<?>[fieldNames.length];
			for(int i=0;i<fieldNames.length;i++){
				Field fd = ReflectHelper.getFieldByFieldName(new BusinTypeSort(), fieldNames[i]);
				fieldTypes[i] = fd.getType();
			}
			
			BusinTypeSort user1 = ReflectHelper.invokeConstructor(BusinTypeSort.class,
					fieldTypes, 
					new Object[]{"BusinTypeSort", "BusinTypeSort", BigDecimal.valueOf(123), new Date(), "admin", "remark"}
			);
			
			BusinTypeSort user = ReflectHelper.invokeConstructor(BusinTypeSort.class,
					new Class<?>[]{String.class, String.class, BigDecimal.class, Date.class, String.class, String.class}, 
					new Object[]{"BusinTypeSort", "BusinTypeSort", BigDecimal.valueOf(123), new Date(), "admin", "remark"}
			);
			
			Field f = ReflectHelper.getFieldByFieldName(user, "businsortName");
			System.out.println(f.getType().getName());
			ReflectHelper.setValueByFieldName(user, "businsortName", "asdf");
			System.out.println(ReflectHelper.getValueByFieldName(user, "businsortName"));
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
