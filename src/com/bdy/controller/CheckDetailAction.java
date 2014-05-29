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
import com.bdy.model.BdyEmp;
import com.bdy.model.BdyFood;
import com.bdy.model.BdyOrder;
import com.bdy.model.BdyPriority;
import com.bdy.model.BdySet;
import com.bdy.model.BdyTable;
import com.bdy.model.CheckOut;
import com.bdy.service.ManageService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class CheckDetailAction extends ActionSupport implements Preparable,SessionAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer tabId;
	private List<BdyDiscount> discounts;
	private List<BdyEmp> emps;
	
	
	
	public List<BdyEmp> getEmps() {
		return emps;
	}


	public void setEmps(List<BdyEmp> emps) {
		this.emps = emps;
	}


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
		emps = service.getAllEmps();
		session.remove("table");
		session.remove("checkout");
		discounts = service.getAllDiscount();
		CheckOut checkout = new CheckOut();//-----創造checkout物件
		checkout.setTabId(tabId); //取得桌號
		Set<BdyOrder> orders = service.getOrdersByTableId(tabId);//--取得此桌的orders
		List<BdyOrder> ords = new ArrayList<BdyOrder>();//Set轉List
		for(BdyOrder temp:orders){
			ords.add(temp);
		}
		Integer maxCustNum=0;//----取得所有訂單中最大客數為客數
		for(BdyOrder tempOrder:ords){
			if(tempOrder.getCustNum()>maxCustNum){
				maxCustNum=tempOrder.getCustNum();
			}
		}
		Double price = service.getPrice(orders);//------計算所有訂單中的價錢
		checkout.setOrders(orders);//-----放入
		checkout.setCustNum(maxCustNum);//-----放入
		checkout.setPrice(price);//-----放入
		Map<BdySet, List<BdyFood>> setMap = service.sortSetMap(checkout.getOrders());
		checkout.setSetDetailMap(service.sortSetDetailMap(checkout.getOrders()));
		checkout.setSetMap(setMap);
		BdyTable table = service.getOrderTableName(tabId);
		session.put("table", table);
		session.put("checkout", checkout);
		return Action.SUCCESS;
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
