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
    private String empId;
    

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

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
		emps=service.getAllEmps();
		prior = service.getAllPri();
		
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
	BdyEmp empTemp;
	@Override
	public String execute() throws Exception {
	 empTemp=service.findEmpById(empId.trim());
//	 empTemp.setEmpId(emp.getEmpId());
	 empTemp.setName(emp.getName());
	 empTemp.setSex(emp.getSex());
	 empTemp.setComedate(emp.getComedate());
	 empTemp.setEmpAddress(emp.getEmpAddress());
	 empTemp.getBdyPriority().setPriId(emp.getBdyPriority().getPriId());
	 empTemp.setPhone(emp.getPhone());
	 empTemp.setResign(emp.getResign());
	 empTemp.setSalary(emp.getSalary());
//	 service.updateEmp(empTemp,empId);
	 service.updateEmps(empTemp);
	 	emps=service.getAllEmps();
		prior = service.getAllPri();
		return Action.SUCCESS;
	}

	

	
	
	
}
