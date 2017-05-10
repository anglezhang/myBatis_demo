/**
 * 
 */
package com.cyw.mammoth.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyw.mammoth.core.dao.BaseDao;
import com.cyw.mammoth.core.service.BaseService;

/**
 * @ClassName:BaseServiceImpl.java
 *
 * @Description:基类扩展实现类
 *
 * @author: wu_penpen
 *
 * @Date:2016-7-26下午5:19:07
 *
 */
@Service("baseService")
public class BaseServiceImpl implements BaseService{
	
	/**
	 * 基类中给子类暴露的一个DAO接口<br>
	 * 连接数据库
	 */
	@Autowired
	public BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

}
