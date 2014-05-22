package com.bdy.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyDiscount;
import com.bdy.model.BdyMainkind;
import com.bdy.model.BdyMakearea;
import com.bdy.model.BdySet;
import com.bdy.service.ManageService;
@WebServlet("/secure/deleteDis")
public class ManageDeleteDiscountServlet extends HttpServlet {

	ManageService service;
	@Override
	public void init() throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		service = (ManageService)context.getBean("ManageService");
				
	}

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String disId = request.getParameter("disId");
		int id  = Integer.parseInt(disId);
		int disState = service.deleteDisc(id);
		List<BdyDiscount> disc = service.getAllDiscount();
		List<BdySet> set = service.getAllSet();
		List<BdyMakearea> ma= service.getAllMakeArea();
		List<BdyMainkind> mk = service.getAllMainKind();
		request.setAttribute("mk", mk);
		request.setAttribute("disState", disState);
		request.setAttribute("disc", disc);
		request.setAttribute("set", set);
		request.setAttribute("ma", ma);
		
		request.getRequestDispatcher("/secure/manageInside.jsp").forward(request, response);
		
		
	}

	
	
}
