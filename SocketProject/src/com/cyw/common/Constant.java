package com.cyw.common;

public class Constant {

	/**
	 * 请求类型对照量
	 * @author Administrator
	 */
	public class RequestType{
		public static final String REQUEST_TYPE_CONN = "10";//客户端连接
		public static final String REQUEST_TYPE_GET_ROOMSTAT = "20";//客户端请求房态
		public static final String REQUEST_TYPE_SEND_ORDER = "21";//客户端发送订单
		
		public static final String REQUEST_TYPE_CLIENT_RETURN = "30";//客户端处理返回
	}
	
	public class ResultCode{
		public static final String SUCCESS = "1";//请求成功
		public static final String REQUEST_TYPE_ERR = "-1";//请求类型不正确
		public static final String REQUEST_TARGET_ERR = "-2";//请求目标未连接
	}
}
