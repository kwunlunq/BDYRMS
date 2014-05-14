package com.bdy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyFloor;
import com.bdy.model.BdyFood;
import com.bdy.model.BdyFoodkind;
import com.bdy.model.BdySet;
import com.bdy.model.dao.BdyFloorDao;
import com.bdy.model.dao.BdyFoodDao;
import com.bdy.model.dao.BdyFoodkindDao;
import com.bdy.service.ManageService;

@WebServlet("/secure/option")
public class ManageOptionServlet extends HttpServlet {

	ManageService optionservice;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		optionservice = (ManageService)context.getBean("ManageService");
		PrintWriter out = response.getWriter();		
		String action = request.getParameter("act");
		switch (action) {
		case "foodinit":
			String id = request.getParameter("id");
			List<BdyFoodkind> rest = optionservice.getAllFoodKind();
			String foodKind = id + "-";
			for (BdyFoodkind f : rest) {
				foodKind += f.getFkId() + "," + f.getName() + ";";
			}
			out.print(foodKind);
			break;
		case "setinit":
			String detailid = request.getParameter("detailid");
			String sid = request.getParameter("sid");
			String fkid = request.getParameter("fkid");
			List<BdyFoodkind> restFood = optionservice.getAllFoodKind();
			List<BdySet> restSet = optionservice.getAllSet();
			String resultSet =detailid +"-"+ sid+"-"+fkid+"-";
			for(BdySet s : restSet){
				resultSet += s.getSetId()+","+s.getName()+";";
			}
			resultSet +="-";
			for(BdyFoodkind f : restFood){
				resultSet +=f.getFkId()+","+f.getName()+";";
			}
			out.print(resultSet);
			break;
		}

	}

}
