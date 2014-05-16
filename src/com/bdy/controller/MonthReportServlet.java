package com.bdy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.MonthReport;
import com.bdy.service.ReportService;



@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/MonthReportServlet")
public class MonthReportServlet extends HttpServlet {

	

	ReportService sevice ;
	

	@Override
	public void init() throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		this.sevice =(ReportService) context.getBean("ReportService");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 接收資料
		String col1 = request.getParameter("year");
		String col2 = request.getParameter("month");
		
		// 轉換資料
		int year = Integer.parseInt(col1);
		int month = Integer.parseInt(col2);
		
		//將資料導入頁面
		List<MonthReport> beans = new ArrayList<MonthReport>();
		
		try {
			beans=sevice.getMonthRevenueDetails(year, month);
		} catch (NamingException e) {
			e.printStackTrace();
		}

		request.setAttribute("bills", beans);
		request.getRequestDispatcher("/report/monthdetail.jsp").forward(request,
				response);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

			
}
