package com.bdy.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

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
	
	public TreeMap<Integer, BdyFoodkind> getAllFksSortedBySeq() {
		TreeMap<Integer, BdyFoodkind> fks = new TreeMap<Integer, BdyFoodkind>();
		List<BdyFoodkind> foodkinds = foodkindDao.getAllFoodkind();
		
		int index = 1;
		for (BdyFoodkind fk : foodkinds) {
			fks.put(index++, fk);
		}
		return fks;
	}
	public TreeMap<Integer, BdyMainkind> getAllMks() {
		TreeMap<Integer, BdyMainkind> mks = new TreeMap<Integer, BdyMainkind>();
		List<BdyMainkind> mainkinds = mainkindDao.getAllMainkind();
		for (BdyMainkind mk : mainkinds) {
			mks.put(mk.getMkId(), mk);
		}
		return mks;
	}
	public TreeMap<Integer, BdyFood> getAllFoods() {
		TreeMap<Integer, BdyFood> foods = new TreeMap<Integer, BdyFood>();
		List<BdyFood> foodlist = foodDao.getAllFood();
		for (BdyFood fd : foodlist) {
			foods.put(fd.getFdId(), fd);
		}
		return foods;
	}
	public TreeMap<Integer, BdySetdetail> getAllSortedSetdetails() {
		TreeMap<Integer, BdySetdetail> setdetails = new TreeMap<Integer, BdySetdetail>();
		List<BdySetdetail> setdetaillist = setdetailDao.getAllSetdetailSortedBySeq();
		int index = 1;
		for (BdySetdetail sd : setdetaillist) {
			setdetails.put(index++, sd);
		}
		return setdetails;
	}
	public TreeMap<Integer, BdySet> getAllSets() {
		TreeMap<Integer, BdySet> sets = new TreeMap<Integer, BdySet>();
		List<BdySet> setlist = setDao.getAllSet();
		for (BdySet set : setlist) {
			sets.put(set.getSetId(), set);
		}
		return sets;
	}
	public BdyEmp getEmp(String empId) {
		BdyEmp emp = empDao.getEmpById(empId);
		return emp;
	}
	public TreeMap<Integer, BdyTable> getAllTables() {
		TreeMap<Integer, BdyTable> tables = new TreeMap<Integer, BdyTable>();
		List<BdyTable> tablelist = tableDao.getAllTable();
		for (BdyTable table : tablelist) {
			tables.put(table.getTbId(), table);
		}
		return tables;
	}
	public TreeMap<Integer, BdyFloor> getAllFloors() {
		TreeMap<Integer, BdyFloor> floors = new TreeMap<Integer, BdyFloor>();
		List<BdyFloor> floorlist = floorDao.getAllFloor();
		for (BdyFloor floor : floorlist) {
			floors.put(floor.getFloorid(), floor);
		}
		return floors;
	}
	public JsonObject getFoodsJSONOld() {
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
		System.out.println("Geting foods...");
		JsonArrayBuilder mkBuilder = Json.createArrayBuilder();
		List<BdyMainkind> mks =  mainkindDao.getAllMainkind();
		for (BdyMainkind mk : mks) {
			int mkId = mk.getMkId();
			int fkId = -1;
			JsonArrayBuilder foodBuilder = Json.createArrayBuilder();
			for (BdyFood food : foodDao.getFoodsByMkId(mkId)) {
				if (fkId == -1) {
					fkId = food.getBdyFoodkind().getFkId();
				}
				foodBuilder.add(Json.createObjectBuilder()
									.add("fdId", food.getFdId())
									.add("fdName", food.getName())
									.add("fkId", fkId)
									.build());
			}
			mkBuilder.add(Json.createObjectBuilder()
					 		  .add(mk.getName(), foodBuilder));
		}
		
		JsonArrayBuilder fkBuilder = Json.createArrayBuilder();
		List<BdyFoodkind> fks = foodkindDao.getAllFoodkind();
		
		for (BdyFoodkind fk : fks) {
			List<BdyFood> foods = foodDao.getFoodsByFkId(fk.getFkId());
			if (foods!=null && foods.size()!=0 && foods.get(0).getBdyMainkind()!=null) {
				continue;
			}
			JsonArrayBuilder foodBuilder = Json.createArrayBuilder();
			for (BdyFood food : foods) {
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
		System.out.println("Geting foods...");
		JsonArrayBuilder mkBuilder = Json.createArrayBuilder();
		List<BdyMainkind> mks =  mainkindDao.getAllMainkind();
		for (BdyMainkind mk : mks) {
			int mkId = mk.getMkId();
			int fkId = -1;
			JsonArrayBuilder foodBuilder = Json.createArrayBuilder();
			for (BdyFood food : foodDao.getFoodsByMkId(mkId)) {
				if (fkId == -1) {
					fkId = food.getBdyFoodkind().getFkId();
				}
				foodBuilder.add(Json.createObjectBuilder()
									.add("fdId", food.getFdId())
									.add("fdName", food.getName())
									.add("fkId", fkId)
									.build());
			}
			mkBuilder.add(Json.createObjectBuilder()
					 		  .add(mk.getName(), foodBuilder));
		}
		
		JsonArrayBuilder fkBuilder = Json.createArrayBuilder();
		List<BdyFoodkind> fks = foodkindDao.getAllFoodkind();
		
		for (BdyFoodkind fk : fks) {
			List<BdyFood> foods = foodDao.getFoodsByFkId(fk.getFkId());
			if (foods!=null && foods.size()!=0 && foods.get(0).getBdyMainkind()!=null) {
				continue;
			}
			JsonArrayBuilder foodBuilder = Json.createArrayBuilder();
			for (BdyFood food : foods) {
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
		System.out.println("Geting fks...");
		JsonArrayBuilder aryBuilder = Json.createArrayBuilder();
		List<BdyFoodkind> fks = foodkindDao.getAllFoodkind();
		for (BdyFoodkind fk : fks) {
			aryBuilder.add(Json.createObjectBuilder()
							   .add("fkName", fk.getName())
							   .add("fkId", fk.getFkId())
							   .build());
		};

		return aryBuilder.build();
	}
	public JsonArray getTableJson() {
		System.out.println("Geting table...");
		JsonArrayBuilder aryBuilder = Json.createArrayBuilder();
		List<BdyFloor> floors = floorDao.getAllFloor();
		for (BdyFloor floor : floors) {
			JsonArrayBuilder tbary = Json.createArrayBuilder();
			List<BdyTable> tables = tableDao.getTableByFloor(floor.getFloorid());
			for (BdyTable table: tables) {
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

	public JsonArray makeJSONTables(TreeMap<Integer, BdyTable> tableMap, TreeMap<Integer, BdyFloor> floorMap) {
		JsonArrayBuilder aryBuilder = Json.createArrayBuilder();
		Iterator<Integer> flKeys = tableMap.keySet().iterator();
		while (flKeys.hasNext()) {
			BdyFloor floor = floorMap.get(flKeys.next());
			int floorId = floor.getFloorid();
//		List<BdyFloor> floors = floorDao.getAllFloor();
//		for (BdyFloor floor : floors) {
			JsonArrayBuilder tbary = Json.createArrayBuilder();
			
			Iterator<Integer> tbKeys = floorMap.keySet().iterator();
			List<BdyTable> fIdTables = new ArrayList<BdyTable>();
			while (tbKeys.hasNext()) {
				BdyTable table = tableMap.get(tbKeys.next());
				if (table.getBdyFloor() == floorId) {
					fIdTables.add(table);
				}
			}
			List<BdyTable> tables = tableDao.getTableByFloor(floor.getFloorid());
			for (BdyTable table: tables) {
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
		List<BdySet> sets = setDao.getAllSet();
		for (BdySet set : sets) {
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
	
	public void readOrderJson(JsonObject object, TreeMap<Integer, BdyFood> fdMap,
			TreeMap<Integer, BdySet> setMap, TreeMap<Integer, BdyTable> tableMap, 
			BdyEmp bdyEmp) {
			System.out.println("Processing order...");
			long start = System.currentTimeMillis();
		  
		  try {
			// tbid
			int tbId = Integer.parseInt(object.getString("TableId", DEFAULT_VALUE));
			System.out.println("TableId : "+tbId);
			// custNum
			int custNum = Integer.parseInt(object.getString("CustNum", DEFAULT_VALUE));
			System.out.println("CustNum : "+custNum);
			// empId
			String empId = object.getString("EmpId", "0");
			System.out.println("EmpId : "+empId);
			// orderTime
			Calendar nowTime = new GregorianCalendar(Locale.TAIWAN);
			System.out.println("OrderTime : "+nowTime.getTime());
			
			// 1.emp
			BdyEmp emp;
			if (bdyEmp!=null) {
				emp = bdyEmp;
			} else {
				System.out.println("EMP not found in session");
				emp = empDao.getEmpById(empId);
			}
			// 2.tb
			BdyTable tb;
			if (tableMap!=null && tableMap.size()!=0) {
				tb = tableMap.get(tbId);
			} else {
				System.out.println("TB not found in session");
				tb = tableDao.getTableById(tbId);
			}
			System.out.println("empid = "+emp.getEmpId());
			System.out.println("tbid="+tb.getTbId());
			BdyOrder order = new BdyOrder(emp, tb, nowTime.getTime(), 0, custNum);
			orderDao.insert(order);
			BdyOrder newOrder = orderDao.getOrderByOrder(order);
			System.out.println("newOrderId="+newOrder.getOdId());
			JsonArray foods = object.getJsonArray("Foods");
			
			int lastSetId = 0;
			List<BdySetdetail> bdySetdetails = setdetailDao.getAllSetdetail();
			BdySet bdySet = null;
			int size = foods.size();
			List<BdyOrderlist> lists = new ArrayList<BdyOrderlist>();
			for (int i = 0; i < size; i++) {
				JsonObject food = foods.getJsonObject(i);
				int fdId = Integer.parseInt(food.getString("fdid", DEFAULT_VALUE));
				System.out.println("\tfdId : "+fdId);
				
				
				BdyFood bdyFood;
				// 1.food
				if (fdMap == null || fdMap.size()==0) {
					System.out.println("food not found in session");
					bdyFood = foodDao.getFood(fdId);
				}else {
					bdyFood = fdMap.get(fdId);
				}
				int fkId = bdyFood.getBdyFoodkind().getFkId();
				System.out.println("\tfkId : "+fkId);
				int setId = Integer.parseInt(food.getString("setId", DEFAULT_VALUE));
				
				// 2.set
				if (lastSetId != setId) {
					System.out.println("set change");
					if (setMap == null || setMap.size()==0) {
						System.out.println("set not found in session");
						bdySet = setDao.getSet(setId);
					} else {
						bdySet = setMap.get(setId);
					}
				}
				
				System.out.println("\tsetId : "+setId);
				double fdPrice = bdyFood.getPrice();
				double setBasePrice = 0;
				for (BdySetdetail detail : bdySetdetails) {
					if (detail.getBdyFoodkind().getFkId() == fkId) {
						setBasePrice = detail.getPrice();
					}
				}
				if (setBasePrice == 0) {
					System.out.println("Error : setBasePrice not found!");
				}
				
				System.out.println("\tprice : "+fdPrice+", "+setBasePrice);
				double addMoney = 0;
				if (setBasePrice!=0 && fdPrice>setBasePrice) {
					addMoney = fdPrice - setBasePrice;
				}
				System.out.println("\taddMoney : "+addMoney);
				BdyFoodkind foodkind = bdyFood.getBdyFoodkind();
				
				// 一筆OrderList完成, 加進lists中
				BdyOrderlist odlist = new BdyOrderlist(newOrder, bdySet, bdyFood, foodkind, addMoney, 0);
				lists.add(odlist);
				System.out.println("\t-------");
			}
			// 將lists批次寫入資料庫
			orderlistDao.batchInsert(lists);
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

		  long interval = System.currentTimeMillis() - start;
		  System.out.println("Complete("+interval/1000.0+"s)");
		  
	}
	
	public TreeMap<String, TreeMap<Integer,Object>> getOrderInitialData () {
		TreeMap<String, TreeMap<Integer,Object>> map = null;
				
		return map;
	}
	public JsonObject makeJSONFoods(TreeMap<Integer, BdyFood> fdMap, TreeMap<Integer, BdyFoodkind> fkMap, TreeMap<Integer, BdyMainkind> mkMap) {
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
		Iterator<Integer> mkKeys = mkMap.keySet().iterator();
		Iterator<Integer> fdKeys = fdMap.keySet().iterator();
		// 主餐部分
		while (mkKeys.hasNext()) {
			BdyMainkind mk = mkMap.get(mkKeys.next());
			int mkId = mk.getMkId();
			int fkId = -1;
			JsonArrayBuilder foodBuilder = Json.createArrayBuilder();
			//--------------------------------//
			List<BdyFood> mkIdFoods = new ArrayList<BdyFood>();
			fdKeys = fdMap.keySet().iterator();
			while (fdKeys.hasNext()) {
				BdyFood food = fdMap.get(fdKeys.next());
				if (food.getBdyMainkind() != null) {
					if (food.getBdyMainkind().getMkId() == mkId) {
						mkIdFoods.add(food);
					}
				}
			}
			//--------------------------------//
			
			for (BdyFood food : mkIdFoods) {
				if (fkId == -1) {
					fkId = food.getBdyFoodkind().getFkId();
				}
				foodBuilder.add(Json.createObjectBuilder()
									.add("fdId", food.getFdId())
									.add("fdName", food.getName())
									.add("fkId", fkId)
									.build());
			}
			mkBuilder.add(Json.createObjectBuilder()
					 		  .add(mk.getName(), foodBuilder));
		}
		
		JsonArrayBuilder fkBuilder = Json.createArrayBuilder();

		//--------------------------------//
		Iterator<Integer> fkKeys = fkMap.keySet().iterator();
		//--------------------------------//
//		List<BdyFoodkind> fks = foodkindDao.getAllFoodkind();

		/*
		 * 非主餐部分
		 */
		while (fkKeys.hasNext()) {
//		for (BdyFoodkind fk : fks) {
			BdyFoodkind fk = fkMap.get(fkKeys.next());
			int fkId = fk.getFkId();
			
			//--------------------------------//
			List<BdyFood> fkIdFoods = new ArrayList<BdyFood>();
			fdKeys = fdMap.keySet().iterator();
			while (fdKeys.hasNext()) {
				BdyFood food = fdMap.get(fdKeys.next());
				if (food.getBdyFoodkind().getFkId() == fkId) {
					fkIdFoods.add(food);
				}
			}
			//--------------------------------//
			
//			List<BdyFood> foods = foodDao.getFoodsByFkId(fk.getFkId());
			if (fkIdFoods!=null && fkIdFoods.size()!=0 && fkIdFoods.get(0).getBdyMainkind()!=null) {
				continue;
			}
			JsonArrayBuilder foodBuilder = Json.createArrayBuilder();
			for (BdyFood food : fkIdFoods) {
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
	public JsonArray makeJSONSets(TreeMap<Integer, BdySet> setMap, TreeMap<Integer, BdySetdetail> sdMap) {
		JsonArrayBuilder aryBuilder = Json.createArrayBuilder();
		
		Iterator<Integer> setKeys = setMap.keySet().iterator();
		while (setKeys.hasNext()) {
			BdySet set = setMap.get(setKeys.next());
			int setId = set.getSetId();

			Iterator<Integer> sdKeys = sdMap.keySet().iterator();
			List<BdySetdetail> details = new ArrayList<BdySetdetail>();
			while (sdKeys.hasNext()) {
				BdySetdetail sd = sdMap.get(sdKeys.next());
				if (sd.getBdySet().getSetId() == setId) {
					details.add(sd);
				}
			}
			
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
	
	public JsonArray makeJSONFks(TreeMap<Integer, BdyFoodkind> fkMap) {
		JsonArrayBuilder aryBuilder = Json.createArrayBuilder();
		Iterator<Integer> fkKeys = fkMap.keySet().iterator();
		while (fkKeys.hasNext()) {
			BdyFoodkind fk = fkMap.get(fkKeys.next());
			aryBuilder.add(Json.createObjectBuilder()
							   .add("fkName", fk.getName())
							   .add("fkId", fk.getFkId())
							   .build());
		};

		return aryBuilder.build();
	}
	
}
