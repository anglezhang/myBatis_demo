/**
 * 
 */
package com.cyw.mammoth.test;

import org.apache.log4j.Logger;


/**
 * @ClassName:PrintMsg.java
 *
 * @Description:
 *
 * @author: wu_penpen
 *
 * @Date:2016-7-25下午3:42:02
 *
 */
public class PrintMsg {
	
	Logger logger = Logger.getLogger(PrintMsg.class);
	
	public PrintMsg(){
		logger.info(">>这是PrintMsg.class 的构造函数");
		printMsg();
	}
	
	public void printMsg() {
		logger.error("这是输出异常的日志:logger.error");
	}

}
