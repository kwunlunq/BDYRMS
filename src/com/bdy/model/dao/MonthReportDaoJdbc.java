package com.bdy.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.bdy.model.MonthReport;

public class MonthReportDaoJdbc {
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
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
}
