package com.bdy.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.service.TableService;

@WebServlet("/table/tableset")
public class TableSetServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	TableService TS;

	@Override
	public void init() throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		TS = (TableService)context.getBean("TableService");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		JsonReader jsonReader = Json.createReader(req.getReader());
		JsonObject object = jsonReader.readObject();
		String action = object.getString("act");
		int floor = Integer.parseInt(object.getString("floor"));
		switch(action)
		{
			case "init":
				JsonArray floorList = TS.getFloorListInJson();
				out.print(floorList.toString());
				break;
			case "save":
				JsonArray tables = object.getJsonArray("tables");
				JsonArray DTL = object.getJsonArray("delTBlist");
				TS.saveTableByJson(tables, floor, DTL);
				break;
			case "load":
				JsonArray tableList = TS.getTableByFloorInJson(floor);
		        out.print(tableList.toString());
				break;
			case "addFloor":
				String floorName = object.getString("floorName");
				TS.insertFloor(floorName);
				break;
			case "updateFloor":
				JsonArray updateFloorList = object.getJsonArray("floorList");
				JsonArray delFloorList = object.getJsonArray("delFloorList");
				TS.updateFloor(updateFloorList, delFloorList);
				break;
			case "tbOpen":
				int tbId = Integer.parseInt(object.getString("tbId"));
				int tbState = object.getInt("tbState");
				int custNum = Integer.parseInt(object.getString("custNum"));
				TS.setTbToOpenState(tbId, tbState, custNum);
				break;
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);
	}
}
