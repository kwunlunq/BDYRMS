package com.bdy.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;

import com.bdy.model.BdyOrderlist;
import com.bdy.model.dao.BdyBillDao;
import com.bdy.model.dao.BdyDiscountDao;
import com.bdy.model.dao.BdyEmpDao;
import com.bdy.model.dao.BdyFloorDao;
import com.bdy.model.dao.BdyFoodDao;
import com.bdy.model.dao.BdyFoodkindDao;
import com.bdy.model.dao.BdyMakeareaDao;
import com.bdy.model.dao.BdyOrderDao;
import com.bdy.model.dao.BdyOrderlistDao;
import com.bdy.model.dao.BdyPriorityDao;
import com.bdy.model.dao.BdySetDao;
import com.bdy.model.dao.BdySetdetailDao;
import com.bdy.model.dao.BdyTableDao;
import com.bdy.model.KitchenView;

public class KitchenService {
	BdyBillDao billDao;
	BdyDiscountDao discountDao;
	BdyEmpDao empDao;
	BdyFloorDao floorDao;
	BdyFoodDao foodDao;
	BdyFoodkindDao foodkindDao;
	BdyMakeareaDao makeareaDao;
	BdyOrderDao orderDao;
	BdyOrderlistDao orderlistDao;
	BdyPriorityDao priorityDao;
	BdySetDao setDao;
	BdySetdetailDao setdetailDao;
	BdyTableDao tableDao;
	public void setBillDao(BdyBillDao billDao) {
		this.billDao = billDao;
	}
	public void setDiscountDao(BdyDiscountDao discountDao) {
		this.discountDao = discountDao;
	}
	public void setEmpDao(BdyEmpDao empDao) {
		this.empDao = empDao;
	}
	public void setFloorDao(BdyFloorDao floorDao) {
		this.floorDao = floorDao;
	}
	public void setFoodDao(BdyFoodDao foodDao) {
		this.foodDao = foodDao;
	}
	public void setFoodkindDao(BdyFoodkindDao foodkindDao) {
		this.foodkindDao = foodkindDao;
	}
	public void setMakeareaDao(BdyMakeareaDao makeareaDao) {
		this.makeareaDao = makeareaDao;
	}
	public void setOrderDao(BdyOrderDao orderDao) {
		this.orderDao = orderDao;
	}
	public void setOrderlistDao(BdyOrderlistDao orderlistDao) {
		this.orderlistDao = orderlistDao;
	}
	public void setPriorityDao(BdyPriorityDao priorityDao) {
		this.priorityDao = priorityDao;
	}
	public void setSetDao(BdySetDao setDao) {
		this.setDao = setDao;
	}
	public void setSetdetailDao(BdySetdetailDao setdetailDao) {
		this.setdetailDao = setdetailDao;
	}
	public void setTableDao(BdyTableDao tableDao) {
		this.tableDao = tableDao;
	}
	public KitchenService(){}
	
	public JsonArray getAllOrderlists(){
		List<BdyOrderlist> list= orderlistDao.getAllorderlist();
		String str="已出餐";
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		 for(BdyOrderlist orderList:list){
			 SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
			 Integer i = orderList.getOlState();
			 if(i.equals(new Integer(0))){
				 str="尚未出餐"; 
			 }
			 jsonArrayBuilder.add(
	                    Json.createObjectBuilder()
	                    .add("點單明細ID",orderList.getOdlistId())
	                    .add("點餐單ID", orderList.getBdyOrder().getOdId())
	                    .add("桌號", orderList.getBdyOrder().getBdyTable().getTbId())
	                    .add("進單時間",sdf.format(orderList.getBdyOrder().getOrdTime()))
	                    .add("出餐狀態",str)
	                    );
			 str="已出餐";
		 }
		JsonArray jsonArray = jsonArrayBuilder.build();
		return jsonArray;
	}
	public List<KitchenView> getAllOrderlistsForView(){
		List<BdyOrderlist> list= orderlistDao.getAllorderlist();
		List<KitchenView> viewList = new ArrayList<KitchenView>();
		 for(BdyOrderlist orderList:list){
			 if(orderList.getOlState().equals(new Integer(1))){
				 break;
			 }
			 KitchenView viewItem = new KitchenView();
			 viewItem.setOrderlistname(orderList.getBdyFood().getName());
			 viewItem.setOrderlistID(orderList.getOdlistId());
			 viewItem.setOrderID(orderList.getBdyOrder().getOdId());
			 viewItem.setTableID(orderList.getBdyOrder().getBdyTable().getTbId());
			 viewItem.setOrderDate(orderList.getBdyOrder().getOrdTime());
			 viewItem.setOrderStatus(orderList.getOlState());
			 viewList.add(viewItem);
		 }		 
		 Collections.sort(viewList,new Comparator<KitchenView>(){

			@Override
			public int compare(KitchenView item1, KitchenView item2) {
				
				return item1.getOrderDate().compareTo(item2.getOrderDate());
			}
			 
		 });
		return viewList;
		
	}
	
}
