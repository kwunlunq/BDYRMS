package com.bdy.controller;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyEmp;
import com.bdy.model.BdyPriority;
import com.bdy.service.ManageService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class ManageShowEmp extends ActionSupport implements Preparable {
	
	private static final long serialVersionUID = 1L;
	private List<BdyEmp> emps ;
    private List <BdyPriority> prior;
    
	public List<BdyPriority> getPrior() {
		return prior;
	}

	public void setPrior(List<BdyPriority> prior) {
		this.prior = prior;
	}

	public ManageService getService() {
		return service;
	}

	public void setService(ManageService service) {
		this.service = service;
	}

	public List<BdyEmp> getEmps() {
		return emps;
	}

	public void setEmps(List<BdyEmp> emps) {
		this.emps = emps;
	}
	
	ManageService service;
	@Override
	public void prepare() throws Exception {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		service=(ManageService) context.getBean("ManageService");
	}
	@Override
	public String execute() throws Exception {
		emps=service.getAllEmps();
		prior = service.getAllPri();
		return Action.SUCCESS;
	}

	
	
	
}
