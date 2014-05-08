package com.bdy.service;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;

import com.bdy.model.BdyFood;
import com.bdy.model.BdyFoodkind;
import com.bdy.model.BdyOrderlist;
import com.bdy.model.BdySet;
import com.bdy.model.BdySetdetail;
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

public class OrderService {
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
	public List<BdySetdetail> getSetDetailBySetId(int SetId) {
		List<BdySetdetail> result = new ArrayList<BdySetdetail>();
		
		List<BdySetdetail> details = setdetailDao.getSetdetailBySetId(SetId);
		
		for (BdySetdetail detail: details) {
			System.out.print(detail.getBdyFoodkind().getName());
			System.out.print("\t");
		}
		System.out.println();
		
		
		return result;
	}
	
	public int groupTest(int setId, int fkId) {
		return setdetailDao.fkCount(setId, fkId);
	}
	
	public JsonArray getMainsJSON() {
//		foodkindDao.getMainFoodkinds();
		
		/*
		 * 目標 :
		 * [{"牛排":["牛小排", "菲力牛排", "肋眼牛排"]}, 
 		 *  {"披薩":["夏威夷披薩", "海鮮披薩", "xx披薩"]}
 		 *  ]
		 */
		JsonArrayBuilder fkBuilder = Json.createArrayBuilder();
		for (BdyFoodkind fk : foodkindDao.getMainFoodkinds()) {
			System.out.println(fk.getName());
			System.out.println("---------------");
			int fkId = fk.getFkId();
			JsonArrayBuilder foodBuilder = Json.createArrayBuilder();
			for (BdyFood food : foodDao.getFoodsByFkId(fkId)) {
				System.out.println(food.getName());
				foodBuilder.add(food.getName());
			}
			System.out.println("\n\n");
			fkBuilder.add(Json.createObjectBuilder()
					 		  .add(fk.getName(), foodBuilder));
		}
		return fkBuilder.build();
	}
	
}
