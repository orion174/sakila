package dao;

import java.util.*;
import java.sql.*;

import util.DBUtil;

/* film 테이블 FilmDao
 * 프로시져 film_in_stock + film_not_in_stock 구현
 * film 테이블 검색기능 SELECT film price 구현 
 */
public class FilmDao {

	// 1) 프로시져 film_in_stock
	public Map<String,Object> filmInStock(int filmId, int storeId) {
		// HasgMap
		Map<String,Object> map = new HashMap<String, Object>();
		
		// DB 초기화
		Connection conn = null;
		// PreparedStatement -> 쿼리를 실행
		// CallableStatement -> 프로시저를 실행
		CallableStatement stmt = null; 
		ResultSet rs = null; 
		
		// select inventory_id...
		List<Integer> list = new ArrayList<>();
		// select count(inventory_id)..	
		Integer count = 0;  
		
		// DBUtil 호출
		conn = DBUtil.getConnection();
		
		try {
			stmt = conn.prepareCall("{CALL film_in_stock(?,?,?)}");
			stmt.setInt(1,filmId);
			stmt.setInt(2,storeId);
			stmt.registerOutParameter(3,Types.INTEGER);
			rs= stmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getInt(1)); // rs.getInt("inventory_id")
			}
			count = stmt.getInt(3); // 프로시저 3번째 out변수 값
		} catch (SQLException e) {
			e.printStackTrace();
		}

		map.put("list", list);
		map.put("count", count);

		return map;
	}

	// 2) 프로시져 film_not_in_stock
	public Map<String,Object> filmNotInStock(int filmId, int storeId) {
		// Hash Map
		Map<String, Object> map = new HashMap<String,Object>();
		
		// DB 초기화
		Connection conn = null;
		// PreparedStatement -> 쿼리를 실행
		// CallableStatement -> 프로시저를 실행
		CallableStatement stmt = null;
		ResultSet rs = null;
		
		// select inventory_id...
		List<Integer> list = new ArrayList<>(); 
		// select count(inventory_id)..
		Integer count = 0; 
		
		// DBUtil 호출
		conn=DBUtil.getConnection();
		
		try {
			stmt = conn.prepareCall("{call film_not_in_stock(?,?,?)}");
			stmt.setInt(1,filmId);
			stmt.setInt(2,storeId);
			stmt.registerOutParameter(3, Types.INTEGER);
			rs= stmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getInt(1)); // rs.getInt("inventory_id")
			}
			count = stmt.getInt(3); // 프로시저 3번째 out변수 값
		} catch (SQLException e) {
			e.printStackTrace();
		}
		map.put("list", list);
		map.put("count", count);

		return map;
	}
	
	// 3) 검색기능구현 
	public List<Double> selectfilmPriceList() {
		// ArrayList
		List<Double> list = new ArrayList<Double>();
		
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// DBUtil 호출
		conn = DBUtil.getConnection();
		// SQL 쿼리 문자열 저장
		String sql = 
			"SELECT DISTINCT price FROM film_list ORDER BY price";
		
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getDouble("price"));
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

	// 메서드 단위 테스트
	public static void main(String[] args) {
		// FilmDao
		FilmDao filmDao = new FilmDao();
		// test 변수 값
		int filmId = 7;
		int storeId = 2;
		
		// filmInStock 
		Map<String,Object> map = filmDao.filmInStock(filmId, storeId);
		List<Integer> list = (List<Integer>) map.get("list");
		int count = (int) map.get("count");
		
		// 단위 테스트 결과
		System.out.println(filmId + "번 영화는 " + storeId +"번 가게에 "+ count + "개 남음.");
		
		for(int id : list) {
			System.out.println(id); // inventory_id
		}
	}
}