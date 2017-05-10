/**
 * 
 */
package com.cyw.mammoth.service.impl;



import java.util.List;

import com.cyw.mammoth.core.service.impl.BaseServiceImpl;
import com.cyw.mammoth.model.Add;
import com.cyw.mammoth.service.ITestDemo;

/**
 * @ClassName:TestDemoImpl.java
 *
 * @Description:测试案例实现类
 *
 * @author: wu_penpen
 *
 * @Date:2016-7-26下午5:25:55
 *
 */
@SuppressWarnings("unchecked")
public class TestDemoImpl extends BaseServiceImpl implements ITestDemo {

	
	@Override
	public List<Add> getAddList() {
		List<Add> list=baseDao.queryForList("AddMapper.getAll");
		return list;
	}

	
	@Override
	public void save(Add add) {
		baseDao.insert("AddMapper.insertSelective",add);
	}


	@Override
	public Add getAddInfo(String id) {
		Add add=(Add) baseDao.queryForObject("AddMapper.selectByPrimaryKey", id);
		return add;
	}


	@Override
	public void updateAddInfo(Add add) {
		baseDao.update("AddMapper.updateByPrimaryKeySelective", add);
	}

	@Override
	public void delAddInfo(String id) {
		baseDao.delete("AddMapper.deleteByPrimaryKey", id);
	}
	

}
