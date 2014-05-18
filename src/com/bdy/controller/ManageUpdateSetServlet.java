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
import com.bdy.model.BdyFoodkind;
import com.bdy.model.BdySetdetail;
import com.bdy.service.ManageService;

@WebServlet("/secure/updateset")
public class ManageUpdateSetServlet extends HttpServlet {
	
	
	ManageService setservice;
	@Override
	public void init() throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		setservice = (ManageService)context.getBean("ManageService");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<BdyFood> foods = setservice.getAllFood();
		List<BdyDiscount> discount = setservice.getAllDiscount();
		List<BdyFoodkind> foodkind = setservice.getAllFoodKind();

		
		String sdetailid = request.getParameter("sdetailId");
		String ssetid = request.getParameter("ssetId");
		String sfkid = request.getParameter("sfkId");
		
		Integer setdetailid = Integer.parseInt(sdetailid);
		Integer setsetid = Integer.parseInt(ssetid);
		Integer setfkid = Integer.parseInt(sfkid);
		request.setAttribute("pags", "1");
		List<BdySetdetail>  detail = setservice.updateSet(setdetailid, setsetid, setfkid);
		request.setAttribute("resultFood", foods);
		request.setAttribute("resultDetail", detail);
		request.setAttribute("resultdiscount", discount);
		request.setAttribute("resultfoodkind", foodkind);
		request.getRequestDispatcher("/secure/manageIndex.jsp").forward(request, response);
		
		
	}

	

}
