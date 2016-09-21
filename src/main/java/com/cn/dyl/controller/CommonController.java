package com.cn.dyl.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * .spirng mvc 默认index controller 方式 
 * @author dengyinlei
 *
 */
@Controller
@RequestMapping(value="/")
public class CommonController {

	@RequestMapping(value="")
	public String index(HttpServletRequest request)
	{
		System.out.println("-----------------------");
		System.out.println("===========================你好==========================");
		return "useList";
	}
	@RequestMapping(value="index")
	public String index1(HttpServletRequest request)
	{
		System.out.println("-----------------------");
		return "useList";
	}
	public CommonController(){
		System.out.println("===========================你好==========================");
	}
}
