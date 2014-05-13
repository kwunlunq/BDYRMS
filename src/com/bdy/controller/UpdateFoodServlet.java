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

import com.bdy.model.BdyDiscount;
import com.bdy.model.BdyFood;
import com.bdy.model.BdySetdetail;
import com.bdy.service.ManageService;
@WebServlet("/secure/update")
public class UpdateFoodServlet extends HttpServlet {
	
	ManageService foodservice;
	@Override
	public void init() throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		foodservice = (ManageService)context.getBean("ManageService");

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		List<BdySetdetail> detail = foodservice.getAllDetail();
		List<BdyDiscount> discount = foodservice.getAllDiscount();
		String ffdid = request.getParameter("fdid");
		String fname = request.getParameter("fname");
		String fprice = request.getParameter("fprice");
		String fqty = request.getParameter("fqty");
		String fdesc = request.getParameter("fdesc");
		String ffkid = request.getParameter("ffkind");

		Double price = Double.parseDouble(fprice);
		Integer qty = Integer.parseInt(fqty);
		Integer fkid = Integer.parseInt(ffkid);
		Integer fdid = Integer.parseInt(ffdid);
		

		foodservice.updateFood(fdid,fname, price, qty, fdesc, fkid);
		// request.getRequestDispatcher("/secure/manageIndex.jsp").forward(request,
		// response);
		List<BdyFood> foods = foodservice.getAllFood();
		request.setAttribute("resultFood", foods);
		request.setAttribute("resultDetail", detail);
		request.setAttribute("resultdiscount", discount);
		request.getRequestDispatcher("/secure/manageIndex.jsp").forward(request, response);
	}

	

}
