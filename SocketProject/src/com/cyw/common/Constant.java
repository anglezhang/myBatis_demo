package com.cyw.common;

public class Constant {

	/**
	 * �������Ͷ�����
	 * @author Administrator
	 */
	public class RequestType{
		public static final String REQUEST_TYPE_CONN = "10";//�ͻ�������
		public static final String REQUEST_TYPE_GET_ROOMSTAT = "20";//�ͻ�������̬
		public static final String REQUEST_TYPE_SEND_ORDER = "21";//�ͻ��˷��Ͷ���
		
		public static final String REQUEST_TYPE_CLIENT_RETURN = "30";//�ͻ��˴�����
	}
	
	public class ResultCode{
		public static final String SUCCESS = "1";//����ɹ�
		public static final String REQUEST_TYPE_ERR = "-1";//�������Ͳ���ȷ
		public static final String REQUEST_TARGET_ERR = "-2";//����Ŀ��δ����
	}
}
