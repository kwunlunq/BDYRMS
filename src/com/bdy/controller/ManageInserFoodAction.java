package com.bdy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyDiscount;
import com.bdy.model.BdyFood;
import com.bdy.model.BdySetdetail;
import com.bdy.service.ManageService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class ManageInserFoodAction extends ActionSupport implements ServletRequestAware , Preparable {
	private String foodname; 
	private double foodPrice ;
	private int foodQTY ;
	private String discription ;
	private int foodKind;
	 
	 HttpServletRequest request;
	 ManageService foodinsertservice;
	public String getFoodname() {
		return foodname;
	}
	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}
	public double getFoodPrice() {
		return foodPrice;
	}
	public void setFoodPrice(double foodPrice) {
		this.foodPrice = foodPrice;
	}
	public int getFoodQTY() {
		return foodQTY;
	}
	public void setFoodQTY(int foodQTY) {
		this.foodQTY = foodQTY;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public int getFoodKind() {
		return foodKind;
	}
	public void setFoodKind(int foodKind) {
		this.foodKind = foodKind;
	}
		
	
	@Override
	public void validate() {
		List<BdyDiscount> discount = foodinsertservice.getAllDiscount();
		List<BdyFood> foods = foodinsertservice.getAllFood();
		List<BdySetdetail> detail = foodinsertservice.getAllDetail();
		request.setAttribute("resultFood", foods);
		request.setAttribute("resultDetail", detail);
		request.setAttribute("resultdiscount", discount);
		if(foodname==null||foodname.trim().length()==0){
			this.addFieldError("foodname", this.getText("foodname.required"));
		}
		if(foodPrice==0){
			this.addFieldError("foodPrice", this.getText("foodPrice.required"));
		}
		if(foodQTY==0) {
            this.addFieldError("foodQTY",
            		this.getText("foodQTY.required"));
		}
	}
	
	@Override
	public String execute() throws Exception {
		int insertState = foodinsertservice.insertFood(foodname, foodPrice, foodQTY, discription, foodKind);
		List<BdyDiscount> discount = foodinsertservice.getAllDiscount();
		List<BdyFood> foods = foodinsertservice.getAllFood();
		List<BdySetdetail> detail = foodinsertservice.getAllDetail();
		request.setAttribute("insertState", insertState);
		
		if(foods!=null){
			request.setAttribute("foodcount", foods.size());
			
		}
		else{
			request.setAttribute("foodcount","0");
		}
		request.setAttribute("resultFood", foods);
		request.setAttribute("resultDetail", detail);
		request.setAttribute("resultdiscount", discount);
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
		foodinsertservice = (ManageService)context.getBean("ManageService");
		
		
				
	}

	
}