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
        JsonArray data = floorBuilder.build();
        System.out.println("TSE " +  data.toString());
        return data;
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
								.add("tbFloor", tableBean.getBdyFloor().getFloorid())
								.add("tbState", tableBean.getTableState())
								.add("custNum", tableBean.getCustNum())
							);
		}
        System.out.println("TSE Return data : ");
        JsonArray data = tableBuilder.build();
        System.out.println("TSE " +  data.toString());
        return data;
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
				BdyFloor floorBean = new BdyFloor();
				floorBean.setFloorid(Integer.parseInt(tableJson.getString("tbFloor")));
				table.setName(tableJson.getString("tbName"));
				table.setSize(Integer.parseInt(tableJson.getString("tbSize")));
				table.setLocation(tableJson.getString("pos"));
				table.setBdyFloor(floorBean);
				table.setTableState(Integer.parseInt(tableJson.getString("tbState")));
				if(tableJson.getString("tbId").startsWith("tb")){
					table.setTbId(0);
					tableDao.saveTable(table);
				}else{
					table.setTbId(Integer.parseInt(tableJson.getString("tbId")));
					tableDao.updateTable(table);
				}
			}
		}else
		{
			System.out.println("TSE No table can save");
		}
		System.out.println("TSE saveTableByJson(JsonArray tables,int floor,JsonArray DTL) done");
	}
	
	public void insertFloor(String floorName){
		System.out.println("TSS Strat to insertFloor(String floorName)");
		System.out.println("TSS [ floorName = "+floorName+" ]");
		BdyFloor floorBean = new BdyFloor();
		floorBean.setName(floorName);
		floorBean.setSort(9999);
		floorDao.insertFloor(floorBean);
		System.out.println("TSE insertFloor(String floorName) done");
	}
	
	public void updateFloor(JsonArray floorList,JsonArray delFloorList){
		System.out.println("TSS Strat to updateFloor(JsonArray floorList,JsonArray delFloorList)");
		System.out.println("TSS [ floorList = "+floorList+" ]");
		System.out.println("TSS [ delFloorList = "+delFloorList+" ]");
		BdyFloor floorBean = new BdyFloor();
		if(delFloorList.size() > 0){
			for(int i=0;i < delFloorList.size() ; i++){
				floorBean.setFloorid(Integer.parseInt(delFloorList.getString(i)));
				tableDao.deleteTableByFloor(floorBean.getFloorid());
				floorDao.deleteFloor(floorBean);
			}
		}else{
			System.out.println("TSE No floor can delete!");
		}
		
		if(floorList.size()>0){
			for(int i=0;i < floorList.size() ;i++){
				JsonObject floorData = floorList.getJsonObject(i);
				floorBean.setFloorid(Integer.parseInt(floorData.getString("floorId")));
				floorBean.setName(floorData.getString("floorName"));
				floorBean.setSort(i);
				floorDao.updateFloor(floorBean);
			}
		}else{
			System.out.println("TSE No floor can update!");
		}
		System.out.println("TSE updateFloor(JsonArray floorList,JsonArray delFloorList) done");
	}
	
	public void setTbToOpenState(int tbId,int tbState,int custNum){
		System.out.println("TSS Strat to setTbToOpenState(int tbId,int tbState,int custNum)");
		System.out.println("TSS [ tbId = "+tbId+" ]");
		System.out.println("TSS [ tbState = "+tbState+" ]");
		System.out.println("TSS [ custNum = "+custNum+" ]");
		BdyTable tableBean = tableDao.getTableById(tbId);
		tableBean.setCustNum(custNum);
		tableBean.setTableState(tbState);
		tableDao.updateTable(tableBean);
		System.out.println("TSE setTbToOpenState(int tbId,int tbState,int custNum) done");
	}
}
