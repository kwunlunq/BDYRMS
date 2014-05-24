package com.bdy.controller;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyFoodkind;
import com.bdy.service.ManageService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class SetMealIndexAction extends ActionSupport implements Preparable{

	List<BdyFoodkind> foodKind;
	ManageService service;
	
	public List<BdyFoodkind> getFoodKind() {
		return foodKind;
	}

	public void setFoodKind(List<BdyFoodkind> foodKind) {
		this.foodKind = foodKind;
	}

	@Override
	public String execute() throws Exception {
		foodKind = service.getAllFoodKindSetMeal();
		return Action.SUCCESS;
	}

	@Override
	public void prepare() throws Exception {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		service = (ManageService)context.getBean("ManageService");
		
	}

}
