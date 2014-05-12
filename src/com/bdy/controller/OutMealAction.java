package com.bdy.controller;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyFoodkind;
import com.bdy.model.KitchenView;
import com.bdy.service.KitchenService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class OutMealAction extends ActionSupport implements Preparable {
	private int id;
	KitchenService service;
	List<KitchenView> viewlist;
	List<BdyFoodkind> foodKinds;
	public List<BdyFoodkind> getFoodKinds() {
		return foodKinds;
	}

	public void setFoodKinds(List<BdyFoodkind> foodKinds) {
		this.foodKinds = foodKinds;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public List<KitchenView> getViewlist() {
		return viewlist;
	}

	public void setViewlist(List<KitchenView> viewlist) {
		this.viewlist = viewlist;
	}

	@Override
	public void prepare() throws Exception {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		service =(KitchenService) context.getBean("KitchenService");
		service.sortAllSetOutMealMap();
	}
	@Override
	public String execute() throws Exception {
		service.outMealchange(id);
		viewlist=service.getNotOutOrderlistsObject();
		foodKinds=service.getFoodKinds();
		return Action.SUCCESS;
	}

	
	
}
