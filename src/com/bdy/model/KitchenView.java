package com.bdy.model;

public class KitchenView  {
	private String  orderlistname;//-食物名稱:凱薩沙拉
	private int orderlistID;////明細ID
	private int orderID;//訂單ID
	private int tableID;//桌號
	private BdyTable table;
	private java.util.Date orderDate;//點單時間
	private int orderStatus;//出餐狀態
	private java.util.Date outMealTime;//出餐時間點
	private int foodkindID;
	
	public BdyTable getTable() {
		return table;
	}
	public void setTable(BdyTable table) {
		this.table = table;
	}
	public int getFoodkindID() {
		return foodkindID;
	}
	public void setFoodkindID(int foodkindID) {
		this.foodkindID = foodkindID;
	}
	public java.util.Date getOutMealTime() {
		return outMealTime;
	}
	public void setOutMealTime(java.util.Date outMealTime) {
		this.outMealTime = outMealTime;
	}
	public String getOrderlistname() {
		return orderlistname;
	}
	public void setOrderlistname(String orderlistname) {
		this.orderlistname = orderlistname;
	}
	public int getOrderlistID() {
		return orderlistID;
	}
	public void setOrderlistID(int orderlistID) {
		this.orderlistID = orderlistID;
	}
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public int getTableID() {
		return tableID;
	}
	public void setTableID(int tableID) {
		this.tableID = tableID;
	}
	public java.util.Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(java.util.Date orderDate) {
		this.orderDate = orderDate;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

}
