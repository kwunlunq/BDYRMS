package com.bdy.model;

// Generated 2014/4/17 �U�� 08:38:49 by Hibernate Tools 4.0.0

/**
 * BdySet generated by hbm2java
 */
public class BdySetdetail implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int sdId;
	private BdySet bdySet;
	private BdyFoodkind bdyFoodkind;
	private Double price;

	public BdySetdetail() {
	}

	public BdySetdetail(int sdId) {
		this.sdId = sdId;
	}

	public BdySetdetail(int sdId, BdySet bdySet, BdyFoodkind bdyFoodkind,
			Double price) {
		this.sdId = sdId;
		this.bdySet = bdySet;
		this.bdyFoodkind = bdyFoodkind;
		this.price = price;
	}

	public int getSdId() {
		return this.sdId;
	}

	public void setSdId(int sdId) {
		this.sdId = sdId;
	}

	public BdySet getBdySet() {
		return this.bdySet;
	}

	public void setBdySet(BdySet bdySet) {
		this.bdySet = bdySet;
	}

	public BdyFoodkind getBdyFoodkind() {
		return this.bdyFoodkind;
	}

	public void setBdyFoodkind(BdyFoodkind bdyFoodkind) {
		this.bdyFoodkind = bdyFoodkind;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
