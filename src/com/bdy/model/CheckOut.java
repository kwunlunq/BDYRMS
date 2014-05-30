package com.bdy.model;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CheckOut {

	
	private Integer tabId;//--桌號
	private Set<BdyOrder> orders;//--所有點餐單
	private java.util.Date endDate;//---結帳時間
	private Integer custNum;//---客數
	private BdyDiscount discount;//----折扣
	private Double price;//-----應付帳款
	private BdyEmp emp;//-----結帳員工
	private Integer finalPrice;//------------實收金額
	private String discription;//------------吃到蟑螂
	
	
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	private  Map<BdySet,List<BdyFood>> setMap = new TreeMap<BdySet,List<BdyFood>>(new Comparator<BdySet>() {

		@Override
		public int compare(BdySet o1, BdySet o2) {
			
			return new Integer(o1.getSetId()).compareTo(new Integer(o2.getSetId()));
		}
	});//-----map
	
	private Map<BdySet,List<List<?>>> setDetailMap = new TreeMap<BdySet,List<List<?>>>(new Comparator<BdySet>() {

		@Override
		public int compare(BdySet o1, BdySet o2) {
			
			return new Integer(o1.getSetId()).compareTo(new Integer(o2.getSetId()));
		}
	});//-----map
	
	
	
	public Map<BdySet, List<List<?>>> getSetDetailMap() {
		return setDetailMap;
	}
	public void setSetDetailMap(Map<BdySet, List<List<?>>> setDetailMap) {
		this.setDetailMap = setDetailMap;
	}
	public Map<BdySet, List<BdyFood>> getSetMap() {
		return setMap;
	}
	public void setSetMap(Map<BdySet, List<BdyFood>> setMap) {
		this.setMap = setMap;
	}
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
