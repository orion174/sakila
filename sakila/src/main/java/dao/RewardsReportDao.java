package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.DBUtil;
import vo.RewardCustomer;

// rewards_report 프로시저
public class RewardsReportDao {
	public Map<String, Object> rewardsReport(int minMonthlyPurchases, int minDollarAmuntPurchased) {
		Map<String, Object> map = new HashMap<String, Object>();

		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
		// 손님 정보 담을 리스트 선언
		List<RewardCustomer> list = new ArrayList<>();
		// registerOutParameter
		Integer count = 0;
		
		conn = DBUtil.getConnection();
		
		try {
			stmt = conn.prepareCall("{call rewards_report(?,?,?)}");
			stmt.setInt(1, minMonthlyPurchases);
			stmt.setInt(2, minDollarAmuntPurchased);
			stmt.registerOutParameter(3, Types.INTEGER);
			rs = stmt.executeQuery();
			while (rs.next()) { // 손님정보 출력
				RewardCustomer c = new RewardCustomer();
				c.setCustomerId(rs.getInt(1));
				c.setStoreId(rs.getInt(2));
				c.setFirstName(rs.getString(3));
				c.setLastName(rs.getString(4));
				c.setEmail(rs.getString(5));
				c.setAddressId(rs.getInt(6));
				c.setActive(rs.getInt(7));
				c.setCreateDate(rs.getString(8));
				c.setLastUpdate(rs.getString(9));
				list.add(c);
			}
			count = stmt.getInt(3); // 프로시저 3번째 out변수 값
		} catch (SQLException e) {
			e.printStackTrace();
		}
		map.put("customer", list);
		map.put("count", count);
		return map;
	}
}