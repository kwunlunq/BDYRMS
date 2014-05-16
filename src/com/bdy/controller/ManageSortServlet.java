package com.bdy.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
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

@WebServlet("/secure/sort")
public class ManageSortServlet extends HttpServlet {
	
	ManageService manage;
	boolean booleanFoodPrice = true;
	boolean booleanQty = true;
	public void init() throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		manage = (ManageService) context.getBean("ManageService");	
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		List<BdyFood> food = manage.getAllFood();
		List<BdySetdetail> detail = manage.getAllDetail();
		List<BdyDiscount> discount = manage.getAllDiscount();
		List<BdyFoodkind> foodkind = manage.getAllFoodKind();
		String act = request.getParameter("act");		
		//排序價位 和 庫存star
		if(act.equals("sort")){
			String type = request.getParameter("type");
		if(type.equals("price")){
		if(booleanFoodPrice==true){
		Collections.sort(food,new Comparator<BdyFood>() {

			@Override
			public int compare(BdyFood price,BdyFood price2) {
				
				return (int) (price2.getPrice()-price.getPrice());
			}
		});
		booleanFoodPrice=false;
		}else{
			Collections.sort(food, new Comparator<BdyFood>(){

				@Override
				public int compare(BdyFood price, BdyFood price2) {

					return (int) (price.getPrice()-price2.getPrice());
				}
				
			});
			booleanFoodPrice=true;
		}
		}
		if(type.equals("qty")){
		if(booleanQty==true){
			Collections.sort(food,new Comparator<BdyFood>(){

				@Override
				public int compare(BdyFood qty, BdyFood qty1) {
					return qty1.getQty()-qty.getQty();
				}				
			});
			booleanQty=false;
		}else{
			Collections.sort(food,new Comparator<BdyFood>(){

				@Override
				public int compare(BdyFood qty, BdyFood qty1) {
					return qty.getQty()-qty1.getQty();
				}				
			});
			booleanQty=true;
		}
		}
		}
		

		Collections.sort(foodkind,new Comparator<BdyFoodkind>(){
			@Override
			public int compare(BdyFoodkind o1, BdyFoodkind o2) {
				
				return new Integer(o1.getFkId()).compareTo(new Integer(o2.getFkId()));
			}
		});
		//排序價位 和 庫存end
		request.setAttribute("resultFood", food);
		request.setAttribute("resultDetail", detail);
		request.setAttribute("resultdiscount", discount);
		request.setAttribute("resultfoodkind", foodkind);
		
		if(food!=null){
			request.setAttribute("foodcount", food.size());
			
		}
		else{
			request.setAttribute("foodcount","0");
		}
		request.getRequestDispatcher("/secure/manageIndex.jsp").forward(request, response);
	}

	
}
