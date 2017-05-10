/**
 * 
 */
package com.cyw.mammoth.core.ctrl;


import org.springframework.beans.factory.annotation.Autowired;
import com.cyw.mammoth.core.dao.BaseDao;
import com.cyw.mammoth.core.util.SpringBeanLoader;

/**
 * @ClassName:BaseCtrl.java
 *
 * @Description:Controller基类扩展
 *
 * @author: wu_penpen
 *
 * @Date:2016-7-26上午10:38:06
 *
 */
public class BaseCtrl {

	protected BaseDao baseDao;

	@Autowired
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	protected int pageEnumShow = 1;
	
	protected int pageSize = 20;
	
	protected int total;
	
	protected int pageIndex;
	
	/*@Autowired
	protected BaseDao baseDao=(BaseDao)getService("baseDao");*/

	/**
     * 从服务容器中获取服务组件
     * 
     * @param pBeanId
     * @return
     */
	/*protected Object getService(String pBeanId) {
		Object springBean = SpringBeanLoader.getSpringBean(pBeanId);
		return springBean;
	}*/

}
