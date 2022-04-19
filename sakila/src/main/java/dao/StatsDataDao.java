package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.DBUtil;

public class StatsDataDao {
	// 1) customer 별 총 amount 180$ 이상
	public List<Map<String,Object>> amountByCoustomer(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DBUtil
		conn = DBUtil.getConnection();
		// SQL 쿼리
		String sql = 
				"SELECT p.customer_id customerId,"
		+ "		CONCAT (c.first_name,' ',c.last_name) name, "
		+ "		SUM(amount) total "
		+ "	FROM payment p INNER JOIN customer c "
		+ "	ON p.customer_id = c.customer_id "
		+ "	GROUP BY p.customer_id "
		+ "	HAVING SUM(amount) >=180"
		+ "	ORDER BY SUM(amount) DESC";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("customerId",rs.getInt("customerId"));
				m.put("name", rs.getString("name"));
				m.put("total", rs.getString("total"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
	
	// 2) 영화 제일 많이 빌려간 customer 5명
	public List<Map<String,Object>> countByCoustomer(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DBUtil
		conn = DBUtil.getConnection();
		// SQL 쿼리
		String sql = "SELECT p.customer_id customerId, "
				+ "			CONCAT (c.first_name,' ',c.last_name) name, "
				+ "			COUNT(*) total "
				+ "		FROM payment p INNER JOIN customer c "
				+ "		ON p.customer_id = c.customer_id "
				+ "		GROUP BY p.customer_id "
				+ "		ORDER BY COUNT(*) DESC "
				+ "		LIMIT 0,5";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("customerId",rs.getInt("customerId"));
				m.put("name", rs.getString("name"));
				m.put("total", rs.getString("total"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
	
	// 3) rental_rate별 영화 갯
	public List<Map<String,Object>> filmCountByRentalRate(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DBUtil
		conn = DBUtil.getConnection();
		// SQL 문자열 쿼리
		String sql = "SELECT rental_rate rentalRate,"
				+ "			COUNT(*) total "
				+ "		FROM film "
				+ "		GROUP BY rental_rate "
				+ "		ORDER BY COUNT(*) DESC";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("rentalRate",rs.getDouble("rentalRate"));
				m.put("total", rs.getInt("total"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
	
	// 4) rating별 영화 갯수
	public List<Map<String,Object>> filmCountByRating(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DBUtil
		conn = DBUtil.getConnection();
		// SQL 쿼리
		String sql = "SELECT rating,"
				+ "			COUNT(*) total"
				+ "		FROM film "
				+ "		GROUP BY rating "
				+ "		ORDER BY COUNT(*) DESC";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("rating",rs.getString("rating"));
				m.put("total", rs.getInt("total"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
	
	// 5) language 별 영화 갯수
	public List<Map<String,Object>> filmCountByLanguage(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DBUtil
		conn = DBUtil.getConnection();
		// SQL 쿼리
		String sql = "SELECT l.name name,"
				+ "				 COUNT(*) total "
				+ "		FROM film f INNER JOIN language l "
				+ "		ON f.language_id = l.language_id "
				+ "		GROUP BY l.language_id";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("name",rs.getString("name"));
				m.put("total", rs.getInt("total"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
	
	// 6) length 별 영화 갯수
	public List<Map<String,Object>> filmCountByLength(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DBUtil
		conn = DBUtil.getConnection();
		// SQL 쿼리
		String sql = "SELECT t.length2 filmLength, "
				+ "		 COUNT(*) total "
				+ "	FROM (SELECT title, "
				+ "				length,  "
				+ "			CASE WHEN LENGTH<=60 then 'less1H' "
				+ "				  WHEN LENGTH BETWEEN 61 AND 119 then 'between1Hand2H' "
				+ "				  WHEN LENGTH BETWEEN 121 AND 179 then 'betwwen2Hand3H' "
				+ "				  ELSE 'more3H' "
				+ "			END LENGTH2	 	 "
				+ "			FROM film) t "
				+ "	GROUP BY t.length2";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("filmLength",rs.getString("filmLength"));
				m.put("total", rs.getInt("total"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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

	// 7) actor 별 영화 가장 많이 출현한 배우 5명
	public List<Map<String,Object>> actorByFilmCount(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DBUtil
		conn = DBUtil.getConnection();
		// SQL 쿼리
		String sql ="SELECT"
				+ "		 fa.actor_id, "
				+ "		 CONCAT (a.first_name,' ',a.last_name) actorName, "
				+ "		 COUNT(*) totalFilmCount  "
				+ "FROM film_actor fa "
				+ "INNER JOIN actor a "
				+ "ON fa.actor_id =a.actor_id "
				+ "GROUP BY fa.actor_id "
				+ "LIMIT 0,5";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("actorName",rs.getString("actorName"));
				m.put("totalFilmCount", rs.getInt("totalFilmCount"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
	
	// 8) 영화별 빌려간 횟수와 총 매출
	public List<Map<String,Object>> filmByRentalAmount(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DBUtil
		conn = DBUtil.getConnection();
		// SQL 쿼리
		String sql ="SELECT f.title title, "
				+ "		COUNT(*) total, "
				+ "		SUM(p.amount) amount "
				+ "FROM payment p "
				+ "	INNER JOIN rental r "
				+ "	ON r.rental_id = p.rental_id "
				+ "	INNER JOIN inventory i "
				+ "	ON i.inventory_id = r.inventory_id "
				+ "	INNER JOIN film f "
				+ "	ON f.film_id = i.film_id "
				+ "GROUP BY f.film_id "
				+ "LIMIT 0,5";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("title",rs.getString("title"));
				m.put("total", rs.getInt("total"));
				m.put("amount", rs.getInt("amount"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
	
	// 9) store 각 매장마다 요일별 매출(월, 화, 수, 목, 금, 토, 일)
	public List<Map<String,Object>> amountByDayOfWeek(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		// DB 초기화 
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DBUtil
		conn = DBUtil.getConnection();
		// SQL 쿼리
		String sql ="SELECT t.w, "
				+ "		s.store_id storeId, "
				+ "		CASE t.w "
				+ "		 	WHEN 0 THEN '월' "
				+ "		 	WHEN 1 THEN '화' "
				+ "		 	WHEN 2 THEN '수' "
				+ "		 	WHEN 3 THEN '목' "
				+ "		 	WHEN 4 THEN '금' "
				+ "		 	WHEN 5 THEN '토' "
				+ "			WHEN 6 THEN '일' "
				+ "		END DAYOFWEEK, "
				+ "		 t.c cnt "
				+ "FROM (SELECT staff_id, WEEKDAY(payment_date) w, COUNT(*) c "
				+ "		FROM payment "
				+ "		GROUP BY staff_id, WEEKDAY(payment_date)) t "
				+ "	INNER JOIN staff s "
				+ "	ON t.staff_id = s.staff_id "
				+ " ORDER BY s.store_id, t.w ASC "
				+ " LIMIT 0,14";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("storeId", rs.getInt("storeId"));
				m.put("DAYOFWEEK",rs.getString("DAYOFWEEK"));
				m.put("cnt", rs.getInt("cnt"));
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
}