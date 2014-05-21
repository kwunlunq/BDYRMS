package com.bdy.model;

import java.util.Set;

public class CheckOut {

	
	private Integer tabId;
	private Set<BdyOrder> orders;
	private java.util.Date endDate;
	private Integer custNum;
	private BdyDiscount discount;
	private Double price;
	private BdyEmp emp;
	private Integer finalPrice;
	public Integer getTabId() {
		return tabId;
	}
	public void setTabId(Integer tabId) {
		this.tabId = tabId;
	}
	public Set<BdyOrder> getOrders() {
		return orders;
	}
	public void setOrders(Set<BdyOrder> orders) {
		this.orders = orders;
	}
	public java.util.Date getEndDate() {
		return endDate;
	}
	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}
	public Integer getCustNum() {
		return custNum;
	}
	public void setCustNum(Integer custNum) {
		this.custNum = custNum;
	}
	public BdyDiscount getDiscount() {
		return discount;
	}
	public void setDiscount(BdyDiscount discount) {
		this.discount = discount;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public BdyEmp getEmp() {
		return emp;
	}
	public void setEmp(BdyEmp emp) {
		this.emp = emp;
	}
	public Integer getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(Integer finalPrice) {
		this.finalPrice = finalPrice;
	}

}
