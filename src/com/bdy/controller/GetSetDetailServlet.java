package com.bdy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyFoodkind;
import com.bdy.model.BdySet;
import com.bdy.model.BdySetdetail;
import com.bdy.service.OrderService;

@WebServlet(
		urlPatterns={"/order/getSetDetailServlet"}
)
public class GetSetDetailServlet extends HttpServlet {
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
		int setId = 0;
		try {
			setId = Integer.parseInt(request.getParameter("setId"));
		} catch (NumberFormatException e) {
			System.out.println("No parameter found! (setId=?)");
		}
		out.write(service.getSetDetailBySetIdJSON(setId).toString());
	}
	

	public void test(HttpServletRequest request,
			 HttpServletResponse response) {
		int setId = 0;
		
		try {
			setId = Integer.parseInt(request.getParameter("setId"));
		} catch (NumberFormatException e1) {
			System.out.println("parameter not found : setId");
//			e1.printStackTrace();
		}
		System.out.println(setId);
		List<BdySet> sets = service.getSet();
		List<BdyFoodkind> fks = service.getFoodkinds();
		int setNum = sets.size();
		int fkNum = fks.size();
		System.out.println("==============");
		JsonArrayBuilder setAry = Json.createArrayBuilder();

		/*
		 * 目標JSON物件陣列 : 
		 * [{"納帕套餐"  : {"前菜":2, "主餐":1, "甜點":3}, 
		 *  {"奧克蘭套餐": {"主餐":2, "飲料":2}]
		 *  利用setAry建立外層陣列 [ ]
		 */
		for (int i = 1; i <= setNum; i++) {
			String setName = sets.get(i-1).getName();
			System.out.println(setName);
			
			/*
			 *  建立每份主餐的內容, 存成一個JSON物件
			 *  {"前菜":2, "主餐":1, "甜點":3}
			 */
			JsonObjectBuilder fkobj = Json.createObjectBuilder();
			for (int j = 1; j <= fkNum; j++) {
				int num = (int) service.groupTest(i, j);
				if (num != 0) {
					System.out.print("\t");
					String fkName = fks.get(j-1).getName();
					System.out.print(fks.get(j-1).getName());
					System.out.print(num);
					System.out.println("份 ");
					fkobj.add(fkName, num);
				}
			}
			System.out.println("==============");
			
			/*
			 * 套餐名稱作為key
			 * 上面建立的物件fkobj作為value
			 * 建立新的JSON物件
			 * {"納帕套餐"  : {"前菜":2, "主餐":1, "甜點":3}
			 * 加入到外層setAry中
			 * 			--> [  {"納帕套餐"  : {"前菜":2, "主餐":1, "甜點":3}
			 * 				]
			 */
			
			setAry.add(Json.createObjectBuilder()
					       .add(setName, fkobj));
		}
		
		try {
			response.setContentType("text/plain;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(setAry.build().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	public void getJsonSetDetail(HttpServletRequest request, HttpServletResponse response) {
//		service.getSetDetailBySetId(1);
//		return ;
//	}
	@Override
	protected void doPost(HttpServletRequest req,
						HttpServletResponse resp)
								throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
