package com.bdy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyFoodkind;
import com.bdy.model.MonthReport;
import com.bdy.service.ReportService;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/report/MonthReportServlet")
public class MonthReportServlet extends HttpServlet {

	ReportService service;

	@Override
	public void init() throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		this.service = (ReportService) context.getBean("ReportService");
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 接收資料
		String col1 = request.getParameter("year");
		String col2 = request.getParameter("month");

		// 驗證資料
		Map<String, String> errors = new HashMap<String, String>();
		request.setAttribute("errorMsgs", errors);

		if (col1 == null || col2 == null || col1.trim().length() == 0|| col2.trim().length() == 0) {
			errors.put("dateError", "Please select a year and a month");
			request.getRequestDispatcher("/report/monthdetail.jsp").forward(
					request, response);
			return;
		}

		// 轉換資料
		int year = 0;
		int month = 0;
		try {
			year = Integer.parseInt(col1);
			month = Integer.parseInt(col2);
		} catch (NumberFormatException e1) {
			errors.put("dateError", "Year and month must be integer");
			request.getRequestDispatcher("/report/monthdetail.jsp").forward(
					request, response);
			return;
		}
		

		// 將資料導入頁面
		List<MonthReport> beans = new ArrayList<MonthReport>();

		try {
			beans = service.getMonthRevenueDetails(year, month);
		} catch (NamingException e) {
		}
		if (beans == null || beans.isEmpty()) {
			errors.put("dateError", "No data!!");
			request.getRequestDispatcher("/report/monthdetail.jsp").forward(
					request, response);
			return;
		} else {
			request.setAttribute("bills", beans);
			request.getRequestDispatcher("/report/monthdetail.jsp").forward(
					request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}


}
