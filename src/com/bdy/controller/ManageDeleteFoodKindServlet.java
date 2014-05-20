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
@WebServlet("/secure/deletefoodkind")
public class ManageDeleteFoodKindServlet extends HttpServlet {

	
	ManageService service;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			String fkId = request.getParameter("fkId");
			int id = Integer.parseInt(fkId);
			System.out.println("run this delete");
			int fkState = service.deleteFoodKind(id);
			List<BdySetdetail> detail = service.getAllDetail();
			List<BdyDiscount> discount = service.getAllDiscount();
			List<BdyFood> foods = service.getAllFood();
			List<BdyFoodkind> foodkind = service.getAllFoodKind();
			
			request.setAttribute("pags", "2");
			request.setAttribute("fkState", fkState);
			request.setAttribute("resultFood", foods);
			request.setAttribute("resultDetail", detail);
			request.setAttribute("resultdiscount", discount);
			request.setAttribute("resultfoodkind", foodkind);
			System.out.println("run fkdelete success");
			//String path = request.getContextPath();
			//response.sendRedirect(path+"/secure/sort?act=show");
			
			request.getRequestDispatcher("/secure/manageIndex.jsp").forward(request, response);
	}

	@Override
	public void init() throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		service = (ManageService)context.getBean("ManageService");
	}

	
}
