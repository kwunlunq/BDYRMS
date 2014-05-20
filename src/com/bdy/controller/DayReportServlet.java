package com.bdy.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.bdy.model.BdyBill;
import com.bdy.service.ReportService;



@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/report/DayReportServlet")
public class DayReportServlet extends HttpServlet {
	ReportService service;
	public void init() throws ServletException {
			
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		service=(ReportService)context.getBean("ReportService");
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// 接收資料
		String col = request.getParameter("date");

		// 驗證資料
		Map<String, String> errors = new HashMap<String, String>();
		request.setAttribute("errorMsgs", errors);

		if (col == null || col.trim().length() == 0) {
			errors.put("dateError", "Please enter date for select");
			request.getRequestDispatcher("/report/daydetail.jsp").forward(request,
					response);
			return;
		}

		// 轉換資料
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		try {
			date = sdf.parse(col);
		} catch (ParseException e) {
				errors.put("dateError", "Date must be a date of YYYY-MM-DD");
				request.getRequestDispatcher("/report/daydetail.jsp").forward(request,
						response);
				return;
		}

	
		// 呼叫Model、根據Model執行結果導向View
		if (errors != null && !errors.isEmpty()) {
			request.getRequestDispatcher("/report/daydetail.jsp").forward(request,
					response);
			return;
		} else {
			
			
			List<BdyBill> beans = new ArrayList<BdyBill>();
			beans=service.getDayRevenueDetails(date);
			if (beans == null||beans.isEmpty()) {
				errors.put("dateError", "No data!!");
				request.getRequestDispatcher("/report/daydetail.jsp").forward(request,
						response);
				return;
			} else {
				
				request.setAttribute("bills", beans);
				request.getRequestDispatcher("/report/daydetail.jsp").forward(request,
						response);
			}
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
