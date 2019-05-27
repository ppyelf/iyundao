package com.ayundao.base.utils;

/**
 * @ClassName: CommonUtils
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/13 16:24
 * @Description: 公共参数
 * @Version: V1.0
 */
public final class CommonUtils {

	/**
	 * 日期格式配比
	 */
	public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

	/**
	 * xml文件路径
	 */
	public static final String XML_PATH = "/.xml";

	/**
	 * .properties文件路径
	 */
	public static final String PROPERTIES_PATH = "/.properties";

	/**
	 * 不可实例化
	 */
	private CommonUtils() {
	}

}