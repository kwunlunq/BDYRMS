package com.bdy.controller;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;








import com.bdy.model.BdyEmp;
import com.bdy.service.LoginService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements SessionAware,ServletRequestAware{

	private Map<String,Object> session;
	HttpServletRequest request;
	@Override
	public void setSession(Map<String, Object> map) {
		this.session=map;
		
	}	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}
	//接收資料	
	private String userID;
	private String userPW;

	
	//驗證資料
	@Override
	public void validate() {
		if(userID==null || userID.trim().length()==0) {
            this.addFieldError("userID",
            		this.getText("loginForm.userID.required"));
		}
		if(userPW==null || userPW.trim().length()==0) {
            this.addFieldError("userPW",
            		this.getText("loginForm.userPW.required"));
		}
	}
	@Override
	public String execute() throws Exception {
		LoginService Login = new LoginService(request.getServletContext());
		BdyEmp bean = Login.login(userID, userPW);
		
		if(bean==null||bean.getResign()==1){
			this.addFieldError("userPW",
            		this.getText("loginForm.login.fail"));
			
			return Action.INPUT;
		}else if(bean.getBdyPriority().getPriId() == 3){
			session.put("empData", bean);
			return Action.SUCCESS;
		}else{
			session.put("empData", bean);
			return Action.SUCCESS;
		}
		
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserPW() {
		return userPW;
	}

	public void setUserPW(String userPW) {
		this.userPW = userPW;
	}
}
