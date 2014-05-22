package com.bdy.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyDiscount;
import com.bdy.model.BdyOrder;
import com.bdy.model.BdyPriority;
import com.bdy.model.CheckOut;
import com.bdy.service.ManageService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class CheckDetailAction extends ActionSupport implements Preparable,SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer tabId;
	private List<BdyDiscount> discounts;
	
	
	public List<BdyDiscount> getDiscounts() {
		return discounts;
	}


	public void setDiscounts(List<BdyDiscount> discounts) {
		this.discounts = discounts;
	}


	ManageService service;
	Map<String, Object> session;
	public Integer getTabId() {
		return tabId;
	}


	public void setTabId(Integer tabId) {
		this.tabId = tabId;
	}


	@Override
	public String execute() throws Exception {
		discounts = service.getAllDiscount();
		CheckOut checkout = new CheckOut();
		checkout.setTabId(tabId);
		Set<BdyOrder> orders = service.getOrdersByTableId(tabId);
		List<BdyOrder> ords = new ArrayList<BdyOrder>();
		for(BdyOrder temp:orders){
			ords.add(temp);
		}
		Integer maxCustNum=0;
		for(BdyOrder tempOrder:ords){
			if(tempOrder.getCustNum()>maxCustNum){
				maxCustNum=tempOrder.getCustNum();
			}
		}
		Double price = service.getPrice(orders);
		
		checkout.setOrders(orders);
		checkout.setCustNum(maxCustNum);
		checkout.setPrice(price);
		session.put("checkout", checkout);
		return super.execute();
	}


	@Override
	public void prepare() throws Exception {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		service = (ManageService)context.getBean("ManageService");
		
	}


	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}

}
