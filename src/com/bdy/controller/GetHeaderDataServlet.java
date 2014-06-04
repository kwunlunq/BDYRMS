package com.bdy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeMap;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyDiscount;
import com.bdy.model.BdyFloor;
import com.bdy.model.BdyFood;
import com.bdy.model.BdyFoodkind;
import com.bdy.model.BdyMainkind;
import com.bdy.model.BdySet;
import com.bdy.model.BdySetdetail;
import com.bdy.model.BdyTable;
import com.bdy.service.ManageService;
import com.bdy.service.OrderService;

/**
 * Servlet implementation class GetMainServlet
 */
@WebServlet("/header/getHeaderDataServlet")
public class GetHeaderDataServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	OrderService service;
	ManageService manageService;

	@Override
	public void init() throws ServletException {
		WebApplicationContext context = 
			WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		service = (OrderService) context.getBean("OrderService");
		manageService = (ManageService) context.getBean("ManageService");
	}
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String data = request.getParameter("data");
		String empId = request.getParameter("empId");
		HttpSession session = request.getSession();
		
		if (data == null) {
			out.print("Param not found! (\"data\")");
			return ;
		}
		switch (data) {
		case "initial" :
			TreeMap<Integer, BdyMainkind> mks = getDataToSession(session, new Integer(0), new BdyMainkind(), "mks", "getAllMks");
			TreeMap<Integer, BdyFoodkind> fks = getDataToSession(session, new Integer(0), new BdyFoodkind(), "fks", "getAllFksSortedBySeq");
			TreeMap<Integer, BdyFood> foods = getDataToSession(session, new Integer(0), new BdyFood(), "foods", "getAllFoods");
			TreeMap<Integer, BdyTable> tables = getDataToSession(session, new Integer(0), new BdyTable(), "tables", "getAllTables");
			TreeMap<Integer, BdyFloor> floors = getDataToSession(session, new Integer(0), new BdyFloor(), "floors", "getAllFloors");
			TreeMap<Integer, BdySet> sets = getDataToSession(session, new Integer(0), new BdySet(), "sets", "getAllSets");
			TreeMap<Integer, BdySetdetail> sds = getDataToSession(session, new Integer(0), new BdySetdetail(), "sds", "getAllSortedSetdetails");
//			TreeMap<Integer, BdyDiscount> diss = getDataToSession(session, new Integer(0), new BdyDiscount(), "diss", "getAllDiscounts");
			break;
		case "bills":
			Calendar c = new GregorianCalendar();
		    c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
		    c.set(Calendar.MINUTE, 0);
		    c.set(Calendar.SECOND, 0);
		    Date today = c.getTime();
		    Integer billsCount = 0;
			try {
				billsCount = manageService.getTodayBills(today);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			out.write(billsCount.toString());
			break;
		case "updateTable" :
			session.removeAttribute("tables");
			session.removeAttribute("floors");
		case "table" :
			tables = getDataToSession(session, new Integer(0), new BdyTable(), "tables", "getAllTables");
			floors = getDataToSession(session, new Integer(0), new BdyFloor(), "floors", "getAllFloors");
			JsonArray table = service.makeJSONTables(tables, floors);
			out.write(table.toString());
			break;
		case "todayIncome" :
			out.print(service.getTodayIncome().toString());
			break;
		case "food" :
			foods = getDataToSession(session, new Integer(0), new BdyFood(), "foods", "getAllFoods");
			fks = getDataToSession(session, new Integer(0), new BdyFoodkind(), "fks", "getAllFksSortedBySeq");
			mks = getDataToSession(session, new Integer(0), new BdyMainkind(), "mks", "getAllMks");
			JsonObject food = service.makeJSONFoods(foods, fks, mks);
			out.write(food.toString());
			break;
		case "fk" :
			fks = getDataToSession(session, new Integer(0), new BdyFoodkind(), "fks", "getAllFksSortedBySeq");
			JsonArray fk = service.makeJSONFks(fks);
			out.write(fk.toString());
			break;
		case "set" :
			sets = getDataToSession(session, new Integer(0), new BdySet(), "sets", "getAllSets");
			sds = getDataToSession(session, new Integer(0), new BdySetdetail(), "sds", "getAllSortedSetdetails");
			JsonArray sd = service.makeJSONSets(sets, sds);
			out.write(sd.toString());
			break;
		case "updateDiscount" :
			session.removeAttribute("diss");
//			System.out.println("Session Cleared : discount");
		case "discount" :
			TreeMap<Integer, BdyDiscount> diss = getDataToSession(session, new Integer(0), new BdyDiscount(), "diss", "getAllDiscounts");
			JsonArray dis = service.makeJSONDiss(diss);
			out.write(dis.toString());
			break;
		case "orderNotCheckAndCustNum" :
			out.write(service.getOrderNotCheckAndCustNum().toString());
			break;
		case "emp" :
			session.removeAttribute("emp");
			getEmpToSession(session, empId);
			break;
		case "clear" :
			session.removeAttribute("foods");
			session.removeAttribute("fks");
			session.removeAttribute("mks");
			session.removeAttribute("sets");
			session.removeAttribute("sds");
			session.removeAttribute("tables");
			session.removeAttribute("floors");
			session.removeAttribute("emp");
			out.println("Session Cleared");
			System.out.println("Session Cleared");
			break;
		default :
			System.out.println("Wrong param!");
			break;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T1, T2> TreeMap<T1, T2> getDataToSession(HttpSession session,
			T1 t1, T2 t2, String dataName, String methodName) {
		TreeMap<T1, T2> datas = (TreeMap<T1, T2>) session.getAttribute(dataName);
		boolean flTb = false;
		if (dataName.equals("floors") || dataName.equals("tables")) {
			flTb = true;
		}
		if (datas==null || datas.size()==0) {
			if (!flTb) {
				System.out.println("Session Miss : "+dataName);
			}
			// 使用Reflect動態選擇method
			java.lang.reflect.Method method = null;
			try {
				// 建立在service.getClass()中, 名為methodName的method
				method = service.getClass().getMethod(methodName);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			try {
				// 呼叫method
				datas = (TreeMap<T1, T2>) method.invoke(service);
				session.setAttribute(dataName, datas);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			if (!flTb) {
				System.out.println("Session Hit : "+dataName);
			}
		}
		return datas;
	}
	
	public void getEmpToSession(HttpSession session , String empId) {
		if (session.getAttribute("emp") != null) {
			System.out.println("Session Hit : emp");
		} else if (empId != null) {
			System.out.println("Session Update : emp");
			session.setAttribute("emp", service.getEmp(empId));
		} else {
			System.out.println("No Emp ID");
			return ;
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
