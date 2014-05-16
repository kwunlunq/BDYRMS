package com.bdy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.service.OrderService;

/**
 * Servlet implementation class GetMainServlet
 */
@WebServlet("/order/SendOrderServlet")
public class SendOrderServlet extends HttpServlet {
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
		out.write("success!");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonReader jsonReader = Json.createReader(request.getReader());
		JsonObject object = jsonReader.readObject();
		jsonReader.close();
		  
		service.readOrderJson(object);
		  
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write("success!");

	}

}
