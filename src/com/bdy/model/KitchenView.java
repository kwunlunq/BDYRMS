package com.bdy.model;

public class KitchenView  {
	private String  orderlistname;
	private int orderlistID;
	private int orderID;
	private int tableID;
	private java.util.Date orderDate;
	private int orderStatus;
	private Long outMealTime;
	
	public Long getOutMealTime() {
		return outMealTime;
	}
	public void setOutMealTime(Long outMealTime) {
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
