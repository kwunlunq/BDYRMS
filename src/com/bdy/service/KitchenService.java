package com.bdy.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;

import com.bdy.model.BdyOrderlist;
import com.bdy.model.BdySet;
import com.bdy.model.BdySetdetail;
import com.bdy.model.dao.BdyBillDao;
import com.bdy.model.dao.BdyDiscountDao;
import com.bdy.model.dao.BdyEmpDao;
import com.bdy.model.dao.BdyFloorDao;
import com.bdy.model.dao.BdyFoodDao;
import com.bdy.model.dao.BdyFoodkindDao;
import com.bdy.model.dao.BdyMainkindDao;
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
	BdyMainkindDao mainkindDao;
	Map<Integer,Map<Integer,Double>> sortmealMap = new LinkedHashMap<Integer,Map<Integer,Double>>();
	public Map<Integer, Map<Integer, Double>> getMealMap() {
		return sortmealMap;
	}
	public BdyMainkindDao getMainkindDao() {
		return mainkindDao;
	}
	public void setMainkindDao(BdyMainkindDao mainkindDao) {
		this.mainkindDao = mainkindDao;
	}
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
	public void sortAllSetOutMealMap(){
		List<BdySet> setMeaList=setDao.getAllSet();
		for(BdySet item:setMeaList){//------------納帕套餐
			System.out.println(item.getName());
			Set<BdySetdetail> itemMealKinds=item.getBdySetdetails();//-----------納帕套餐的食物種類處理
			List<BdySetdetail> temp =new  ArrayList<BdySetdetail>();
			for(BdySetdetail item1:itemMealKinds){//--change ArrayList
				System.out.println("食物種類名稱: " + item1.getBdyFoodkind().getName());
				temp.add(item1);
			}
			Collections.sort(temp, new Comparator<BdySetdetail>() {

				@Override
				public int compare(BdySetdetail item1, BdySetdetail item2) {
					
					return new Integer(item1.getBdyFoodkind().getSeq()).compareTo(new Integer(item2.getBdyFoodkind().getSeq()));
				}
			});
			System.out.println("--------------------------------------------");
			Double countDate = new Double(0);
			Map<Integer,Double> detailMap = new LinkedHashMap<Integer, Double>();
			for(BdySetdetail timeslot:temp){
				detailMap.put(new Integer(timeslot.getBdyFoodkind().getFkId()),new Double(timeslot.getBdyFoodkind().getPeriod()+countDate));
				countDate+=timeslot.getBdyFoodkind().getPeriod();
			System.out.println("出餐名稱-->"+timeslot.getBdyFoodkind().getName()+":點餐後"+(Double)detailMap.get(timeslot.getBdyFoodkind().getFkId())+"分鐘出餐");
			
		}
			sortmealMap.put(item.getSetId(), detailMap);
			
		}
	}
	
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
