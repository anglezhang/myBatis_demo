/**
 * 
 */
package com.cyw.mammoth.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @ClassName:LogUtils.java
 *
 * @Description:日志记录类
 *
 * @author: wu_penpen
 *
 * @Date:2016-7-22下午2:41:17
 *
 */
public class LogUtils {
	
	private static Log info=LogFactory.getLog("infoR");
	private static Log debug=LogFactory.getLog("allR");
	private static Log error = LogFactory.getLog("errorR");
	
	public LogUtils() {
	}
	
	/**
	 * 一般情况记录到/logs/infoLog.txt
	 */
	public static void info(String message) {
		info.info(message);
	}

	/**
	 * 调试信息记录到/logs/debugLog.txt
	 */
	public static void debug(String message) {
		debug.debug(message);
	}
	
	/**
	 * 错误信息记录到/logs/errorLog.txt
	 */
	public static void error(String message) {
		error.error(message);
	}

}
