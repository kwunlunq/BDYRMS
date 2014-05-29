package com.bdy.controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
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

@WebServlet("/setmeal/deleteset")
public class SetMealDeleteSetServlet extends HttpServlet {
	
	
	ManageService deleteService;
	

	@Override
	public void init() throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		deleteService = (ManageService)context.getBean("ManageService");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<BdyDiscount> discount = deleteService.getAllDiscount();
		List<BdyFood> foods = deleteService.getAllFood();
		List<BdyFoodkind> foodkind = deleteService.getAllFoodKind();

		String detailId = request.getParameter("setId");
		Integer id = Integer.parseInt(detailId);
		deleteService.deleteSetDetail(id);
		deleteService.deleteSet(id);
		List<BdySetdetail> detail = deleteService.getAllDetail();
		request.setAttribute("pags", "1");
		request.setAttribute("resultFood", foods);
		request.setAttribute("resultDetail", detail);
		request.setAttribute("resultdiscount", discount);
		request.setAttribute("resultfoodkind", foodkind);
		request.getRequestDispatcher("/secure/manageIndex.jsp").forward(request, response);
	}

}
