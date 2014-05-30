package com.bdy.controller;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class LogoutAction extends ActionSupport implements SessionAware {
	Map<String, Object> session ;
	
	@Override
	public void setSession(Map<String, Object> session ) {
		this.session = session;

	}

	@Override
	public String execute() throws Exception {
		session.remove("empData");
		return Action.SUCCESS;
	}

	
}
