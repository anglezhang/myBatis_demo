/**
 * 
 */
package com.cyw.mammoth.core.util;

import java.io.Serializable;
import java.util.HashMap;




/**
 * @ClassName:BaseDto.java
 *
 * @Description:数据传输对象
 *
 * @author: wu_penpen
 *
 * @Date:2016-7-28下午2:24:47
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class BaseDto extends HashMap implements  Serializable {

	
	private static final long serialVersionUID = 1L;
	
	public BaseDto(){}
	
	public BaseDto(String key, Object value){
		put(key, value);
	}
	
	

}
