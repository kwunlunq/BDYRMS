package com.bdy.service;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyEmp;
import com.bdy.model.dao.BdyEmpDao;



public class LoginService {
	
	BdyEmpDao dao ;
	WebApplicationContext context;

	public LoginService(ServletContext ctx) {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ctx);
		dao = (BdyEmpDao) context.getBean("BdyEmpDao");
	}
	public LoginService(HttpServlet servlet){
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(servlet.getServletContext());
		dao = (BdyEmpDao) context.getBean("BdyEmpDao");
	}
	public int changePassword(
		String username, String oldPassword, String newPassword) {
		BdyEmp bean = this.login(username, oldPassword);
		if(bean!=null) {
			String temp = newPassword;
			BdyEmp emp = new BdyEmp();
			emp.setEmpId(username);
			emp.setPasswd(temp);
			return dao.update(emp);
		}
		return 0;
	}
	
	public BdyEmp login(String username, String password) {
		BdyEmp bean = dao.getEmpById(username);
		if(bean!=null) {
			if(password!=null && password.length()!=0) {
				String temp = bean.getPasswd().trim();
				if(temp.equals(password)) {
					return bean;
				}else{
					return null;
				}
			} 
		}
		return null;
	}
}
