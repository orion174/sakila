package dao;

import java.sql.*;
import java.util.*;

import util.DBUtil;

/* 사킬라 데이터베이스 프로시져 rewards_report 코드
 * vo 안쓰고 구현함(HashMap 사용)
 * RewadrsReport
 */
 public class RewardsReport {
	// rewards_report call
	public Map<String,Object> rewardsReport(int minMonthlyPurchase, double minDollarAmountPurchase) {
			
		// 데이터 저장 hashmap사용 
		Map<String, Object> map = new HashMap<String,Object>();
			
		// DB 초기화
		Connection conn = null;
		CallableStatement stmt = null;
		ResultSet rs = null;
			
		// hashmap 
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>(); 
			
		// 쿼리문의 count를 저장할 변수
		Integer count = 0; 
			
		// DBUtil 호출
		conn = DBUtil.getConnection();
		try {	
			// SQL 쿼리 호출
			stmt = conn.prepareCall("{CALL rewards_report(?,?,?)}");
					
			stmt.setInt(1, minMonthlyPurchase);
			stmt.setDouble(2, minDollarAmountPurchase);
			stmt.registerOutParameter(3,Types.INTEGER);
			rs = stmt.executeQuery();
					
			// 데이터 변환
			while(rs.next()) {
				HashMap<String,Object>hm = new HashMap<String,Object>();
				hm.put("customerId",rs.getInt(1));
				hm.put("storeId",rs.getInt(2));
				hm.put("firstName",rs.getString(3));
				hm.put("lastName",rs.getString(4));
				hm.put("email",rs.getString(5));
				hm.put("addressId",rs.getInt(6));
				hm.put("active",rs.getInt(7));
				hm.put("createDate",rs.getString(8));
				hm.put("updateDate",rs.getString(9));

				// 디버깅 코드
				System.out.println(rs.getInt(1) + " <--customerId");
				System.out.println(rs.getInt(2) + " <--storeId");
				System.out.println(rs.getString(3) + " <--firstName");
				System.out.println(rs.getString(4) + " <--lastName");
				System.out.println(rs.getString(5) + " <--email");
				System.out.println(rs.getInt(6) + " <--addressId");
				System.out.println(rs.getInt(7) + " <--active");
				System.out.println(rs.getString(8) + " <--createDate");
				System.out.println(rs.getString(9) + " <--updateDate");
				list.add(hm);
			}
			// count의 개수
			count = stmt.getInt(3); 
			System.out.println(count + " <--count");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		map.put("list", list);
		map.put("count", count);

		return map;
	}
		
	// rewardsReport 메서드 단위 테스트 
	public static void main(String[] args) {
		// RewardsReport 호출
		RewardsReport r = new RewardsReport();
			
		// 테스트 변수 값 저장
		int minMonthlyPurchase = 7;
		double minDollarAmountPurchase = 50.00;
		
		Map<String,Object> map = r.rewardsReport(minMonthlyPurchase, minDollarAmountPurchase);
			
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
			
		int count = (int)map.get("count");
		System.out.println(count + " <-- count");

	}
}
