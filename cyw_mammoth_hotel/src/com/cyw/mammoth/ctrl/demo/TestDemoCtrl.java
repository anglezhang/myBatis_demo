/**
 * 
 */
package com.cyw.mammoth.ctrl.demo;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cyw.mammoth.core.ctrl.BaseCtrl;
import com.cyw.mammoth.model.Add;
import com.cyw.mammoth.service.ITestDemo;
import com.cyw.mammoth.util.LogUtils;


/**
 * @ClassName:TestDemoCtrl.java
 *
 * @Description:测试案例
 *
 * @author: wu_penpen
 *
 * @Date:2016-7-25下午6:10:37
 *
 */
@Controller
@RequestMapping(value="/demo/TestDemoCtrl")
public class TestDemoCtrl extends BaseCtrl{
	
	
	@Autowired
	private ITestDemo testDemoService;

	/**
	 * 测试列表
	 * @return
	 */
	@RequestMapping(value="/getTestList.do",method=RequestMethod.GET)
	public ModelAndView getTestList(){
		ModelAndView mav=new ModelAndView("listAll");
		List<Add> list=testDemoService.getAddList();
		mav.addObject("addLists", list);
		return mav;
	}
	
	/**
	 * 测试添加信息
	 * @param add
	 * @return
	 */
	@RequestMapping(value="/addInfo.do",method=RequestMethod.POST)
	public ModelAndView addInfo(Add add){
		LogUtils.info("某某模块添加业务开始");
		ModelAndView mav=new ModelAndView("result");
		try {
			add.setId(UUID.randomUUID().toString());
			testDemoService.save(add);
			mav.addObject("message", "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("message", "失败");
		}
		LogUtils.info("某某模块添加业务结束");
		return mav;
	}
	
	/**
	 * 测试查询一个对象
	 * @param tid id
	 * @return
	 */
	@RequestMapping(value="/getAddInfo.do")
	public ModelAndView getAddInfo(String tid){
		ModelAndView mav=new ModelAndView("modify");
		Add add=testDemoService.getAddInfo(tid);
		mav.addObject("add", add);
		return mav;
	}
	
	/**
	 * 测试修改一个对象
	 * @param add
	 * @return
	 */
	@RequestMapping(value="/updateAddInfo.do")
	public ModelAndView updateAddInfo(Add add){
		ModelAndView mav=new ModelAndView("result");
		testDemoService.updateAddInfo(add);
		mav.addObject("message", "修改成功");
		return mav;
	}
	
	@RequestMapping(value="/delAddInfo.do")
	public ModelAndView delAddInfo(String tid){
		ModelAndView mav=new ModelAndView("result");
		testDemoService.delAddInfo(tid);
		mav.addObject("message", "删除成功");
		return mav;
	}
	
}
