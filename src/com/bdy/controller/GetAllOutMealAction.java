package com.bdy.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.service.KitchenService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class GetAllOutMealAction extends ActionSupport implements Preparable,ServletResponseAware {
	
	
	private static final long serialVersionUID = 1L;
	HttpServletResponse response;
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		
	}
	KitchenService service;
	@Override
	public void prepare() throws Exception {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		service=(KitchenService)context.getBean("KitchenService");
		service.sortAllSetOutMealMap();
	}
	
	public void myJSONOutMeal() throws IOException{
		response.setContentType("html/application;charset=UTF-8");
		PrintWriter out =response.getWriter();
		JsonArray notOutMeals=service.getNotOutOrderlistsJsonArray();
		out.write(notOutMeals.toString());
	}

	

	
	
}
