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
@WebServlet("/secure/deletefood")
public class ManageDeleteFoodServlet extends HttpServlet {
	

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
		
		String did = request.getParameter("fid");
		int id = Integer.parseInt(did);	
	
		int foodState = deleteService.deleteFood(id);
		List<BdySetdetail> detail = deleteService.getAllDetail();
		List<BdyDiscount> discount = deleteService.getAllDiscount();
		List<BdyFood> foods = deleteService.getAllFood();
		List<BdyFoodkind> foodkind = deleteService.getAllFoodKind();

			
		if(foods!=null){
			request.setAttribute("foodcount", foods.size());
			
		}
		else{
			request.setAttribute("foodcount","0");
		}
		request.setAttribute("resultFood", foods);
		request.setAttribute("resultDetail", detail);
		request.setAttribute("resultdiscount", discount);
		request.setAttribute("resultfoodkind", foodkind);
		
		request.getRequestDispatcher("/secure/manageIndex.jsp").forward(request, response);
		//String path = request.getContextPath();
		//response.sendRedirect(path+"/secure/sort?act=show&del="+foodState);
		
	}

}
