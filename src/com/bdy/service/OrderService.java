package com.bdy.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bdy.model.BdyEmp;
import com.bdy.model.BdyFloor;
import com.bdy.model.BdyFood;
import com.bdy.model.BdyFoodkind;
import com.bdy.model.BdyMainkind;
import com.bdy.model.BdyOrder;
import com.bdy.model.BdyOrderlist;
import com.bdy.model.BdySet;
import com.bdy.model.BdySetdetail;
import com.bdy.model.BdyTable;
import com.bdy.model.dao.BdyBillDao;
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
import com.bdy.model.dao.BdyPriorityDao;
import com.bdy.model.dao.BdySetDao;
import com.bdy.model.dao.BdySetdetailDao;
import com.bdy.model.dao.BdyTableDao;

public class OrderService {
	private final static int MAIN_SEQ = 10;
	private final static String DEFAULT_VALUE = "0";
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
	
	
	public void setBilldetailDao(BdyBilldetailDao billdetailDao) {
		this.billdetailDao = billdetailDao;
	}
	public void setNewsDao(BdyNewsDao newsDao) {
		this.newsDao = newsDao;
	}
	public void setBookingDao(BdyBookingDao bookingDao) {
		this.bookingDao = bookingDao;
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
	public OrderService(){}
	public List<BdyOrderlist> getAllOrderlists(){
		 return orderlistDao.getAllorderlist();
	}
	
	public List<BdySet> getSet() {
		return setDao.getAllSet();
	}
	
	public List<BdyFoodkind> getFoodkinds() {
		return foodkindDao.getAllFoodkind();
	}
	public JsonArray getSetDetailBySetIdJSON(int SetId) {
		List<BdySetdetail> result = new ArrayList<BdySetdetail>();
		List<BdySetdetail> details = setdetailDao.getSortedSetdetailBySetId(SetId);
		JsonArrayBuilder fkBuilder = Json.createArrayBuilder();
		for (BdySetdetail detail: details) {
			BdyFoodkind fk = detail.getBdyFoodkind();
			if (fk.getSeq() == MAIN_SEQ) {
				continue;
			}
			int fkCount = setdetailDao.fkCount(SetId, fk.getFkId());
			System.out.print(detail.getBdyFoodkind().getSeq()+"-");
			System.out.println(detail.getBdyFoodkind().getName()+"\t"+fkCount+"份");
			System.out.print("\t");
			Iterator foods = detail.getBdyFoodkind().getBdyFoods().iterator();
			JsonArrayBuilder fdBuilder = Json.createArrayBuilder();
			while (foods.hasNext()) {
				BdyFood food = (BdyFood) foods.next();
				fdBuilder.add(Json.createObjectBuilder()
								  .add("fdId", food.getFdId())
								  .add("fdName", food.getName()));
				System.out.print(food.getName()+"\t");
			}
			fkBuilder.add(Json.createObjectBuilder()
					 		  .add(fk.getName(), fdBuilder.build())
					 		  .build());
			System.out.println();
		}
		return fkBuilder.build();
	}
	
	public JsonArray getMainsJSON() {
		/*
		 * 目標 :
		 * [{"牛排":["牛小排", "菲力牛排", "肋眼牛排"]}, 
 		 *  {"披薩":["夏威夷披薩", "海鮮披薩", "xx披薩"]}
 		 *  ]
		 */
		JsonArrayBuilder mkBuilder = Json.createArrayBuilder();
		for (BdyMainkind mk : mainkindDao.getAllMainkind()) {
			System.out.println(mk.getName());
			System.out.println("---------------");
			int mkId = mk.getMkId();
			JsonArrayBuilder foodBuilder = Json.createArrayBuilder();
			for (BdyFood food : foodDao.getFoodsByMkId(mkId)) {
				System.out.println(food.getName());
				foodBuilder.add(food.getName());
			}
			System.out.println("\n\n");
			mkBuilder.add(Json.createObjectBuilder()
					 		  .add(mk.getName(), foodBuilder));
		}
		return mkBuilder.build();
	}
	
	public JsonObject getFoodsJSON() {
		/*
		 * 目標 :
		 * {"isMain":
		 * 		[{"牛排":[	{"fdId"="1", "fdName"="牛小排", "fkId=5"},
		 * 					{"fdId"="2", "fdName"="菲力牛排", "fkId=5"}
		 * 					{"fdId"="3", "fdName"="肋眼牛排", "fkId=5"}]}, 
 		 *  	 {"披薩":["	{"fdId"="4", "fdName"="夏威夷披薩"},
 		 *  				{"fdId"="5", "fdName"="海鮮披薩"}]}
 		 *  	]
 		 *  "notMain":
 		 *  	[{"前菜":[  {}, {}, {}    ]
 		 *  }
 		 *  
		 */
		
		JsonArrayBuilder mkBuilder = Json.createArrayBuilder();
		System.out.println("mk Array : ");
		for (BdyMainkind mk : mainkindDao.getAllMainkind()) {
			System.out.println(mk.getName());
			System.out.println(mk.getName());
			System.out.println("---------------");
			int mkId = mk.getMkId();
			int fkId = -1;
			JsonArrayBuilder foodBuilder = Json.createArrayBuilder();
			for (BdyFood food : foodDao.getFoodsByMkId(mkId)) {
				System.out.println(food.getName());
				if (fkId == -1) {
					fkId = food.getBdyFoodkind().getFkId();
//					System.out.println("fkId="+fkId);
				}
				foodBuilder.add(Json.createObjectBuilder()
									.add("fdId", food.getFdId())
									.add("fdName", food.getName())
									.add("fkId", fkId)
									.build());
			}
			System.out.println("\n\n");
			mkBuilder.add(Json.createObjectBuilder()
					 		  .add(mk.getName(), foodBuilder));
		}
		
		System.out.println("fk Array :");
		
		JsonArrayBuilder fkBuilder = Json.createArrayBuilder();
		List<BdyFoodkind> fks = foodkindDao.getAllFoodkind();
		
		for (BdyFoodkind fk : fks) {
			List<BdyFood> foods = foodDao.getFoodsByFkId(fk.getFkId());
			if (foods!=null && foods.size()!=0 && foods.get(0).getBdyMainkind()!=null) {
				continue;
			}
			JsonArrayBuilder foodBuilder = Json.createArrayBuilder();
			for (BdyFood food : foods) {
				System.out.println(food.getName());
				foodBuilder.add(Json.createObjectBuilder()
									.add("fdId", food.getFdId())
									.add("fdName", food.getName())
									.add("fkId", fk.getFkId())
									.build());
			}
			fkBuilder.add(Json.createObjectBuilder()
							  .add(fk.getName(), foodBuilder)
							  .build());
		}
		JsonObject resultObject = 
				Json.createObjectBuilder().add("isMain", mkBuilder.build())
								  		  .add("notMain", fkBuilder.build())
								  		  .build();
		return resultObject;
	}
	
	public JsonArray getFoodkindJson() {
		JsonArrayBuilder aryBuilder = Json.createArrayBuilder();
		for (BdyFoodkind fk : foodkindDao.getAllFoodkind()) {
			aryBuilder.add(Json.createObjectBuilder()
							   .add("fkName", fk.getName())
							   .add("fkId", fk.getFkId())
							   .build());
		};
		
		return aryBuilder.build();
	}
	public JsonArray getTableJson() {
		JsonArrayBuilder aryBuilder = Json.createArrayBuilder();
		for (BdyFloor floor : floorDao.getAllFloor()) {
			System.out.println(floor);
			JsonArrayBuilder tbary = Json.createArrayBuilder();
			for (BdyTable table: tableDao.getTableByFloor(floor.getFloorid())) {
				tbary.add(Json.createObjectBuilder()
							  .add("tbId", table.getTbId())
							  .add("tbName", table.getName())
							  .build());
			}
			aryBuilder.add(Json.createObjectBuilder()
							   .add("fId", floor.getFloorid())
							   .add("fName", floor.getName())
							   .add("tables", tbary)
							   .build());
		}
		
		return aryBuilder.build();
	}
	public JsonArray getSetJson() {
		JsonArrayBuilder aryBuilder = Json.createArrayBuilder();
		for (BdySet set : setDao.getAllSet()) {
			// 名稱
			System.out.println(set.getName());
			
			List<BdySetdetail> details = setdetailDao.getSortedSetdetailBySetId(set.getSetId());
			
			JsonArrayBuilder detailbuilder = Json.createArrayBuilder();
			for (BdySetdetail detail : details) {
				detailbuilder.add(detail.getBdyFoodkind().getFkId());
			}
			JsonObject object =	Json.createObjectBuilder()
									.add("name", set.getName())
									.add("id", set.getSetId())
									.add("detail", detailbuilder.build())
									.build();
			aryBuilder.add(object);
		}
		return aryBuilder.build();
	}
	
	public void readOrderJson(JsonObject object) {
		  System.out.println(object.toString());
		  System.out.println("點餐單 : ");
		  
		  try {
			int tbId = Integer.parseInt(object.getString("TableId", DEFAULT_VALUE));
			System.out.println("TableId : "+tbId);
			int custNum = Integer.parseInt(object.getString("CustNum", DEFAULT_VALUE));
			System.out.println("CustNum : "+custNum);
			String empId = object.getString("EmpId", "0");
			System.out.println("EmpId : "+empId);
			Calendar nowTime = new GregorianCalendar(Locale.TAIWAN);
			System.out.println("OrderTime : "+nowTime.getTime());
			
			BdyEmp emp = empDao.getEmpById(empId);
			BdyTable tb = tableDao.getTableById(tbId);
			System.out.println("empid = "+emp.getEmpId());
			System.out.println("tbid="+tb.getTbId());
			BdyOrder order = new BdyOrder(emp, tb, nowTime.getTime(), 0, custNum);
			orderDao.insert(order);
			BdyOrder newOrder = orderDao.getOrderByOrder(order);
			System.out.println("newOrderId="+newOrder.getOdId());
			JsonArray foods = object.getJsonArray("Foods");
			
			for (int i = 0; i < foods.size(); i++) {
				JsonObject food = foods.getJsonObject(i);
				int fdId = Integer.parseInt(food.getString("fdid", DEFAULT_VALUE));
				System.out.println("\tfdId : "+fdId);
				
				int fkId = foodDao.getFood(fdId).getBdyFoodkind().getFkId();
				System.out.println("\tfkId : "+fkId);
				int setId = Integer.parseInt(food.getString("setId", DEFAULT_VALUE));
				System.out.println("\tsetId : "+setId);
				
				double fdPrice = foodDao.getFood(fdId).getPrice();
				double setBasePrice = setdetailDao.getPriceBySetAndFk(setId, fkId);
				System.out.println("\tprice : "+fdPrice+", "+setBasePrice);
				double addMoney = 0;
				if (setBasePrice!=0 && fdPrice>setBasePrice) {
					addMoney = fdPrice - setBasePrice;
				}
				System.out.println("\taddMoney : "+addMoney);
				
				BdySet set = setDao.getSet(setId);
				BdyFood bdyFood = foodDao.getFood(fdId);
				BdyFoodkind foodkind = foodkindDao.getFoodkind(fkId);
				BdyOrderlist odlist = new BdyOrderlist(newOrder, set, bdyFood, foodkind, addMoney, 0);
				orderlistDao.insert(odlist);
			}
		  } catch (NullPointerException e) {
			  System.out.println("nullPointer (param not found?)");
			  e.printStackTrace();
		  } catch (ClassCastException e) {
			  System.out.println("ClassCastException (type error?)");
			  e.printStackTrace();
		  } catch (Exception e) {
			  System.out.print("unknown error : ");
			  System.out.println(e.getMessage());
		  }
		  
	}
}
