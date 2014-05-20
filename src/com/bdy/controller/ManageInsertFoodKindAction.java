package com.bdy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyDiscount;
import com.bdy.model.BdyFood;
import com.bdy.model.BdyFoodkind;
import com.bdy.model.BdyMakearea;
import com.bdy.model.BdySetdetail;
import com.bdy.service.ManageService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class ManageInsertFoodKindAction extends ActionSupport implements ServletRequestAware , Preparable{

	
	HttpServletRequest request;
	ManageService service;
	private String name;
	private Double period;
	private int ma;
	private int seq;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPeriod() {
		return period;
	}

	public void setPeriod(Double period) {
		this.period = period;
	}

	public int getMa() {
		return ma;
	}

	public void setMa(int ma) {
		this.ma = ma;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	
	@Override
	public void validate() {
		List<BdyDiscount> discount = service.getAllDiscount();
		List<BdyFood> foods = service.getAllFood();
		List<BdySetdetail> detail = service.getAllDetail();
		List<BdyFoodkind> foodkind = service.getAllFoodKind();
		request.setAttribute("resultFood", foods);
		request.setAttribute("resultDetail", detail);
		request.setAttribute("resultdiscount", discount);
		request.setAttribute("resultfoodkind", foodkind);
		if(name==null||name.trim().length()==0){
			this.addFieldError("fkName", this.getText("fkname.required"));
		}
		if(period==null||period==0){
			this.addFieldError("fkPeriod",this.getText("fkperiod.required"));
		}
		if(seq==0){
			this.addFieldError("fkSEQ",this.getText("seq.required"));
		}
		request.setAttribute("pags", "2");
	}
	
	@Override
	public String execute() throws Exception {
		BdyFoodkind fk  = new BdyFoodkind();
		fk.setName(name);
		fk.setPeriod(period);
		BdyMakearea Ma = new BdyMakearea();
		Ma.setMaId(ma);
		fk.setBdyMakearea(Ma);
		fk.setSeq(seq);
		
		int fkState = service.insertFoodKind(fk);
		List<BdyDiscount> discount = service.getAllDiscount();
		List<BdyFood> foods = service.getAllFood();
		List<BdySetdetail> detail = service.getAllDetail();
		List<BdyFoodkind> foodkind = service.getAllFoodKind();
		request.setAttribute("pags", "2");
		request.setAttribute("fkState", fkState);
		request.setAttribute("resultFood", foods);
		request.setAttribute("resultDetail", detail);
		request.setAttribute("resultdiscount", discount);
		request.setAttribute("resultfoodkind", foodkind);
		return Action.SUCCESS;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}

	@Override
	public void prepare() throws Exception {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		service = (ManageService)context.getBean("ManageService");
		
	}

	

	
}
