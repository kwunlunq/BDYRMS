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
import com.bdy.model.BdySetdetail;
import com.bdy.service.ManageService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class ManageInsertSetDetailAction extends ActionSupport implements ServletRequestAware ,Preparable{

	HttpServletRequest request;
	 ManageService setinsertservice;
	private int setId;
	private int foodId;
	private Double setDetailPrice;
	
	
	public Double getSetDetailPrice() {
		return setDetailPrice;
	}
	public void setSetDetailPrice(Double setDetailPrice) {
		this.setDetailPrice = setDetailPrice;
	}
	public int getSetId() {
		return setId;
	}
	public void setSetId(int setId) {
		this.setId = setId;
	}
	public int getFoodId() {
		return foodId;
	}
	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}
	
	
	
	
	@Override
	public void validate() {
		
		List<BdyDiscount> discount = setinsertservice.getAllDiscount();
		List<BdyFood> foods = setinsertservice.getAllFood();
		List<BdySetdetail> detail = setinsertservice.getAllDetail();
		List<BdyFoodkind> foodkind = setinsertservice.getAllFoodKind();
		request.setAttribute("resultFood", foods);
		request.setAttribute("resultDetail", detail);
		request.setAttribute("resultdiscount", discount);
		request.setAttribute("resultfoodkind", foodkind);
		request.setAttribute("pags", 1);
		if(setDetailPrice==null||setDetailPrice==0){
			this.addFieldError("setdPrice",this.getText("setdPrice.required"));
		}
	}
	@Override
	public String execute() throws Exception {
		int setState = setinsertservice.insertSet(foodId, setId,setDetailPrice);
		List<BdyDiscount> discount = setinsertservice.getAllDiscount();
		List<BdyFood> foods = setinsertservice.getAllFood();
		List<BdySetdetail> detail = setinsertservice.getAllDetail();
		List<BdyFoodkind> foodkind = setinsertservice.getAllFoodKind();

		request.setAttribute("pags", 1);
		request.setAttribute("setState", setState);
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
		setinsertservice = (ManageService)context.getBean("ManageService");
		
	}
	
	
	
}
