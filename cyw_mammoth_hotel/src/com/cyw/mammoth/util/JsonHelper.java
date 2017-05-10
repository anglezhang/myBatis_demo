/**
 * 
 */
package com.cyw.mammoth.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyw.mammoth.core.util.Dto;
import com.cyw.mammoth.core.util.MammothUtils;



/**
 * @ClassName:JsonHelper.java
 *
 * @Description:JSON处理
 *
 * @author: wu_penpen
 *
 * @Date:2016-7-28下午2:14:17
 *
 */
@SuppressWarnings({"unused","unchecked","rawtypes"})
public class JsonHelper {
	
	private static Log log = LogFactory.getLog(JsonHelper.class);
	
	/**
	 * 将不含日期时间格式的Java对象系列化为Json资料格式
	 * 
	 * @param pObject
	 *            传入的Java对象
	 * @return
	 */
	public static final String encodeObject2Json(Object pObject) {
		String jsonString = "[]";
		if (MammothUtils.isEmpty(pObject)) {
			// log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
		} else {
			if (pObject instanceof ArrayList) {
				JSONArray jsonArray = JSONArray.fromObject(pObject);
				jsonString = jsonArray.toString();
			} else {
				JSONObject jsonObject = JSONObject.fromObject(pObject);
				jsonString = jsonObject.toString();
			}
		}
		if (log.isInfoEnabled()) {
			//log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}
	
	/**
	 * 将含有日期时间格式的Java对象系列化为Json资料格式<br>
	 * Json-Lib在处理日期时间格式是需要实现其JsonValueProcessor接口,所以在这里提供一个重载的方法对含有<br>
	 * 日期时间格式的Java对象进行序列化
	 * 
	 * @param pObject
	 *            传入的Java对象
	 * @return
	 */
	public static final String encodeObject2Json(Object pObject, String pFormatString) {
		String jsonString = "[]";
		if (MammothUtils.isEmpty(pObject)) {
			// log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
		} else {
			JsonConfig cfg = new JsonConfig();
			cfg.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonValueProcessorImpl(pFormatString));
			cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl(pFormatString));
			cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl(pFormatString));
			if (pObject instanceof ArrayList) {
				JSONArray jsonArray = JSONArray.fromObject(pObject, cfg);
				jsonString = jsonArray.toString();
			} else {
				JSONObject jsonObject = JSONObject.fromObject(pObject, cfg);
				jsonString = jsonObject.toString();
			}
		}
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}
	
	/**
	 * 将分页信息压入JSON字符串
	 * 此类内部使用,不对外暴露
	 * @param JSON字符串
	 * @param totalCount
	 * @return 返回合并后的字符串
	 */
	private static String encodeJson2PageJson(String jsonString, Integer totalCount) {
		jsonString = "{TOTALCOUNT:" + totalCount + ", ROOT:" + jsonString + "}";
		if (log.isInfoEnabled()) {
			log.info("合并后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}

	/**
	 * 直接将List转为分页所需要的Json资料格式
	 * 
	 * @param list
	 *            需要编码的List对象
	 * @param totalCount
	 *            记录总数
	 * @param pDataFormat
	 *            时间日期格式化,传null则表明List不包含日期时间属性
	 */
	public static final String encodeList2PageJson(List list, Integer totalCount, String dataFormat) {
		String subJsonString = "";
		if (MammothUtils.isEmpty(dataFormat)) {
			subJsonString = encodeObject2Json(list);
		} else {
			subJsonString = encodeObject2Json(list, dataFormat);
		}
		String jsonString = "{TOTALCOUNT:" + totalCount + ", ROOT:" + subJsonString + "}";
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}

	
	
	

}
