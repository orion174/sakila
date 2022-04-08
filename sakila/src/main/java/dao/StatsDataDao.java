package dao;

import java.util.*;
import java.sql.*;

import util.DBUtil;

public class StatsDataDao {
	public List<Map<String, Object>> amountByCoustomer() {
	      List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	      //
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      //
	      conn = DBUtil.getConnection();
	      //
	      String sql = "SELECT p.customer_id customerId,"
	            + "      concat(c.first_name,' ', c.last_name) name,"
	            + "      sum(p.amount) total"
	            + "      FROM payment p INNER JOIN customer c"
	            + "      ON p.customer_id = c.customer_id"
	            + "      GROUP BY p.customer_id"
	            + "      HAVING SUM(p.amount) >= 180"
	            + "      ORDER BY SUM(p.amount) DESC";
	      try {
	         stmt = conn.prepareStatement(sql);
	         rs = stmt.executeQuery();
	         while(rs.next()) {
	            Map<String, Object> m = new HashMap<>();
	            m.put("customerId",rs.getInt("customerId"));
	            m.put("name",rs.getString("name"));
	            m.put("total",rs.getDouble("total"));
	            list.add(m);
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            rs.close();
	            stmt.close();
	            conn.close();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	      return list;
	   }
	
	// 2. 가격별 영화개수
		public List<Map<String, Object>> countByRentalRate() {
		      List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		      Connection conn = null;
		      PreparedStatement stmt = null;
		      ResultSet rs = null;
		      conn = DBUtil.getConnection();
		      String sql = "SELECT rental_rate rentalRate, COUNT(*) cnt"
		      		+ " FROM film"
		      		+ " GROUP BY rental_rate"
		      		+ " ORDER BY rental_rate ASC;";
		      try {
		         stmt = conn.prepareStatement(sql);
		         rs = stmt.executeQuery();
		         while(rs.next()) {
		            Map<String, Object> m = new HashMap<>();
		            m.put("rentalRate",rs.getDouble("rentalRate"));
		            m.put("cnt",rs.getInt("cnt"));
		            list.add(m);
		         }
		      } catch (SQLException e) {
		         e.printStackTrace();
		      } finally {
		         try {
		            rs.close();
		            stmt.close();
		            conn.close();
		         } catch (SQLException e) {
		            e.printStackTrace();
		         }
		      }
		      return list;
		   }
		
		public List<Map<String, Object>> countByRating() {
		      List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		      Connection conn = null;
		      PreparedStatement stmt = null;
		      ResultSet rs = null;
		      conn = DBUtil.getConnection();
		      String sql = "SELECT rating, COUNT(*) cnt"
		      		+ " FROM film"
		      		+ " GROUP BY rating"
		      		+ " ORDER BY COUNT(*) DESC;";
		      try {
		         stmt = conn.prepareStatement(sql);
		         rs = stmt.executeQuery();
		         while(rs.next()) {
		            Map<String, Object> m = new HashMap<>();
		            m.put("rating",rs.getString("rating"));
		            m.put("cnt",rs.getInt("cnt"));
		            list.add(m);
		         }
		      } catch (SQLException e) {
		         e.printStackTrace();
		      } finally {
		         try {
		            rs.close();
		            stmt.close();
		            conn.close();
		         } catch (SQLException e) {
		            e.printStackTrace();
		         }
		      }
		      return list;
		   }

		public List<Map<String, Object>> countByLanguage() {
		      List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		      Connection conn = null;
		      PreparedStatement stmt = null;
		      ResultSet rs = null;
		      conn = DBUtil.getConnection();
		      String sql = "SELECT l.name language, COUNT(*) cnt3"
		      		+ " FROM film f JOIN language l"
		      		+ " ON f.language_id=l.language_id"
		      		+ " GROUP BY l.name;"
		      		+ "";
		      try {
		         stmt = conn.prepareStatement(sql);
		         rs = stmt.executeQuery();
		         while(rs.next()) {
		            Map<String, Object> m = new HashMap<>();
		            m.put("language",rs.getString("language"));
		            m.put("cnt3",rs.getInt("cnt3"));
		            list.add(m);
		         }
		      } catch (SQLException e) {
		         e.printStackTrace();
		      } finally {
		         try {
		            rs.close();
		            stmt.close();
		            conn.close();
		         } catch (SQLException e) {
		            e.printStackTrace();
		         }
		      }
		      return list;
		   }
	
		public List<Map<String, Object>> countByRunningTime() {
		      List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		      //
		      Connection conn = null;
		      PreparedStatement stmt = null;
		      ResultSet rs = null;
		      //
		      conn = DBUtil.getConnection();
		      //
		      String sql = "SELECT t.lengthTime, COUNT(*) cnt4"
		      		+ " FROM (SELECT title, LENGTH,"
		      		+ " case when LENGTH<=60 then 'less than 60'"
		      		+ " when LENGTH BETWEEN 61 AND 120 then 'between 61 and 120'"
		      		+ " when LENGTH BETWEEN 121 AND 180 then 'between 121 and 180'"
		      		+ " ELSE 'more than 180'"
		      		+ " END lengthTime"
		      		+ " FROM film) t"
		      		+ " GROUP BY t.lengthTime;"
		      		+ "";
		      try {
		         stmt = conn.prepareStatement(sql);
		         rs = stmt.executeQuery();
		         while(rs.next()) {
		            Map<String, Object> m = new HashMap<>();
		            m.put("lengthTime",rs.getString("lengthTime"));
		            m.put("cnt4",rs.getInt("cnt4"));
		            list.add(m);
		         }
		      } catch (SQLException e) {
		         e.printStackTrace();
		      } finally {
		         try {
		            rs.close();
		            stmt.close();
		            conn.close();
		         } catch (SQLException e) {
		            e.printStackTrace();
		         }
		      }
		      return list;
		   }
	
		
	// 오류
	public List<Map<String, Object>> salesDayOfWeek() {
		  List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		  //
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      //
	      conn = DBUtil.getConnection();
	      //
	      String sql = "SELECT s.staff_id, t.w, CASE t.w"
	            + "WHEN 0 THEN '월'"
	            + "WHEN 1 THEN '화'"
	            + "WHEN 2 THEN '수'" 
	            + "WHEN 3 THEN '목'" 
	            + "WHEN 4 THEN '금'" 
	            + "WHEN 5 THEN '토'" 
	            + "WHEN 6 THEN '일'" 
	            + "END DAYOFWEEK, t.c"
	            + "FROM (SELECT staff_id, WEEKDAY(payment_date) w, COUNT(*) c"
	            + "FROM payment"
	            + "GROUP BY staff_id, WEEKDAY(payment_date)) t"
	            + "INNER JOIN staff s"
	            + "ON t.staff_id = s.staff_id"
	            + "ORDER BY s.store_id, t.w ASC;"
	            + "";
	      try {
	         stmt = conn.prepareStatement(sql);
	         rs = stmt.executeQuery();
	         while(rs.next()) {
	            Map<String, Object> m = new HashMap<>();
	            m.put("staff_id",rs.getInt("staff_id"));
	            m.put("w",rs.getInt("w"));
	            m.put("DAYOFWEEK",rs.getString("DAYOFWEEK"));
	            m.put("c",rs.getInt("c"));
	            list.add(m);
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            rs.close();
	            stmt.close();
	            conn.close();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	      return list;
	}
	/*
	 # 7. (손님의)도시 매출 순위??
	SELECT ct.city, SUM(p.amount)
	FROM payment p
	LEFT JOIN customer c
	ON p.customer_id = c.customer_id
	LEFT JOIN address a
	ON c.address_id = a.address_id
	LEFT JOIN city ct
	ON a.city_id = ct.city_id
	GROUP BY ct.city
	ORDER BY SUM(p.amount) DESC
	public List<Map<String, Object>> salesRankingOfCity() {
		  List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		  //
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      //
	      conn = DBUtil.getConnection();
	      String sql = 
	    		  "      SELECT ct.city city"
	            + "		 SUM(p.amount) total1"
	            + "FROM payment p"
	            + "LEFT JOIN customer c ON p.customer_id = c.customer_id" 
	            + "LEFT JOIN address a ON c.address_id = a.address_id" 
	            + "LEFT JOIN city ct ON a.city_id = ct.city_id" 
	            + "GROUP BY ct.city" 
	            + "ORDER BY SUM(p.amount) DESC;"
	            + "";
	      try {
	         stmt = conn.prepareStatement(sql);
	         rs = stmt.executeQuery();
	         while(rs.next()) {
	            Map<String, Object> m = new HashMap<>();
	            m.put("city",rs.getString("city"));
	            m.put("total1",rs.getDouble("total1"));
	            list.add(m);
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            rs.close();
	            stmt.close();
	            conn.close();
	         } catch (SQLException e) {
	            e.printStackTrace();
	         }
	      }
	      return list;
	}
	
	
	*/
}
