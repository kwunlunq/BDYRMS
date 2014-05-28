package com.bdy.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;

import com.bdy.model.BdyFoodkind;
import com.bdy.model.BdyOrderlist;
import com.bdy.model.BdySet;
import com.bdy.model.BdySetdetail;
import com.bdy.model.KitchenView;
import com.bdy.model.dao.BdyBillDao;
import com.bdy.model.dao.BdyBillHistoryDao;
import com.bdy.model.dao.BdyBilldetailDao;
import com.bdy.model.dao.BdyBookingDao;
import com.bdy.model.dao.BdyDiscountDao;
import com.bdy.model.dao.BdyEmpDao;
import com.bdy.model.dao.BdyFloorDao;
import com.bdy.model.dao.BdyFoodDao;
import com.bdy.model.dao.BdyFoodkindDao;
import com.bdy.model.dao.BdyMainkindDao;
import com.bdy.model.dao.BdyMakeareaDao;
import com.bdy.model.dao.BdyNewsDao;
import com.bdy.model.dao.BdyOrderDao;
import com.bdy.model.dao.BdyOrderlistDao;
import com.bdy.model.dao.BdyOrderlistReportDao;
import com.bdy.model.dao.BdyPriorityDao;
import com.bdy.model.dao.BdySetDao;
import com.bdy.model.dao.BdySetdetailDao;
import com.bdy.model.dao.BdyTableDao;

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
	
	BdyBilldetailDao billdetailDao;
	BdyNewsDao newsDao;
	BdyBookingDao bookingDao;
	
	BdyBillHistoryDao billHistoryDao;
	BdyOrderlistReportDao orderlistReportDao;
	
	public void setBillHistoryDao(BdyBillHistoryDao billHistoryDao) {
		this.billHistoryDao = billHistoryDao;
	}
	public void setOrderlistReportDao(BdyOrderlistReportDao orderlistReportDao) {
		this.orderlistReportDao = orderlistReportDao;
	}
	public void setBilldetailDao(BdyBilldetailDao billdetailDao) {
		this.billdetailDao = billdetailDao;
	}
	public void setNewsDao(BdyNewsDao newsDao) {
		this.newsDao = newsDao;
	}
	public void setBookingDao(BdyBookingDao bookingDao) {
		this.bookingDao = bookingDao;
	}
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
	//-------------------------------------------init方法得到必須呼叫
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
	//--------------------------------------------------輸入套餐ID，食物類別ID，得到DOUBLE
	public Double findOutMealTime(int setId,int fkId){
			return	sortmealMap.get(setId).get(fkId);
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
	public List<KitchenView> getNotOutOrderlistsObject(){
		List<BdyOrderlist> list= orderlistDao.getAllorderlist();
		List<BdyOrderlist> notOutMeallist = new ArrayList<BdyOrderlist>();//-----未出餐的食物LIST
		for(BdyOrderlist meal:list){
			if(meal.getOlState().equals(new Integer(0))){//------------如果是未出餐的，放置notOutMeallist
				notOutMeallist.add(meal);
			}
		}
		//-------依造"未出餐的食物"的"出餐時間"排序
		List<KitchenView> sortOutlist =new LinkedList<KitchenView>();//---------排序過後的LIST
		for(BdyOrderlist temp:notOutMeallist){
			if(temp.getBdySet()==null){//----------單點(沒有套餐ID)
				KitchenView viewItem = new KitchenView();
				Double slotTime =temp.getBdyFood().getBdyFoodkind().getPeriod();
				System.out.println("------------------------測試單點");
				viewItem.setTableID(temp.getBdyOrder().getBdyTable().getTbId());//桌號
				viewItem.setOrderDate(temp.getBdyOrder().getOrdTime());//點單時間
				viewItem.setOrderlistname(temp.getBdyFood().getName());//點了甚麼食物
				viewItem.setOutMealTime(new Date(temp.getBdyOrder().getOrdTime().getTime()+slotTime.longValue()*60*1000));//實際出餐時間
				viewItem.setFoodkindID(temp.getBdyFood().getBdyFoodkind().getFkId());
				viewItem.setOrderlistID(temp.getOdlistId());
				sortOutlist.add(viewItem);
			}else{//---------------------套餐的處理(有套餐ID)
			KitchenView viewItem = new KitchenView();
			Double slotTime =findOutMealTime(temp.getBdySet().getSetId(),temp.getBdyFood().getBdyFoodkind().getFkId());
			System.out.println("--------------------------測試---------------------------------"+slotTime);
			viewItem.setTableID(temp.getBdyOrder().getBdyTable().getTbId());//桌號
			viewItem.setOrderDate(temp.getBdyOrder().getOrdTime());//點單時間
			viewItem.setOrderlistname(temp.getBdyFood().getName());//點了甚麼食物
			viewItem.setOutMealTime(new Date(temp.getBdyOrder().getOrdTime().getTime()+slotTime.longValue()*60*1000));//實際出餐時間
			viewItem.setFoodkindID(temp.getBdyFood().getBdyFoodkind().getFkId());
			viewItem.setOrderlistID(temp.getOdlistId());
			sortOutlist.add(viewItem);
			System.out.println("食物名稱"+viewItem.getOrderlistname());
			System.out.println("食物出餐時間" + viewItem.getOrderDate());
			}
			System.out.println(sortOutlist.size());
		}
		Collections.sort(sortOutlist,new Comparator<KitchenView>() {//--------------依造出餐時間點排序

			@Override
			public int compare(KitchenView o1, KitchenView o2) {
				
				return o1.getOutMealTime().compareTo(o2.getOutMealTime());
			}
		});
		return sortOutlist;
	}
	
	//--------------------------------拿到所有未出餐食物的出餐順序
	@SuppressWarnings("unchecked")
	public JsonArray getNotOutOrderlists(){
		List<BdyOrderlist> list= orderlistDao.getAllorderlist();
		List<BdyOrderlist> notOutMeallist = new ArrayList<BdyOrderlist>();//-----未出餐的食物LIST
		for(BdyOrderlist meal:list){
			if(meal.getOlState().equals(new Integer(0))){//------------如果是未出餐的，放置notOutMeallist
				notOutMeallist.add(meal);
			}
		}
		//-------依造"未出餐的食物"的"出餐時間"排序
		List<KitchenView> sortOutlist =new ArrayList<KitchenView>();//---------排序過後的LIST
		for(BdyOrderlist temp:notOutMeallist){
			KitchenView viewItem = new KitchenView();
			Double slotTime =findOutMealTime(temp.getBdySet().getSetId(),temp.getBdyFoodkind().getFkId());
			viewItem.setTableID(temp.getBdyOrder().getBdyTable().getTbId());//桌號
			viewItem.setOrderDate(temp.getBdyOrder().getOrdTime());//點單時間
			viewItem.setOrderlistname(temp.getBdyFood().getName());//點了甚麼食物
			viewItem.setOutMealTime(new Date(temp.getBdyOrder().getOrdTime().getTime()+slotTime.longValue()));//實際出餐時間
			sortOutlist.add(viewItem);
		}
		Collections.sort(sortOutlist,new Comparator<KitchenView>() {//--------------依造出餐時間點排序

			@Override
			public int compare(KitchenView o1, KitchenView o2) {
				
				return o1.getOutMealTime().compareTo(o2.getOutMealTime());
			}
		});		
		//--------------------------JSON
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		for(KitchenView notOutMealitem:sortOutlist){
			jsonArrayBuilder.add(
                    Json.createObjectBuilder()
                    .add("桌號",notOutMealitem.getTableID())
                    .add("點單時間",sdf.format(notOutMealitem.getOrderDate()))
                    .add("食物名稱", notOutMealitem.getOrderlistname())
                    .add("出餐時間",notOutMealitem.toString())
                    );
		}
		JsonArray jsonArray = jsonArrayBuilder.build();
		return jsonArray;
	}
	public void outMealchange(int orderListID){//-------------------------出餐
	BdyOrderlist orderlist=orderlistDao.getorderlist(orderListID);
	orderlist.setOlState(1);
	orderlistDao.update(orderlist);
	}
	public List<BdyFoodkind> getFoodKinds(){
	List<BdyFoodkind> temps=foodkindDao.getAllFoodkind();
	Collections.sort(temps,new Comparator<BdyFoodkind>() {

		@Override
		public int compare(BdyFoodkind o1, BdyFoodkind o2) {
			// TODO Auto-generated method stub
			return new Integer(o1.getFkId()).compareTo(new Integer(o2.getFkId()));
		}
		
		
	});
		return temps;
	}
	
	public JsonArray getNotOutOrderlistsJsonArray(){
		List<KitchenView> outMeals= getNotOutOrderlistsObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		for(KitchenView notOutMealitem:outMeals){
			jsonArrayBuilder.add(
                    Json.createObjectBuilder()
                    .add("桌號",tableDao.getTableById(notOutMealitem.getTableID()).getName())
                    .add("點單明細編號",notOutMealitem.getOrderlistID())
                    .add("食物名稱", notOutMealitem.getOrderlistname())
                    .add("點單時間", sdf.format(notOutMealitem.getOrderDate()))                 
                    .add("實際出餐時間",sdf.format(notOutMealitem.getOutMealTime()))
                    .add("食物種類",notOutMealitem.getFoodkindID())
                    .add("出餐點", notOutMealitem.getOutMealTime().getTime())
                    );
		}
		JsonArray jsonArray = jsonArrayBuilder.build();
		return jsonArray;
	}
	
	public JsonArray getFoodKindsJsonArrsy(){
		List<BdyFoodkind> foodkinds= getFoodKinds();
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		for(BdyFoodkind temp:foodkinds){
			jsonArrayBuilder.add(
					Json.createObjectBuilder()
					.add("種類編號",temp.getFkId())
					.add("種類名稱",temp.getName())
					.add("製作區域",temp.getBdyMakearea().getName())
					);
		}
		JsonArray jsonArray = jsonArrayBuilder.build();
		return jsonArray;
	}
	
	
	public void outAllMeal (){
		List<BdyOrderlist> list = orderlistDao.getAllorderlist();
		for(BdyOrderlist notoutMeal:list){
			if(notoutMeal.getOlState()==0){
				notoutMeal.setOlState(new Integer(1));
				orderlistDao.update(notoutMeal);
			}
		}
		
		
	}
}
