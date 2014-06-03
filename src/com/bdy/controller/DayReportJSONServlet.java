package com.bdy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.service.ReportService;

@WebServlet("/report/DayReportJSONServlet")
public class DayReportJSONServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	ReportService service;

	@Override
	public void init() throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		service = (ReportService) context.getBean("ReportService");
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain;charset=UTF-8");

		String col = request.getParameter("date");
		if (col == null || col.trim().length() == 0) {
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;

		try {
			date = sdf.parse(col);
		} catch (ParseException e) {
		}
		PrintWriter out = response.getWriter();
		out.write(service.getSingleDayJSON(date).toJSONString());
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
