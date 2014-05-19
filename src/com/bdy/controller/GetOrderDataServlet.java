package com.bdy.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyFood;
import com.bdy.service.OrderService;

/**
 * Servlet implementation class GetMainServlet
 */
@WebServlet("/order/getOrderDataServlet")
public class GetOrderDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	OrderService service;

	@Override
	public void init() throws ServletException {
		WebApplicationContext context = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		service = (OrderService) context.getBean("OrderService");
	}
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String data = request.getParameter("data");
		if (data == null) {
			System.out.println("Param not found! (\"data\")");
			return ;
		}
		switch (data) {
		case "table" :
			out.write(service.getTableJson().toString());
			break;
		case "food" :
			out.write(service.getFoodsJSON().toString());
			break;
		case "fk" :
			out.write(service.getFoodkindJson().toString());
			break;
		case "set" :
			out.write(service.getSetJson().toString());
			break;
		case "main" :
			out.write(service.getMainsJSON().toString());
			break;
		default :
			System.out.println("Wrong param!");
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
