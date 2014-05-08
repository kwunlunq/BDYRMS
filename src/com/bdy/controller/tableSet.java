package com.bdy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyFloor;
import com.bdy.model.BdyTable;
import com.bdy.model.dao.BdyFloorDao;
import com.bdy.model.dao.BdyTableDao;
import com.bdy.service.KitchenService;

@WebServlet("/tableset")
public class tableSet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	KitchenService kservice;
	
	@Override
	public void init() throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		kservice = (KitchenService) context.getBean("KitchenService");	
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		BdyTableDao tableDao = (BdyTableDao) context.getBean("BdyTableDao");
		BdyFloorDao restDao = (BdyFloorDao) context.getBean("BdyFloorDao");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		String action = req.getParameter("act");
		int floor = -1;
		switch(action)
		{
			case "init":
				List<BdyFloor> rest = restDao.getRestById(1);
				String floorData = "";
				for(BdyFloor r : rest){
					floorData += r.getFloorid() + "," + r.getName() + ";";
				}
				out.print(floorData);
				break;
			case "save":
				floor = Integer.valueOf(req.getParameter("f"));
				String datas = null;
				String DTL = null;
				try {
					DTL = req.getParameter("DTL");
				} catch (Exception e) {
					DTL = null;
				}
				try {
					datas = req.getParameter("data");
				} catch (Exception e) {
					datas = null;
				}
				saveTable(datas,floor,DTL);
				break;
			case "load":
				floor = Integer.valueOf(req.getParameter("f"));
				List<BdyTable> tables = tableDao.getTableByFloor(floor);
		        JSONArray list = new JSONArray();
		        for(BdyTable table : tables){
		        	JSONObject obj = new JSONObject();
		        	obj.put("tbId", table.getTbId());
		        	obj.put("tbName", table.getName());
		        	obj.put("tbSize", table.getSize());
		        	obj.put("tbLocation", table.getLocation());
		        	obj.put("tbFloor", table.getBdyFloor());
		        	obj.put("tbState", table.getTableState());
		        	list.add(obj);
		        }
		        out.print(list);
				break;
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);
	}

	public void saveTable(String datas,int floor,String DTL){
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		BdyTableDao tableDao = (BdyTableDao) context.getBean("BdyTableDao");
		String DTLs[] = DTL.split(",");
		for(String ID : DTLs){
			if(ID != null && ID != "" && ID.length()>0 && !ID.startsWith("tb"))
			{
				int tbid = Integer.parseInt(ID);
				tableDao.deleteTableById(tbid);
			}
		}
		if (!datas.isEmpty() && datas != null) {
			String data[] = datas.split(";");
			JSONParser parser = new JSONParser();
			ContainerFactory containerFactory = new ContainerFactory() {
				@SuppressWarnings("rawtypes")
				public List<?> creatArrayContainer() {
					return new LinkedList();
				}

				@SuppressWarnings("rawtypes")
				public Map<?,?> createObjectContainer() {
					return new LinkedHashMap();
				}
			};
			for (int i = 0; i < data.length; i++) {
				String jsonText = data[i];
				try {
					@SuppressWarnings("unchecked")
					Map<String,String> json = (Map<String,String>) parser.parse(jsonText, containerFactory);
					BdyTable table = new BdyTable();
					
					table.setName(json.get("tbName"));
					table.setSize(Integer.parseInt(json.get("tbSize")));
					table.setLocation(json.get("pos"));
					table.setBdyFloor(Integer.parseInt(json.get("tbFloor")));
					table.setTableState(Integer.parseInt(json.get("tbState")));
					if(json.get("tbId").startsWith("tb")){
						table.setTbId(0);
						tableDao.saveTable(table);
					}else{
						table.setTbId(Integer.parseInt(json.get("tbId")));
						tableDao.updateTable(table);
					}
				} catch (ParseException pe) {
					System.out.println(pe);
				}
			}
		}else
		{
			System.out.println("No table can save");
		}
	}
}
