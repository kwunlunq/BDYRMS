package com.bdy.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

import org.directwebremoting.Browser;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.ui.dwr.Util;

import utils.Data;

import com.bdy.model.BdyDiscount;
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

public class OrderService {
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
	
	/*
	 * --------------------------------------------------------
	 * 從資料庫取得點餐所需資料，傳給servlet存進session
	 * --------------------------------------------------------
	 */
	
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
	public TreeMap<Integer, BdyDiscount> getAllDiscounts() {
		TreeMap<Integer, BdyDiscount> dicsounts = new TreeMap<Integer, BdyDiscount>();
		List<BdyDiscount> discountlist = discountDao.getAllDiscount();
		for (BdyDiscount discount : discountlist) {
			dicsounts.put(discount.getDisId(), discount);
		}
		return dicsounts;
	}
	
	public JsonObject getOrderNotCheckAndCustNum() {
		JsonObject object = Json.createObjectBuilder()
								.add("orderNum", orderDao.getOrderNotCheckNum())
//								.add("custNum", tableDao.getCustNum())
								.add("odlistNum", orderlistDao.getUnservedOdlist())
								.build();
		return object;
	}
	
	public Double getTodayIncome() {
		return billDao.getTodayIncome();
	}
	/*
	 * --------------------------------------------------------
	 * 接收servlet傳來的session中的資料，做成Json格式傳回去
	 * --------------------------------------------------------
	 */

	public JsonArray makeJSONTables(TreeMap<Integer, BdyTable> tableMap, TreeMap<Integer, BdyFloor> floorMap) {
		JsonArrayBuilder aryBuilder = Json.createArrayBuilder();
		// 走訪map : 呼叫keySet()取出所有的key, 來取出map所有的value
		Iterator<Integer> flKeys = floorMap.keySet().iterator();
		while (flKeys.hasNext()) {
			BdyFloor floor = floorMap.get(flKeys.next());
			int fId = floor.getFloorid();
 
			JsonArrayBuilder tbary = Json.createArrayBuilder();
			
			Iterator<Integer> tbKeys = tableMap.keySet().iterator();
			List<BdyTable> fIdTables = new ArrayList<BdyTable>();
			while (tbKeys.hasNext()) {
				BdyTable table = tableMap.get(tbKeys.next());
				if (table.getBdyFloor().getFloorid() == fId) {
					fIdTables.add(table);
				}
			}
			List<BdyTable> tables = tableDao.getTableByFloor(floor.getFloorid());
			for (BdyTable table: tables) {
				tbary.add(Json.createObjectBuilder()
							  .add("tbId", table.getTbId())
							  .add("tbName", table.getName())
							  .add("tbSize", table.getSize())
							  .add("tbState", table.getTableState())
							  .add("custNum", table.getCustNum())
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

		Iterator<Integer> fkKeys = fkMap.keySet().iterator();

		// 非主餐部分
		while (fkKeys.hasNext()) {
			BdyFoodkind fk = fkMap.get(fkKeys.next());
			int fkId = fk.getFkId();
			
			List<BdyFood> fkIdFoods = new ArrayList<BdyFood>();
			fdKeys = fdMap.keySet().iterator();
			while (fdKeys.hasNext()) {
				BdyFood food = fdMap.get(fdKeys.next());
				if (food.getBdyFoodkind().getFkId() == fkId) {
					fkIdFoods.add(food);
				}
			}
			
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

	public JsonArray makeJSONDiss(TreeMap<Integer, BdyDiscount> disMap) {
		JsonArrayBuilder aryBuilder = Json.createArrayBuilder();
		Iterator<Integer> disKeys = disMap.keySet().iterator();
		while (disKeys.hasNext()) {
			BdyDiscount dis = disMap.get(disKeys.next());
			aryBuilder.add(Json.createObjectBuilder()
							   .add("disId", dis.getDisId())
							   .add("disName", dis.getName())
							   .add("disPrice", dis.getDisPrice())
							   .build());
		};

		return aryBuilder.build();
	}
	/*
	 * --------------------------------------------------------
	 * 製作點餐單
	 * 接收前端完成點餐產生的點單餐物件(已轉成Json Object)，
	 * 處理之後產生一筆Order及多筆OrderList寫進資料庫
	 * --------------------------------------------------------
	 */
	public void readOrderJson(JsonObject object, TreeMap<Integer, BdyFood> fdMap,
		TreeMap<Integer, BdySet> setMap, TreeMap<Integer, BdyTable> tableMap, 
		BdyEmp bdyEmp) {
		System.out.println("Processing order...");
		long start = System.currentTimeMillis();
	  
		try {
			int tbId = Integer.parseInt(object.getString("TableId", DEFAULT_VALUE));
			System.out.println("TableId : "+tbId);
			int custNum = Integer.parseInt(object.getString("CustNum", DEFAULT_VALUE));
			System.out.println("CustNum : "+custNum);
			String empId = object.getString("EmpId", "0");
			System.out.println("EmpId : "+empId);
			Calendar nowTime = new GregorianCalendar(Locale.TAIWAN);
			System.out.println("OrderTime : "+nowTime.getTime());
			
			BdyEmp emp;
			if (bdyEmp!=null) {
				emp = bdyEmp;
			} else {
				System.out.println("EMP not found in session");
				emp = empDao.getEmpById(empId);
			}
			
			BdyTable tb;
			if (tableMap!=null && tableMap.size()!=0) {
				tb = tableMap.get(tbId);
			} else {
				System.out.println("TB not found in session");
				tb = tableDao.getTableById(tbId);
			}
			System.out.println("EmpId from db : "+emp.getEmpId());
			System.out.println("TbId from db : "+tb.getTbId());
			BdyOrder newOrder = new BdyOrder(emp, tb, nowTime.getTime(), 0, custNum);
//			orderDao.insert(newOrder);
//			BdyOrder newOrder = orderDao.getOrderByOrder(order);
//			System.out.println("\nNew order generated. Id = "+newOrder.getOdId());
			System.out.println("\t   ----");
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
				if (fdMap == null || fdMap.size()==0) {
					System.out.println("food not found in session");
					bdyFood = foodDao.getFood(fdId);
				}else {
					bdyFood = fdMap.get(fdId);
					if (bdyFood == null ) {
						System.out.println("bdyFood is null!");
					}
				}
				int fkId = bdyFood.getBdyFoodkind().getFkId();
				System.out.println("\tfkId : "+fkId);
				int setId = Integer.parseInt(food.getString("setId", DEFAULT_VALUE));
				
				if (lastSetId != setId) {
					lastSetId = setId;
					System.out.println("\t(set change)");
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
						if (detail.getPrice() == null) {
							System.out.println("Error : setBasePrice not found");
						} else {
							setBasePrice = detail.getPrice();
						}
					}
				}
				
				System.out.println("\tprice : (b)"+setBasePrice+", (f)"+fdPrice);
				double addMoney = 0;
				if (setBasePrice!=0 && fdPrice>setBasePrice) {
					addMoney = fdPrice - setBasePrice;
				}
				System.out.println("\taddMoney : "+addMoney);
				BdyFoodkind foodkind = bdyFood.getBdyFoodkind();
				
				// 一筆OrderList完成, 加進lists中
				BdyOrderlist odlist = new BdyOrderlist(newOrder, bdySet, bdyFood, foodkind, addMoney, 0);
				lists.add(odlist);
				System.out.println("\t   ----");
				
			}
			// insert新的order
			orderDao.insert(newOrder);
			System.out.println("**Order process complete**");
			// 批次insert新的orderlists
			orderlistDao.batchInsert(lists);
			System.out.println("**Orderlists process complete**");
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
		  System.out.println("("+interval/1000.0+"s)\n");
	}

	/*
	 * --------------------------------------------------------
	 * DWR function
	 * 由order.js呼叫, 在 kitchenAllView.jsp id為kitchenDiv
	 * 的element放入資料
	 * --------------------------------------------------------
	 */

	 public void notifyKitchen(Data data) {
	   List<Data> messages = new ArrayList<Data>();
	//   messages.add(new Data("testing" + count++));
	   messages.add(data);

	   // Collection sessions = wctx.getAllScriptSessions();
	   WebContext wctx = WebContextFactory.get();				   ///kitchen/kitchenAllView.jsp
//	   Collection<?> sessions = wctx.getScriptSessionsByPage("/BDYRMS/kitchen/kitchenAllView.jsp");
//	   Collection<?> sessions = wctx.getScriptSessionsByPage("/BDYRMS/testdwr.jsp");
//	   Util utilAll = new Util(sessions);
//	   utilAll.addOptions("updates", messages, "value");
	   
//	   Collection<?> sessions = wctx.getScriptSessionsByPage("/BDYRMS/testdwr.jsp");
//	   Collection session = Browser.withPage("/BDYRMS/testdwr.jsp", task);
//	   org.directwebremoting.ui.dwr.Util utilAll = new Util(sessions);
//	   utilAll.addOptions("updates", messages, "value");
		   
	 }
	 
	public void sendMes(String mes) {
		send("新點餐單!");
	}

	public void send(final String output) {
//		String page = ServerContextFactory.get().getContextPath()
//				+ "/kitchen/kitchenAllView.jsp";
		Browser.withAllSessions(ServerContextFactory.get(), new Runnable() {
			public void run() {
				Util.setValue("kitchenDiv", output); // news 客户端jsp里面textarea的id
			}
		});
//		Browser.withPage(page, new Runnable() {
//			public void run() {
//				Util.setValue("kitchenDiv", output); // news 客户端jsp里面textarea的id
//			}
//		});
	}
	
	
}
