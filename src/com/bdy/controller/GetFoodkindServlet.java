package com.bdy.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdySet;
import com.bdy.model.dao.BdySetDao;
import com.bdy.service.OrderService;

@WebServlet(
		urlPatterns={"/order/getFoodkindServlet"}
)

public class GetFoodkindServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	OrderService service;
	@Override
	public void init() throws ServletException {
		WebApplicationContext context = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		service = (OrderService) context.getBean("OrderService");
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(service.getFoodkindJson().toString());
		out.close();
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
