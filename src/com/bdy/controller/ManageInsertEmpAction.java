package com.bdy.controller;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyEmp;
import com.bdy.service.ManageService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class ManageInsertEmpAction extends ActionSupport implements Preparable {
	private BdyEmp emp;
	
	public BdyEmp getEmp() {
		return emp;
	}
	public void setEmp(BdyEmp emp) {
		this.emp = emp;
	}
	
	ManageService service;
	@Override
	public void prepare() throws Exception {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		service=(ManageService)context.getBean("ManageService");
		
	}
	
	@Override
	public void validate() {
		if(emp.getEmpId()==null||emp.getEmpId().length()==0){
			this.addFieldError("emp.empId", this.getText("emp.empId.required"));
		}
		if(emp.getName()==null||emp.getName().length()==0){
			this.addFieldError("emp.name", this.getText("emp.name.required"));
		}
		if(emp.getComedate()==null){
			this.addFieldError("emp.comedate", this.getText("emp.comedate.required"));
		}
		if(emp.getPhone()==null||emp.getPhone().length()==0){
			this.addFieldError("emp.phone", this.getText("emp.phone.required"));
		}
		if(emp.getEmpAddress()==null||emp.getEmpAddress().length()==0){
			this.addFieldError("emp.empAddress", this.getText("emp.empAddress.required"));
		}
	}
	
	
	
	@Override
	public String execute() throws Exception {
		System.out.println("SEX="+emp.getSex());
		System.out.println(emp.getEmpId());
		System.out.println(emp.getName());
		System.out.println(emp.getPasswd());
		System.out.println(emp.getPhone());
		System.out.println(emp.getBdyPriority().getPriId());
		System.out.println(emp.getSalary());
		System.out.println(service.insertEmp(emp));
		return Action.SUCCESS;
	}	
}
