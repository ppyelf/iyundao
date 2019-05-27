package com.ayundao.base.utils;

import org.apache.commons.beanutils.converters.AbstractConverter;
import org.springframework.util.Assert;

/**
 * @ClassName: EnumUtils
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/13 16:24
 * @Description: 枚举类型转换
 * @Version: V1.0
 */
public class EnumUtils extends AbstractConverter {

	/**
	 * 默认类型
	 */
	private Class<?> defaultType;

	/**
	 * 构造方法
	 * 
	 * @param defaultType
	 *            默认类型
	 */
	public EnumUtils(Class<?> defaultType) {
		Assert.notNull(defaultType);

		this.defaultType = defaultType;
	}

	/**
	 * 类型转换
	 * 
	 * @param type
	 *            类型
	 * @param value
	 *            值
	 * @return 对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
		return (T) Enum.valueOf((Class<Enum>) type, value.toString());
	}

	/**
	 * 获取默认类型
	 * 
	 * @return 默认类型
	 */
	@Override
	protected Class<?> getDefaultType() {
		return defaultType;
	}

}