package com.bdy.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import com.bdy.model.BdyOrderlistReport;
import com.bdy.model.DayFoodAmountReport;
import com.bdy.model.MonthReport;

public class ReportDaoJdbc {
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/*
	 * 輸入年份月份取得當月來客數與當月營收的DAO By Frank
	 */
	private static final String SELECT_BY_YEAROFMONTH = "select sum(CUST_NUM) as DAY_TATOL_CUST_NUM,sum(FIN_PRICE) as DAY_TATOL_FIN_PRICE,day(END_DATE) as DAY_IN_MONTH from BDY_BILL where  Year(END_DATE) = ? And Month(END_DATE) = ? GROUP BY day(END_DATE)";

	public List<MonthReport> getMonthRevenueDetailsDB(int year, int month) {

		List<MonthReport> result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(SELECT_BY_YEAROFMONTH);
			stmt.setInt(1, year);
			stmt.setInt(2, month);
			rset = stmt.executeQuery();
			result = new ArrayList<MonthReport>();
			while (rset.next()) {
				MonthReport temp = new MonthReport();
				temp.setDayInMonth(rset.getInt("DAY_IN_MONTH"));
				temp.setDayTatolCustNum(rset.getInt("DAY_TATOL_CUST_NUM"));
				temp.setDayTatolFinPrice(rset.getDouble("DAY_TATOL_FIN_PRICE"));
				result.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
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
	 * 依照食物種類取得當天食物(非主餐)數量的 DAO By Frank
	 */

	private static final String SELECT_BY_DAYOFFOOD = "select COUNT(FOOD_NAME) as AMOUNT, FOOD_NAME,FOODKIND_NAME from BDY_ORDERLIST_REPORT o inner join BDY_BILL_HISTORY b on o.BILL_ID=b.BILL_ID where MAINKIND_NAME is null and FOODKIND_NAME = ? and  YEAR(END_DATE) = ? and MONTH(END_DATE) = ? and DAY(END_DATE) = ? group by FOOD_NAME,FOODKIND_NAME order by FOODKIND_NAME";
		public List<DayFoodAmountReport> getDayFoodAmount(String foodkindName,java.util.Date date) {

		List<DayFoodAmountReport> result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(SELECT_BY_DAYOFFOOD);
			stmt.setString(1, foodkindName);
			stmt.setInt(2, year);
			stmt.setInt(3, month);
			stmt.setInt(4, day);
			rset = stmt.executeQuery();
			result = new ArrayList<DayFoodAmountReport>();
			while (rset.next()) {
				DayFoodAmountReport temp = new DayFoodAmountReport();
				temp.setAmount(rset.getInt("AMOUNT"));
				temp.setFoodName(rset.getString("FOOD_NAME"));
				temp.setFoodKindName(rset.getString("FOODKIND_NAME"));
				result.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
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
	 * 依照日期取得食物種類名稱(非主餐)的 DAO By Frank
	 */
	
	private static final String SELECT_BY_DAYFOODKINDNAME = "select FOODKIND_NAME from BDY_ORDERLIST_REPORT o inner join BDY_BILL_HISTORY b on o.BILL_ID=b.BILL_ID where MAINKIND_NAME is null and YEAR(END_DATE) = ? and MONTH(END_DATE) = ? and DAY(END_DATE) = ? group by FOODKIND_NAME,MAINKIND_NAME order by  FOODKIND_NAME";

	public List<BdyOrderlistReport> getDayFoodKindName(java.util.Date date) {
		List<BdyOrderlistReport> result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(SELECT_BY_DAYFOODKINDNAME);
			stmt.setInt(1, year);
			stmt.setInt(2, month);
			stmt.setInt(3, day);
			rset = stmt.executeQuery();
			result = new ArrayList<BdyOrderlistReport>();
			while (rset.next()) {
				BdyOrderlistReport temp = new BdyOrderlistReport();
				temp.setFoodkindName(rset.getString("FOODKIND_NAME"));
				result.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
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
	 * 依照食物種類編號取得當月食物數量的DAO By Frank
	 */

	private static final String SELECT_BY_MONTHOFFOOD = "select count(BDY_ORDERLIST.FD_ID) as amount,BDY_FOOD.NAME from BDY_ORDER inner join BDY_ORDERLIST on BDY_ORDERLIST.OD_ID=BDY_ORDER.OD_ID inner join BDY_FOOD on BDY_ORDERLIST.FD_ID=BDY_FOOD.FD_ID where year(BDY_ORDER.ORD_TIME) = ? and month(BDY_ORDER.ORD_TIME) = ?  and BDY_FOOD.FK_ID= ? group by BDY_FOOD.NAME";

	public List<DayFoodAmountReport> getMonthFoodAmount(int year, int month,
			int foodKindID) {

		List<DayFoodAmountReport> result = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.prepareStatement(SELECT_BY_MONTHOFFOOD);
			stmt.setInt(1, year);
			stmt.setInt(2, month);
			stmt.setInt(3, foodKindID);
			rset = stmt.executeQuery();
			result = new ArrayList<DayFoodAmountReport>();
			while (rset.next()) {
				DayFoodAmountReport temp = new DayFoodAmountReport();
				temp.setAmount(rset.getInt("AMOUNT"));
				temp.setFoodName(rset.getString("FOODNAME"));
				result.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
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
