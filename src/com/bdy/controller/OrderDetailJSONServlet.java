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

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/report/OrderDetailJSONServlet")
public class OrderDetailJSONServlet extends HttpServlet {

	ReportService service;

	public void init() throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		service = (ReportService) context.getBean("ReportService");
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain;charset=UTF-8");
		// 接收資料
		String col = request.getParameter("billId");

		// 轉換資料
		int billId = 0;
		try {
			billId = Integer.parseInt(col);
		} catch (NumberFormatException e1) {
		}

		// 將資料導入頁面
		PrintWriter out = response.getWriter();
		out.write(service.getOrderDetailByBillIdJSON(billId).toJSONString());
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
