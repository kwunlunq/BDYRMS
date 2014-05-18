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

public class ManageUpdateEmpAction extends ActionSupport implements Preparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ManageService service;
	
	private BdyEmp emp;//------多對一映射
	private List<BdyEmp> emps ;
    private List <BdyPriority> prior;
    
    
	public List<BdyEmp> getEmps() {
		return emps;
	}

	public void setEmps(List<BdyEmp> emps) {
		this.emps = emps;
	}

	public List<BdyPriority> getPrior() {
		return prior;
	}

	public void setPrior(List<BdyPriority> prior) {
		this.prior = prior;
	}

	public BdyEmp getEmp() {
		return emp;
	}

	public void setEmp(BdyEmp emp) {
		this.emp = emp;
	}
	@Override
	public void prepare() throws Exception {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		service=(ManageService)context.getBean("ManageService");
		
	}
	@Override
	public void validate() {
		emps=service.getAllEmps();
		prior = service.getAllPri();
	}
	
	@Override
	public String execute() throws Exception {
		System.out.println(emp.getComedate());
		emps=service.getAllEmps();
		prior = service.getAllPri();
		return Action.SUCCESS;
	}

	

	
	
	
}
