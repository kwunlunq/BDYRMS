package com.bdy.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyDiscount;
import com.bdy.model.BdyFood;
import com.bdy.model.BdyFoodkind;
import com.bdy.model.BdySetdetail;
import com.bdy.model.FoodKindPrice;
import com.bdy.service.ManageService;
@WebServlet("/setmeal/setInsert")
public class SetMealInsertServlet extends HttpServlet {
	
	ManageService service;
	@Override
	public void init() throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		service = (ManageService)context.getBean("ManageService");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String setId = request.getParameter("setId");
		//System.out.println("setId = "+setId);
		String btnname = request.getParameter("buttonName");
		System.out.println("btn name==="+btnname);
		String name = request.getParameter("setName");
		String sprice = request.getParameter("setPriceName");
		
		Double setprice = Double.valueOf(sprice);
		String check[]=request.getParameterValues("checkname");
		
		List<FoodKindPrice> prices = new LinkedList<FoodKindPrice>();
		switch(btnname){
		case "確定":
		
		
		for(int i=0;i<check.length;i++){
			Integer var= Integer.valueOf(check[i]);
			String price=request.getParameter("price"+var);
			
			//System.out.println("fkID=" + var+"price="+Double.valueOf(price));
			FoodKindPrice bean = new FoodKindPrice();
			bean.setFkId(var);
			bean.setPirce(Double.valueOf(price));
			prices.add(bean);
		}
		service.insertSet(name,setprice);
		service.insertSetDetail(prices);break;
		case "確認更改":	
			Integer sId = Integer.valueOf(setId);
			for(int i=0;i<check.length;i++){
				//System.out.println(check[i]); 抓fkId
				Integer var= Integer.valueOf(check[i]);
				String price=request.getParameter("price"+var);
				//System.out.println("fkID=" + var+"price="+Double.valueOf(price));
				FoodKindPrice bean = new FoodKindPrice();
				bean.setFkId(var);
				bean.setPirce(Double.valueOf(price));
				prices.add(bean);
			}
			service.updateSetMeal(sId,name,setprice);
			service.updateSetDetail(sId,prices);
			break;
		}
		List<BdyDiscount> discount = service.getAllDiscount();
		List<BdyFood> foods = service.getAllFood();
		List<BdySetdetail> detail = service.getAllDetail();
		List<BdyFoodkind> foodkind = service.getAllFoodKind();
		request.setAttribute("resultFood", foods);
		request.setAttribute("resultDetail", detail);
		request.setAttribute("resultdiscount", discount);
		request.setAttribute("resultfoodkind", foodkind);
		request.setAttribute("pags", 1);
		request.getRequestDispatcher("/secure/manageIndex.jsp").forward(request, response);
		
	}

	
}
