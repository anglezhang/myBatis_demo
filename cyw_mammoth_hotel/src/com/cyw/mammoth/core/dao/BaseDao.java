/**
 * 
 */
package com.cyw.mammoth.core.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.cyw.mammoth.core.page.Page;
import com.cyw.mammoth.core.util.Dto;



/**
 * @ClassName:BaseDao.java
 *
 * @Description:基类扩展接口
 *
 * @author: wu_penpen
 *
 * @Date:2016-7-26上午9:56:07
 *
 */
@SuppressWarnings({"rawtypes"})
public interface BaseDao {
	
	/**
	 * 插入一条记录
	 * @param SQL语句ID号
	 * @param parameterObject 要插入的对象(map javaBean)
	 */
	public void insert(String statementName, Object parameterObject);
	
	/**
	 * 插入一条记录
	 * @param SQL语句ID号
	 */
	public void insert(String statementName);
	
	/**
	 * 查询一条记录
	 * @param SQL语句ID号
	 * @param parameterObject 查询条件对象(map javaBean)
	 */
	public Object queryForObject(String statementName, Object parameterObject);
	
	/**
	 * 查询一条记录
	 * @param SQL语句ID号
	 */
	public Object queryForObject(String statementName);
	
	/**
	 * 查询记录集合
	 * @param SQL语句ID号
	 * @param parameterObject 查询条件对象(map javaBean)
	 */
	public List queryForList(String statementName, Object parameterObject);
	
	/**
	 * 查询记录集合
	 * @param SQL语句ID号
	 */
	public List queryForList(String statementName);
	
	/**
	 * 更新记录
	 * @param SQL语句ID号
	 * @param parameterObject 更新对象(map javaBean)
	 */
	public int update(String statementName, Object parameterObject);
	
	/**
	 * 更新记录
	 * @param SQL语句ID号
	 */
	public int update(String statementName);
	
	/**
	 * 删除记录
	 * @param SQL语句ID号
	 * @param parameterObject 更新对象(map javaBean)
	 */
	public int delete(String statementName, Object parameterObject);
	
	/**
	 * 删除记录
	 * @param SQL语句ID号
	 */
	public int delete(String statementName);
	
	/**
	 * 按分页查询
	 * 
	 * @param SQL语句ID号
	 * @param parameterObject
	 *            查询条件对象(map javaBean)
	 */
	public List queryForPage(String statementName, Dto qDto, Page page) throws SQLException;
	
	/**
	 * 按分页查询
	 * 
	 * @param SQL语句ID号
	 * @param parameterObject
	 *            查询条件对象(map javaBean)
	 */
	public List queryForPage(String statementName, Dto qDto) throws SQLException;
	
	/**
	 * 获取Connection对象<br>
	 * 说明：虽然向Dao消费端暴露了获取Connection对象的方法但不建议直接获取Connection对象进行JDBC操作
	 * 
	 * @return 返回Connection对象
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException;
	
	/**
	 * 获取SqlSession对象<br>
	 * @return
	 * @throws SQLException
	 */
	public SqlSession getMyBatisSqlSession() throws SQLException;
	
	
	
	

}
