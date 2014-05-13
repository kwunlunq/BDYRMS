package com.bdy.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.bdy.model.BdyFood;
import com.bdy.model.BdyFoodkind;
import com.bdy.model.BdyMainkind;
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

public class OrderService {
	private final static int MAIN_SEQ = 10;
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
//			String fkName = fk
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
//			fkBuilder.add(fk.getName(), fdBuilder.build());
			System.out.println();
		}
//		System.out.println(fkBuilder.build().toString());
//		return result;
		return fkBuilder.build();
	}
	public int groupTest(int setId, int fkId) {
		return setdetailDao.fkCount(setId, fkId);
	}
	
	public JsonArray getSetDetailJSON(int setId) {
		JsonArrayBuilder fkBuilder = Json.createArrayBuilder();
		
		
//		setdetailDao;
		
		
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
	
//	public void test22() {
//		
//	}

	public JsonObject getFoodsJSON() {
		/*
		 * 目標 :
		 * {"isMain":
		 * 		[{"牛排":[	{"fdId"="1", "fdName"="牛小排"},
		 * 					{"fdId"="2", "fdName"="菲力牛排"}
		 * 					{"fdId"="3", "fdName"="肋眼牛排"}]}, 
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
			// mk name array
			JsonArrayBuilder foodBuilder = Json.createArrayBuilder();
			for (BdyFood food : foodDao.getFoodsByMkId(mkId)) {
				System.out.println(food.getName());
//				foodBuilder.add(food.getName());
				foodBuilder.add(Json.createObjectBuilder()
									.add("fdId", food.getFdId())
									.add("fdName", food.getName())
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
	
}
