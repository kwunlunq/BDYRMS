package com.bdy.controller;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.service.KitchenService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class OutAllMealAction extends ActionSupport implements Preparable {
	KitchenService service;
	@Override
	public void prepare() throws Exception {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		service = (KitchenService)context.getBean("KitchenService");
		
		
	}
	@Override
	public String execute() throws Exception {
		service.outAllMeal();
		return Action.SUCCESS;
	}
	
	
	

	
}
