package com.bdy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
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
import com.bdy.model.BdyMainkind;
import com.bdy.model.BdyMakearea;
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
			String fkId = request.getParameter("fkId");
			List<BdyFoodkind> rest = optionservice.getAllFoodKind();
			Collections.sort(rest,new Comparator<BdyFoodkind>() {

				@Override
				public int compare(BdyFoodkind o1, BdyFoodkind o2) {
					return new Integer(o1.getFkId()).compareTo(new Integer(o2.getFkId()));
				}
				
			});
			String foodKind = id + "-";
			for (BdyFoodkind f : rest) {
				foodKind += f.getFkId() + "," + f.getName() +"," +f.getIsMain()+";";
			}
			foodKind += "-"+fkId;
			out.print(foodKind);
			break;
		case "setinit":
			String detailid = request.getParameter("detailid");
			String sid = request.getParameter("sid");
			String fkid = request.getParameter("fkid");
			List<BdyFoodkind> restFood = optionservice.getAllFoodKind();
			List<BdySet> restSet = optionservice.getAllSet();
			Collections.sort(restFood,new Comparator<BdyFoodkind>() {

				@Override
				public int compare(BdyFoodkind o1, BdyFoodkind o2) {
					return new Integer(o1.getFkId()).compareTo(new Integer(o2.getFkId()));
				}
				
			});
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
		case "insertFood":
			List<BdyFoodkind> insertFood = optionservice.getAllFoodKind();
			Collections.sort(insertFood,new Comparator<BdyFoodkind>(){

				@Override
				public int compare(BdyFoodkind o1, BdyFoodkind o2) {
					return new Integer(o1.getFkId()).compareTo(new Integer(o2.getFkId()));
				}
				
			});
			String resultInsertFood = "";
			for(BdyFoodkind f:insertFood){
				resultInsertFood += f.getFkId()+","+f.getName()+","+f.getIsMain()+";";
			}
			out.print(resultInsertFood);
			break;
		case "insertSet":
			List<BdyFoodkind> insertSetFood = optionservice.getAllFoodKind();
			List<BdySet> insertSetSet = optionservice.getAllSet();
			Collections.sort(insertSetFood,new Comparator<BdyFoodkind>() {

				@Override
				public int compare(BdyFoodkind o1, BdyFoodkind o2) {
					return new Integer(o1.getFkId()).compareTo(new Integer(o2.getFkId()));
				}
				
			});
			
			String resultInsertSet="";
			for(BdySet set:insertSetSet){
				resultInsertSet += set.getSetId()+","+set.getName()+";";
			}
			resultInsertSet += "-";
			for(BdyFoodkind fk:insertSetFood){
				resultInsertSet += fk.getFkId()+","+fk.getName()+";";
			}
			out.print(resultInsertSet);
			break;
		case "foodKindInit":
			String areafkId = request.getParameter("fkId");
			String areamaId = request.getParameter("maId");
			
			List<BdyMakearea> ma = optionservice.getAllMakeArea();
			String str = "";
			str +=areafkId+"-"+areamaId+"-";
			for(BdyMakearea m:ma){
				str +=m.getMaId()+","+m.getName()+";";
			}
			out.print(str);
			break;
		case "insertFoodKind":
			List<BdyMakearea> insertma = optionservice.getAllMakeArea();
			String resultFoodKind = "";
			for(BdyMakearea m:insertma){
				resultFoodKind += m.getMaId()+","+m.getName()+";";
			}
			out.print(resultFoodKind);
			break;
		//.......MK
		case "foodMK":
			String fdId = request.getParameter("fdId");
			String mkId = request.getParameter("mkId");
			List<BdyMainkind> mk = optionservice.getAllMainKind();
			Collections.sort(mk,new Comparator<BdyMainkind>(){

				@Override
				public int compare(BdyMainkind m1, BdyMainkind m2) {
					
					return new Integer(m1.getMkId()).compareTo(new Integer(m2.getMkId()));
				}
				
			});
			String resultMK = fdId+"-"+mkId+"-";
			for(BdyMainkind m:mk){
				resultMK +=m.getMkId()+","+m.getName()+";";
			}
			out.print(resultMK);
			break;
		case "insertMK":
			List<BdyMainkind> mkInsert = optionservice.getAllMainKind();
			Collections.sort(mkInsert,new Comparator<BdyMainkind>(){

				@Override
				public int compare(BdyMainkind m1, BdyMainkind m2) {
					
					return new Integer(m1.getMkId()).compareTo(new Integer(m2.getMkId()));
				}
				
			});
			String resutlInsertMK = "";
			for(BdyMainkind m:mkInsert){
				resutlInsertMK += m.getMkId()+","+m.getName()+";";
			}
			out.print(resutlInsertMK);
			break;
		}

	}

}
