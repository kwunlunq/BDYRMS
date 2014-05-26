package com.bdy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TreeMap;
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
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyEmp;
import com.bdy.model.BdyFood;
import com.bdy.model.BdySet;
import com.bdy.model.BdyTable;
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

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonReader jsonReader = Json.createReader(request.getReader());
		JsonObject object = jsonReader.readObject();
		jsonReader.close();
		HttpSession session = request.getSession();
		TreeMap<Integer, BdyFood> foods = (TreeMap<Integer, BdyFood>) session.getAttribute("foods");
		TreeMap<Integer, BdySet> sets = (TreeMap<Integer, BdySet>) session.getAttribute("sets");
		TreeMap<Integer, BdyTable> tables = (TreeMap<Integer, BdyTable>) session.getAttribute("tables");
		BdyEmp emp = (BdyEmp) session.getAttribute("emp");
		
		service.readOrderJson(object, foods, sets, tables, emp);
		  
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write("success!");

	}

}
