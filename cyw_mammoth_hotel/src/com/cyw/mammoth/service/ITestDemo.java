/**
 * 
 */
package com.cyw.mammoth.service;

import java.util.List;

import com.cyw.mammoth.model.Add;



/**
 * @ClassName:ITestDemo.java
 *
 * @Description:测试案例接口
 *
 * @author: wu_penpen
 *
 * @Date:2016-7-26下午5:24:48
 *
 */
public interface ITestDemo {
	
	/**
	 * 查询列表信息
	 */
	public List<Add> getAddList();
	
	/**
	 * 插入一条数据
	 * @param add
	 */
	public void save(Add add);
	
	/**
	 * 查询一条数据
	 * @param add
	 */
	public Add getAddInfo (String tid);
	
	/**
	 * 修改一条数据
	 */
	public void updateAddInfo(Add add);
	
	/**
	 * 删除一个对象
	 * @param id
	 */
	public void delAddInfo(String id);
	
	
}
