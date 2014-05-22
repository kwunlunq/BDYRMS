package com.bdy.controller;

import java.util.Date;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyEmp;
import com.bdy.model.CheckOut;
import com.bdy.service.ManageService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class CheckOutBillAction extends ActionSupport implements Preparable,SessionAware {

	
		private int disId;
		ManageService service;
		Map<String, Object> session;
		
		
		public int getDisId() {
			return disId;
		}

		public void setDisId(int disId) {
			this.disId = disId;
		}


		@Override
		public void prepare() throws Exception {
			WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
			service = (ManageService)context.getBean("ManageService");
			
		}

		@Override
		public String execute() throws Exception {
			CheckOut checkOut=(CheckOut) session.get("checkout");
			checkOut.setDiscount(service.getDiscountById(disId));
			BdyEmp emp=(BdyEmp)	session.get("empData");
			checkOut.setFinalPrice((int)Math.rint(checkOut.getPrice()*checkOut.getDiscount().getDisPrice()));
			checkOut.setEndDate(new Date(System.currentTimeMillis()));
			checkOut.setEmp(emp);
			
			service.insertBill(checkOut);
			session.remove("checkout");
			return Action.SUCCESS;
		}

		@Override
		public void setSession(Map<String, Object> session) {
			this.session= session;
			
		}
		
}
