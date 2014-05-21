package com.bdy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyDiscount;
import com.bdy.model.BdyMainkind;
import com.bdy.model.BdyMakearea;
import com.bdy.model.BdySet;
import com.bdy.service.ManageService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class ManageInsertMainKindAction extends ActionSupport implements Preparable, ServletRequestAware{

	ManageService service;
	HttpServletRequest request;
	
	private String mkName;
	List<BdyDiscount> disc;
	List<BdySet> set;
	List<BdyMakearea> ma;
	List<BdyMainkind> mk ;	
	
	public String getMkName() {
		return mkName;
	}


	public void setMkName(String mkName) {
		this.mkName = mkName;
	}


	public List<BdyDiscount> getDisc() {
		return disc;
	}


	public void setDisc(List<BdyDiscount> disc) {
		this.disc = disc;
	}


	public List<BdySet> getSet() {
		return set;
	}


	public void setSet(List<BdySet> set) {
		this.set = set;
	}


	public List<BdyMakearea> getMa() {
		return ma;
	}


	public void setMa(List<BdyMakearea> ma) {
		this.ma = ma;
	}


	public List<BdyMainkind> getMk() {
		return mk;
	}


	public void setMk(List<BdyMainkind> mk) {
		this.mk = mk;
	}


	@Override
	public void validate() {
		if(mkName==null||mkName.trim().length()==0){
			this.addFieldError("mkName",this.getText("mk.mkName.required"));
		}
	}
	
	
	@Override
	public String execute() throws Exception {
		service.insideInsertMK(mkName);
		disc = service.getAllDiscount();
		set = service.getAllSet();
		ma= service.getAllMakeArea();
		mk=service.getAllMainKind();
		return Action.SUCCESS;
	}
	
	@Override
	public void prepare() throws Exception {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		service = (ManageService)context.getBean("ManageService");
		
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}

	

	
}
