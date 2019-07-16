package com.ayundao.base.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;
import java.util.Map;

/**
 * @ClassName: SpringUtils
 * @project: ayundao
 * @author: 念
 * @Date: 2019/5/13 16:24
 * @Description: Utils - Spring
 * @Version: V1.0
 */
public class SpringUtils implements ApplicationContextAware {

	/**
	 * ApplicationContext
	 */
	private static ApplicationContext applicationContext;

	/**
	 * 获取ApplicationContext
	 *
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取实例
	 *
	 * @param name
	 *            Bean名称
	 * @return 实例
	 */
	public static Object getBean(String name) {
		Assert.hasText(name, "");

		return applicationContext.getBean(name);
	}

	/**
	 * 获取实例
	 *
	 * @param name
	 *            Bean名称
	 * @param type
	 *            Bean类型
	 * @return 实例
	 */
	public static <T> T getBean(String name, Class<T> type) {
		Assert.hasText(name, "");
		Assert.notNull(type, "");

		return applicationContext.getBean(name, type);
	}

	/**
	 * 获取实例
	 *
	 * @param type
	 *            Bean类型
	 * @return 实例
	 */
	public static <T> Map<String, T> getBeansOfType(Class<T> type) {
		Assert.notNull(type, "");

		return applicationContext.getBeansOfType(type);
	}

	/**
	 * 获取国际化消息
	 *
	 * @param code
	 *            代码
	 * @param args
	 *            参数
	 * @return 国际化消息
	 */
	public static String getMessage(String code, Object... args) {
		Assert.hasText(code, "");

		LocaleResolver localeResolver = getBean("localeResolver", LocaleResolver.class);
		Locale locale = localeResolver.resolveLocale(null);
		return applicationContext.getMessage(code, args, locale);
	}

	/**
	 * 设置ApplicationContext
	 *
	 * @param applicationContext
	 *            ApplicationContext
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
        if(SpringUtils.applicationContext == null) {
            SpringUtils.applicationContext = applicationContext;
        }
        System.out.println("---------------------------------------------------------------------");

        System.out.println("---------------------------------------------------------------------");

        System.out.println("---------------me.shijunjie.util.SpringUtil------------------------------------------------------");

        System.out.println("========ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象,applicationContext="+SpringUtils.applicationContext+"========");

        System.out.println("---------------------------------------------------------------------");
	}

	/**
	 * 获取实例
	 *
	 * @param type
	 *            Bean类型
	 * @return 实例
	 */
	public <T> T getBean(Class<T> type) {
		Assert.notNull(type, "");
		return applicationContext.getBean(type);
	}

	/**
	 * 销毁
	 */
	public void destroy() throws Exception {
		applicationContext = null;
	}

}