package com.bdy.service;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

import com.bdy.model.BdyFloor;
import com.bdy.model.BdyTable;
import com.bdy.model.dao.BdyFloorDao;
import com.bdy.model.dao.BdyTableDao;

public class TableService {

	BdyFloorDao floorDao;
	BdyTableDao tableDao;
	
	public BdyFloorDao getFloorDao() {
		return floorDao;
	}
	public void setFloorDao(BdyFloorDao floorDao) {
		this.floorDao = floorDao;
	}
	public BdyTableDao getTableDao() {
		return tableDao;
	}
	public void setTableDao(BdyTableDao tableDao) {
		this.tableDao = tableDao;
	}
	
	public JsonArray getFloorListInJson(){
		System.out.println("TSS Strat to getFloorListInJson()");
		List<BdyFloor> floorList = floorDao.getAllFloor();
		JsonArrayBuilder floorBuilder = Json.createArrayBuilder();
		for(BdyFloor floorBean : floorList){
			floorBuilder.add(Json.createObjectBuilder()
					.add("floorId", floorBean.getFloorid())
					.add("floorName", floorBean.getName())
				);
		}
        System.out.println("TSE Return data :");
        //System.out.println("TSE " +  floorBuilder.build().toString());
        return floorBuilder.build();
	}
	
	public JsonArray getTableByFloorInJson(int floor){
		System.out.println("TSS Strat to getTableByFloorInJson(int floor) [ floor = " + floor+" ]");
		List<BdyTable> tables =  tableDao.getTableByFloor(floor);
		JsonArrayBuilder tableBuilder = Json.createArrayBuilder();
		for(BdyTable tableBean : tables){
			tableBuilder.add(Json.createObjectBuilder()
								.add("tbId", tableBean.getTbId())
								.add("tbName", tableBean.getName())
								.add("tbSize", tableBean.getSize())
								.add("tbLocation", tableBean.getLocation())
								.add("tbFloor", tableBean.getBdyFloor())
								.add("tbState", tableBean.getTableState())
							);
		}
        System.out.println("TSE Return data : ");
        //System.out.println("TSE " +  tableBuilder.build().toString());
        return tableBuilder.build();
	}
	
	
	public void saveTableByJson(JsonArray tables,int floor,JsonArray DTL){
		System.out.println("TSS Strat to saveTableByJson(JsonArray tables,int floor,JsonArray DTL)");
		System.out.println("TSS [ tables = "+tables.toString()+" ]");
		System.out.println("TSS [ floor = "+floor+" ]");
		System.out.println("TSS [ DTL = "+DTL.toString()+" ]");
		for(int i=0; i< DTL.size();i++){
			if(DTL.getString(i) != null && DTL.getString(i) != "" && DTL.getString(i).length()>0 && !DTL.getString(i).startsWith("tb"))
			{
				int tbid = Integer.parseInt(DTL.getString(i));
				tableDao.deleteTableById(tbid);
			}
		}
		if (!tables.isEmpty() && tables != null) {
			for (int i = 0; i < tables.size(); i++) {
				JsonObject tableJson = tables.getJsonObject(i);
				BdyTable table = new BdyTable();
				table.setName(tableJson.getString("tbName"));
				table.setSize(Integer.parseInt(tableJson.getString("tbSize")));
				table.setLocation(tableJson.getString("pos"));
				table.setBdyFloor(Integer.parseInt(tableJson.getString("tbFloor")));
				table.setTableState(Integer.parseInt(tableJson.getString("tbState")));
				if(tableJson.getString("tbId").startsWith("tb")){
					table.setTbId(0);
					tableDao.saveTable(table);
				}else{
					table.setTbId(Integer.parseInt(tableJson.getString("tbId")));
					tableDao.updateTable(table);
				}
			}
			System.out.println("TSE Table save done");
		}else
		{
			System.out.println("TSE No table can save");
		}
        System.out.println("TSE Not need return");
	}
}
