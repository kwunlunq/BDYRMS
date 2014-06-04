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

import com.bdy.model.BdyBillHistory;
import com.bdy.model.BdyOrderlistReport;
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
			errors.put("dateError", "請輸入一個日期來查詢");
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
				errors.put("dateError", "日期的型式必須是YYYY-MM-DD");
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
			List<BdyBillHistory> beans = new ArrayList<BdyBillHistory>();
			beans=service.getDayRevenue(date);
			if (beans == null||beans.isEmpty()) {
				errors.put("dateError", "查無此日資料!!");
				request.getRequestDispatcher("/report/daydetail.jsp").forward(request,
						response);
				return;
			} else {
				List<BdyOrderlistReport> mainkindNames = new ArrayList<BdyOrderlistReport>();
				mainkindNames=service.getMainkindNameByDate(date);
				request.setAttribute("mainkindNames", mainkindNames);
				List<BdyOrderlistReport> foodkindNames = new ArrayList<BdyOrderlistReport>();
				foodkindNames=service.getFoodkindNameByDate(date);
				request.setAttribute("foodkindNames", foodkindNames);
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
