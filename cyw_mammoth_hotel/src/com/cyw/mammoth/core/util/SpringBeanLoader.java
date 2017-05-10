/**
 * 
 */
package com.cyw.mammoth.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;


/**
 * @ClassName:SpringBeanLoader.java
 *
 * @Description:Spring容器加载
 *
 * @author: wu_penpen
 *
 * @Date:2016-7-26上午11:12:07
 *
 */
public class SpringBeanLoader {
	
private static Log log = LogFactory.getLog(SpringBeanLoader.class);
	
	private static ApplicationContext applicationContext;

	public static void setApplicationContext(ApplicationContext applicationContext) {
		SpringBeanLoader.applicationContext = applicationContext;
	}

	static {
		try {
			initApplicationContext();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化ApplicationContext对象
	 * @throws Exception 
	 */
	private static void initApplicationContext() throws Exception {
		try {
				log.info("系统正在初始化Spring...");
				log.info("Spring初始化成功,SpringBean已经被实例化。");
		} catch (Exception e) {
			log.error("Spring初始化失败.");
			e.printStackTrace();
			System.exit(0);
			throw e;
		}
	}

	/**
	 * 返回ApplicationContext对象
	 * 
	 * @return ApplicationContext 返回的ApplicationContext实例
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取一个SpringBean服务
	 * 
	 * @param pBeanId
	 *            Spring配置文件名中配置的SpringID号
	 * @return Object 返回的SpringBean实例
	 */
	public static Object getSpringBean(String pBeanId) {
		Object springBean = null;
		try {
			springBean = applicationContext.getBean(pBeanId);
		} catch (NoSuchBeanDefinitionException e) {
			log.error("Spring配置文件中没有匹配到ID号为:[" + pBeanId + "]的SpringBean组件,请检查!");
			log.error(e.getMessage());
		}
		return springBean;
	}

}
