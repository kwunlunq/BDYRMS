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
@WebServlet("/order/GetMainServlet")
public class GetMainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	OrderService service;

//    public GetMainServlet() {WebApplicationContext context = 
//			WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
//		service = (OrderService) context.getBean("OrderService");
//    }
	@Override
	public void init() throws ServletException {
		WebApplicationContext context = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		service = (OrderService) context.getBean("OrderService");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(service.getMainsJSON().toString());
//		System.out.println(service.getMainsJSON().toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
