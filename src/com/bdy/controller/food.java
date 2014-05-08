package com.bdy.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyFood;
import com.bdy.service.ManageService;

@WebServlet("/secure/food")
public class food extends HttpServlet {

	
	ManageService manage;
	public void init() throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		manage = (ManageService) context.getBean("ManageService");	
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		response.setCharacterEncoding("UTF-8");
		String path=request.getContextPath();
		List<BdyFood> food = manage.getAllFood();
		System.out.println(food+"111");
		request.setAttribute("resultfood", food);
		if(food!=null){
			request.setAttribute("count", food.size());
		}
		else{
			request.setAttribute("count","0");
		}
		request.getRequestDispatcher("/secure/manageIndex.jsp").forward(request, response);
	}

	
}
