package com.bdy.controller;

import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyPriority;
import com.bdy.service.ManageService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class ManageEmpSelectAction extends ActionSupport implements Preparable,SessionAware {

	Map<String, Object> session;
	 private List <BdyPriority> prior;

	public List<BdyPriority> getPrior() {
		return prior;
	}

	public void setPrior(List<BdyPriority> prior) {
		this.prior = prior;
	}

	ManageService service;
	@Override
	public String execute() throws Exception {
		session.put("prior",prior);
		
		return Action.SUCCESS;
	}

	@Override
	public void prepare() throws Exception {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		service=(ManageService)context.getBean("ManageService");
		prior = service.getAllPri();
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session =session; 
		
	}
	
	
}
