package com.bdy.controller;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyTable;
import com.bdy.service.ManageService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class CheckTableAction extends ActionSupport implements Preparable{

	ManageService service;
	List<BdyTable> usingTables;
	
	public List<BdyTable> getUsingTables() {
		return usingTables;
	}

	public void setUsingTables(List<BdyTable> usingTables) {
		this.usingTables = usingTables;
	}

	@Override
	public String execute() throws Exception {
	 usingTables=service.getUseTable();
		return Action.SUCCESS;
	}

	@Override
	public void prepare() throws Exception {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		service = (ManageService)context.getBean("ManageService");
		
	}

}
