package com.bdy.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import com.bdy.model.DayFoodAmountReport;
import com.bdy.model.MonthReport;

public class ReportDaoJdbc {
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	
	/*
	 * 輸入年份月份取得當月來客數與當月營收的DAO
	 * By Frank
	 */
	private static final String SELECT_BY_YEAROFMONTH = 
			"select sum(CUST_NUM) as DAY_TATOL_CUST_NUM,sum(FIN_PRICE) as DAY_TATOL_FIN_PRICE,day(END_DATE) as DAY_IN_MONTH from BDY_BILL where  Year(END_DATE) = ? And Month(END_DATE) = ? GROUP BY day(END_DATE)";
	
	public List<MonthReport> getMonthRevenueDetailsDB(int year,int month){

		List<MonthReport> result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		try {
			conn=dataSource.getConnection();
			stmt = conn.prepareStatement(SELECT_BY_YEAROFMONTH);
			stmt.setInt(1, year);
			stmt.setInt(2, month);
			rset = stmt.executeQuery();
			result = new ArrayList<MonthReport>();
			while(rset.next()){
				MonthReport temp = new MonthReport();
				temp.setDayInMonth(rset.getInt("DAY_IN_MONTH"));
				temp.setDayTatolCustNum(rset.getInt("DAY_TATOL_CUST_NUM"));
				temp.setDayTatolFinPrice(rset.getDouble("DAY_TATOL_FIN_PRICE"));
				result.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rset!=null) {
				try {
					rset.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt!=null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	
	/*
	 * 依照食物種類編號取得當天食物數量的DAO
	 * By Frank
	 */
	private static final String SELECT_BY_DAYOFFOOD = 
			"select count(BDY_ORDERLIST.FD_ID) as amount,BDY_FOOD.NAME from BDY_ORDER inner join BDY_ORDERLIST on BDY_ORDERLIST.OD_ID=BDY_ORDER.OD_ID inner join BDY_FOOD on BDY_ORDERLIST.FD_ID=BDY_FOOD.FD_ID where (BDY_ORDER.ORD_TIME between ? and ?) And BDY_FOOD.FK_ID= ? group by BDY_FOOD.NAME";

	public List<DayFoodAmountReport> getDayFoodAmount(java.util.Date orderDate,int foodKindID){
		
		List<DayFoodAmountReport> result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		try {
			conn=dataSource.getConnection();
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			calendar.setTime(orderDate);
			calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+1);
			stmt = conn.prepareStatement(SELECT_BY_DAYOFFOOD);
			stmt.setDate(1,new java.sql.Date(orderDate.getTime()));
			stmt.setDate(2,new java.sql.Date(calendar.getTime().getTime()));
			stmt.setInt(3, foodKindID);
			rset = stmt.executeQuery();
			result = new ArrayList<DayFoodAmountReport>();
			while(rset.next()){
				DayFoodAmountReport temp = new DayFoodAmountReport();
				temp.setAmount(rset.getInt("AMOUNT"));
				temp.setName(rset.getString("NAME"));
				result.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rset!=null) {
				try {
					rset.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt!=null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}		
}
