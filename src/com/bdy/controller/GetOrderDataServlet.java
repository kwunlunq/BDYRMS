package com.bdy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
@WebServlet("/order/getOrderDataServlet")
public class GetOrderDataServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static final int CUT_LENGTH = 4000;

	OrderService service;

	@Override
	public void init() throws ServletException {
		WebApplicationContext context = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		service = (OrderService) context.getBean("OrderService");
	}
	

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String foods = null;
		String sets = null;
		String tables = null;
		String fks = null;
		String data = request.getParameter("data");
		String empId = request.getParameter("empId");
		if (data == null) {
			System.out.println("Param not found! (\"data\")");
			out.print("Param not found! (\"data\")");
			return ;
		}
		switch (data) {
		case "table" :
//			out.write(service.getTableJson().toString());
//			tables = findCookie("tables", request);
//			if (tables == null) { // 沒有在cookie中, 向資料庫查詢
//				System.out.println("tables not found");
				tables = service.getTableJson().toString();
				makeCookie("tables", tables, response);
//			}
			System.out.println(tables);
			out.write(tables);
			break;
		case "food" :
			System.out.println("making cookie");
//			foods = findCookie("foods", request);
//			if (foods == null) { // 沒有在cookie中, 向資料庫查詢
//				System.out.println("foods not found");
				foods = service.getFoodsJSON().toString();
				makeCookie("foods", foods, response);
//			}
//			System.out.println(foods);
//			out.write("foods");
			out.write(foods);
			break;
		case "fk" :
//			out.write(service.getFoodkindJson().toString());
//			fks = findCookie("fks", request);
//			if (fks == null) { // 沒有在cookie中, 向資料庫查詢
//				System.out.println("fks not found");
				fks = service.getFoodkindJson().toString();
				makeCookie("fks", fks, response);
//			}
			System.out.println(fks);
			out.write(fks);
			break;
		case "set" :
//			out.write(service.getSetJson().toString());
//			sets = findCookie("sets", request);
//			if (sets == null) { // 沒有在cookie中, 向資料庫查詢
//				System.out.println("sets not found");
				sets = service.getSetJson().toString();
				makeCookie("sets", sets, response);
//			}
			System.out.println(sets);
			out.write(sets);
			break;
		case "main" :
			out.write(service.getMainsJSON().toString());
			break;
		case "sessionFood" :
			HttpSession session = request.getSession();
			

			HashMap<Integer, BdyFood> sessionfood = (HashMap<Integer, BdyFood>) session.getAttribute("foods");
			if (sessionfood==null || sessionfood.size()==0) {
				System.out.println("Session Miss : Food");
				session.setAttribute("foods", service.getAllFood());
			} else {
				System.out.println("Session Hit : Food");
			}
			System.out.println("Session Complete : Food");
			break;
		case "sessionSet" :
			session = request.getSession();

			HashMap<Integer, BdySet> sessionset = (HashMap<Integer, BdySet>) session.getAttribute("sets");
			if (sessionset==null || sessionset.size()==0) {
				System.out.println("Session Miss : Set");
				session.setAttribute("sets", service.getAllSet());
			} else {
				System.out.println("Session Hit : Set");
			}
			System.out.println("Session Complete : Set");
			break;
		case "sessionEmp" :
			session = request.getSession();
			

			BdyEmp sessionemp = (BdyEmp) session.getAttribute("emp");
			if (sessionemp==null) {
				System.out.println("Session Miss : Emp");
				session.setAttribute("emp", service.getEmp(empId));
			} else {
				System.out.println("Session Hit : Emp");
			}
			
			System.out.println("Session Complete : Emp");
			break;
		case "sessionTable" :
			session = request.getSession();

			HashMap<Integer, BdyTable> sessiontable = (HashMap<Integer, BdyTable>) session.getAttribute("tables");
			
			if (sessiontable==null || sessiontable.size()==0) {
				System.out.println("Session Miss : Table");
				session.setAttribute("tables", service.getAllTable());
			} else {
				System.out.println("Session Hit : Table");
			}
			System.out.println("Session Complete : Table");
			break;
		default :
			System.out.println("Wrong param!");
			break;
		}
	}
	public String findCookie(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String result = null;
		for (int i = 0; cookies != null && i < cookies.length; i++) {
			//遍历cookie数组，找到含有name的key的cookie。
			if (name.equals(cookies[i].getName())) {
				result = cookies[i].getValue();
				//得到cookie的值后必须，进行Base64解码，因为前次生成cookie时，value是经过Base64编码。
//				result = Base64Coder.decodeString(result);  //进行Base64解码
			}
		}
//		System.out.println(name+" in cookie : ");
//		System.out.println(result);
//		System.out.println("----------");
		return result;
	}
	public void makeCookie(String name, String value, HttpServletResponse response) {
		try {
			value = java.net.URLEncoder.encode(value, "UTF-8");
			int nameLength = name.length();
			int totalLength = 0;
			int count = 0;
			String orivalue = value;
			String concatevalue = "";
			if (value.length() >= CUT_LENGTH) {
				System.out.println("原長:"+value.length());
				while (value.length() > 0) {
					String smallvalue;
					if (value.length() >= CUT_LENGTH) {
						smallvalue = value.substring(0, CUT_LENGTH);
//						value = value.substring(CUT_LENGTH);
					} else {
						smallvalue = value;
//						value = "";
					}
					concatevalue += smallvalue;
					value = value.substring(smallvalue.length());
	//				System.out.println("Cookie長度超過"+CUT_LENGTH+"!");
					System.out.println("長度 = "+value.length());
					System.out.println(CUT_LENGTH+"/"+value.length()+"/"+count);
					System.out.println("長度 = "+value.length());
	//				System.out.println("small length:"+smallvalue.length());
	//				System.out.println("cookie length => "+value.length());
					
					//value = Base64Coder.encodeString(value); 
					
					// 第一個切割的cookie, 在後面加上-0
					if (name.length() == nameLength) {
						name = name + "-" + count;
					} else {
					// 之後換成別的count
						name = name.substring(0, name.length()-1);
						name += count;
					}
					
	
					System.out.println(name+":");
					System.out.println("URL MODE => " +smallvalue);
					
					totalLength += smallvalue.length();
					System.out.println(count + " : "+smallvalue.length());

					if (count == 0) {
						// 建立cookie
						Cookie cookie = new Cookie(name,smallvalue);
						cookie.setMaxAge(3*60*60); //3 hour
						response.addCookie(cookie);
//						break;
					}
					count ++;
				}
				System.out.println("後總長:"+totalLength);
				System.out.println("後原長:"+value.length());
				if (orivalue.equals(concatevalue)) {
					System.out.println("EQUAL!");
				}
			} else {
				Cookie cookie = new Cookie(name,value);
				cookie.setMaxAge(3*60*60); //3 hour
				response.addCookie(cookie);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
