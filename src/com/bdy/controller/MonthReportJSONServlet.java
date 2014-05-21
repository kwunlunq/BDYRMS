package com.bdy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.bdy.service.ReportService;

@WebServlet("/report/MonthReportJSONServlet")
public class MonthReportJSONServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

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
		response.setContentType("text/plain;charset=UTF-8");
		// 接收資料
		String col1 = request.getParameter("year");
		String col2 = request.getParameter("month");
		// 驗證資料
		if (col1 == null || col2 == null || col1.trim().length() == 0
				|| col2.trim().length() == 0) {
		}

		// 轉換資料
		int year = 0;
		int month = 0;
		try {
			year = Integer.parseInt(col1);
			month = Integer.parseInt(col2);
		} catch (NumberFormatException e1) {
		}

		// 將資料導入頁面
		PrintWriter out = response.getWriter();	
		out.write(service.getSingleMonthJSON(year, month).toJSONString());
		
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
