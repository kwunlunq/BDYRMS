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

import com.bdy.model.BdyDiscount;
import com.bdy.service.ManageService;

@WebServlet(urlPatterns = { "/secure/getDisCount" })
public class GetDiscountServlet extends HttpServlet {

	ManageService service;

	@Override
	public void init() throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
		service = (ManageService) context.getBean("ManageService");
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/application;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String dis = request.getParameter("disId");
		Integer dsiId = Integer.valueOf(dis);
		if (dsiId == -1) {
			out.print(1.0);
		} else {
			BdyDiscount disPer = service.getDiscountById(dsiId);
			out.print(disPer.getDisPrice());
		}

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
