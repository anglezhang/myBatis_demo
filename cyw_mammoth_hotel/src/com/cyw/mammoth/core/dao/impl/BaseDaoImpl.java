/**
 * 
 */
package com.cyw.mammoth.core.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cyw.mammoth.core.dao.BaseDao;
import com.cyw.mammoth.core.page.Page;
import com.cyw.mammoth.core.util.Dto;
import com.cyw.mammoth.core.util.MammothUtils;

/**
 * @ClassName:BaseDaoImpl.java
 *
 * @Description:基类扩展实现类
 *
 * @author: wu_penpen
 *
 * @Date:2016-7-26上午10:03:40
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Transactional
@Component("baseDao")
@Repository("baseDao")
public class BaseDaoImpl extends SqlSessionDaoSupport implements BaseDao {
	
	private static Log log = LogFactory.getLog(BaseDaoImpl.class);
	
	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	@Override
	public void insert(String statementName, Object parameterObject) {
		this.getSqlSession().insert(statementName, parameterObject);
	}

	@Override
	public void insert(String statementName) {
		this.getSqlSession().insert(statementName);
	}

	@Override
	public Object queryForObject(String statementName, Object parameterObject) {
		return this.getSqlSession().selectOne(statementName, parameterObject);
	}

	@Override
	public Object queryForObject(String statementName) {
		return this.getSqlSession().selectOne(statementName);
	}

	@Override
	public List queryForList(String statementName, Object parameterObject) {
		return this.getSqlSession().selectList(statementName, parameterObject);
	}

	@Override
	public List queryForList(String statementName) {
		return this.getSqlSession().selectList(statementName);
	}

	@Override
	public int update(String statementName, Object parameterObject) {
		return this.getSqlSession().update(statementName, parameterObject);
	}

	@Override
	public int update(String statementName) {
		return this.getSqlSession().update(statementName);
	}

	@Override
	public int delete(String statementName, Object parameterObject) {
		return this.getSqlSession().delete(statementName, parameterObject);
	}

	@Override
	public int delete(String statementName) {
		return this.getSqlSession().delete(statementName);
	}

	
	@Override
	public List queryForPage(String statementName, Dto qDto, Page page)
			throws SQLException {
		if (MammothUtils.isNotEmpty(page)) {
			qDto.setPage(page);
		}
		return this.getSqlSession().selectList(statementName, qDto, new RowBounds(0, 999999));
	}

	
	@Override
	public List queryForPage(String statementName, Dto qDto)
			throws SQLException {
		String start = qDto.getAsString("start");
		String limit = qDto.getAsString("limit");
		int startInt = 0;
		if (MammothUtils.isNotEmpty(start)) {
			startInt = Integer.parseInt(start);
			qDto.put("start", startInt);
		} else {
			qDto.put("start", 0);
			log.warn("缺失分页起始参数,后台已经为你自动赋值，但建议您参照标准范例，如果不是分页查询请使用queryForList()方法");
		}
		
		if (MammothUtils.isNotEmpty(limit)) {
			int limitInt = Integer.parseInt(limit);
			qDto.put("end", limitInt);
		} else {
			qDto.put("end", 999999);
			log.warn("缺失分页终止参数,后台已经为你自动赋值，但建议您参照标准范例，如果不是分页查询请使用queryForList()方法");
		}
		Integer intStart = qDto.getAsInteger("start");
		Integer end = qDto.getAsInteger("end");
		return this.getSqlSession().selectList(statementName, qDto, new RowBounds(intStart.intValue(), end.intValue()));
	}

	@Override
	public Connection getConnection() throws SQLException {
		return this.getSqlSession().getConnection();
	}

	@Override
	public SqlSession getMyBatisSqlSession() throws SQLException {
		return this.getSqlSession();
	}
	
}
