package dao;

import java.sql.*;
import java.util.*;

import util.DBUtil;

/* Rental Table 검색기능 구현
 * 요구사항 : film search와 같은 고객이름 검색 + ( + ?번가게 선택 + ( 대여날짜 범위 선택 ) ) 
 */

// 1) rental Table에서 조건에 맞는 데이터 SELECT 하기 위한 코드
public class RentalDao {
	// 자료저장 Map 사용
	public List<Map<String, Object>> selectRentalSearchList(int beginRow, int rowPerPage, int storeId, String customerName, String beginDate, String endDate) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DBUtil  호출
		conn = DBUtil.getConnection();

		try {
			// rental table 검색 SQL 쿼리 문자열 저장
			String sql =   
					  "		SELECT"
					+ "		r.rental_id rentalId,"
					+ " 	CONCAT(c.first_name,' ',c.last_name) name,"
					+ " 	s.store_id storeId,"
					+ " 	i.film_id filmId,"
					+ " 	f.title title,"
					+ " 	r.rental_date rentalDate,"
					+ " 	r.return_date returnDate"
					+ "	FROM rental r INNER JOIN customer c"
					+ "	ON r.customer_id = c.customer_id"
					+ "	INNER JOIN staff s"
					+ "	ON r.staff_id = s.staff_id"
					+ "	INNER JOIN inventory i"
					+ "	ON r.inventory_id = i.inventory_id"
					+ "	INNER JOIN film f"
					+ "	ON i.film_id = f.film_id"
					+ "	WHERE CONCAT(c.first_name ,' ', c.last_name) LIKE ?";
			
			/* 검색기능 구현
			 * 고객 이름 
			 * 1) 기본 검색
			 * 1번째 경우 : 대여기간 설정(o) + store_id 설정(o) 
			 * 2번째 경우 : 대여기간 설정(o) + store_id 설정(x)
			 * 3번째 경우 : 대여기간 설정(x) + store_id 설정(o) 
			 * 4번째 경우 : 대여기간 설정(x) + store_id 설정(x)
			 * 2) 상세 검색
			 * 5번째 경우 : 대여 마지막 날짜만 설정  + store_id 설정(o) 
			 * 6번째 경우 : 대여 시작 날짜만 설정  + store_id 설정(o) 
			 * 7번째 경우 : 대여 마지막 날짜만 설정 + store_id 설정(x)
			 * 8번째 경우 : 대여 시작 날짜만 설정 + store_id 설정(x)
			 */
			
			// 1번째 경우 : 대여기간 설정(o) + store_id 설정(o) 
			if(!beginDate.equals("") && !endDate.equals("")) { 
				if(storeId != -1) { 
					sql += " AND s.store_id=? AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d') ORDER BY rental_id LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+customerName+"%");
					stmt.setInt(2, storeId);
					stmt.setString(3, beginDate);
					stmt.setString(4, endDate);
					stmt.setInt(5, beginRow);
					stmt.setInt(6, rowPerPage);
				}
			// 2번째 경우 : 대여기간 설정(o) + store_id 설정(x)
				else if(storeId == -1) { 
					sql += " AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d') ORDER BY rental_id LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+customerName+"%");
					stmt.setString(2, beginDate);
					stmt.setString(3, endDate);
					stmt.setInt(4, beginRow);
					stmt.setInt(5, rowPerPage);
				} 
			}
			// 3번째 경우 : 대여기간 설정(x) + store_id 설정(o) 
			else if(beginDate.equals("") && endDate.equals("")) { 
				if(storeId != -1) { 
					sql += " AND s.store_id=? ORDER BY rental_id LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+customerName+"%");
					stmt.setInt(2, storeId);
					stmt.setInt(3, beginRow);
					stmt.setInt(4, rowPerPage);
				}
			// 4번째 경우 : 대여기간 설정(x) + store_id 설정(x)
				else if(storeId == -1) { 
					sql += " ORDER BY rental_id LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+customerName+"%");
					stmt.setInt(2, beginRow);
					stmt.setInt(3, rowPerPage);
				}
			}
			// 5번째 경우 : 대여 마지막 날짜만 설정  + store_id 설정(o) 
			else if(storeId!=-1&&beginDate.equals("")&&!endDate.equals("")) {
				sql = sql + " AND s.store_id=? AND r.rental_date BETWEEN STR_TO_DATE('0000-01-01','%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d')  ORDER BY rental_id limit ?,?";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1, "%"+customerName+"%");
				stmt.setInt(2, storeId);
				stmt.setString(3,endDate);
				stmt.setInt(4, beginRow);
				stmt.setInt(5, rowPerPage);
			}
			// 6번째 경우 : 대여 시작 날짜만 설정  + store_id 설정(o) 
			else if(storeId!=-1&&!beginDate.equals("")&&endDate.equals("")) {
				sql = sql + " AND s.store_id=? AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND NOW()  ORDER BY rental_id limit ?,?";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1, "%"+customerName+"%");
				stmt.setInt(2, storeId);
				stmt.setString(3, beginDate);
				stmt.setInt(4, beginRow);
				stmt.setInt(5, rowPerPage);
			}
			// 7번째 경우 : 대여 마지막 날짜만 설정 + store_id 설정(x)
			else if(storeId==-1&&beginDate.equals("")&&!endDate.equals("")) { 
				sql = sql + " AND r.rental_date BETWEEN STR_TO_DATE('0000-01-01','%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d')  ORDER BY rental_id limit ?,?";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1, "%"+customerName+"%");
				stmt.setString(2, endDate);
				stmt.setInt(3, beginRow);
				stmt.setInt(4, rowPerPage);
			}
			// 8번째 경우 : 대여 시작 날짜만 설정 + store_id 설정(x)
			else if(storeId==-1&&!beginDate.equals("")&&endDate.equals("")) {
				sql = sql + " AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND NOW()  ORDER BY rental_id limit ?,?";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1, "%"+customerName+"%");
				stmt.setString(2, beginDate);
				stmt.setInt(3, beginRow);
				stmt.setInt(4, rowPerPage);
			}
			
			// SQL 쿼리 저장
			rs = stmt.executeQuery(); 
			// 데이터 변환 Map 사용
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("rentalId", rs.getInt("rentalId"));
				map.put("name", rs.getString("name"));
				map.put("storeId", rs.getInt("storeId"));
				map.put("filmId", rs.getInt("filmId"));
				map.put("title", rs.getString("title"));
				map.put("rentalDate", rs.getString("rentalDate"));
				map.put("returnDate", rs.getString("returnDate"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 자원 반납
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
	
	// 2) 페이징 처리를 위한 TotalCount 구하는 코드
	public int selectRentalSearchTotalRow(int storeId, String customerName, String beginDate, String endDate) {
		// Action에서 넘겨줄 row 선언 후 초기화
		int row = 0;
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DBUtil 호출
		conn = DBUtil.getConnection(); // DB접속

		try {
			// 조인 테이블의 총 데이터 수 집계 SQL 쿼리 문자열 저장
			String sql = 
					  "		SELECT COUNT(*) cnt"
					+ "	FROM rental r INNER JOIN customer c"
					+ "	ON r.customer_id = c.customer_id"
					+ " INNER JOIN staff s"
					+ "	ON r.staff_id = s.staff_id"
					+ "	INNER JOIN inventory i"
					+ "	ON r.inventory_id = i.inventory_id"
					+ "	INNER JOIN film f"
					+ "	ON i.film_id = f.film_id"
					+ "	WHERE CONCAT(c.first_name,' ',c.last_name) LIKE ?";
			
			// 1번째 경우 : 대여기간 설정(o) + store_id 설정(o) TotalCount
			if(!beginDate.equals("") && !endDate.equals("")) { 
				if(storeId != -1) { 
					sql += " AND s.store_id=? AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d') ";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+customerName+"%");
					stmt.setInt(2, storeId);
					stmt.setString(3, beginDate);
					stmt.setString(4, endDate);

				}
			// 2번째 경우 : 대여기간 설정(o) + store_id 설정(x) TotalCount
				else if(storeId == -1) { 
					sql += " AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d') ";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+customerName+"%");
					stmt.setString(2, beginDate);
					stmt.setString(3, endDate);
				} 
			}
			// 3번째 경우 : 대여기간 설정(x) + store_id 설정(o) TotalCount
			else if(beginDate.equals("") && endDate.equals("")) { 
				if(storeId != -1) { 
					sql += " AND s.store_id=? ";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+customerName+"%");
					stmt.setInt(2, storeId);
				}
			// 4번째 경우 : 대여기간 설정(x) + store_id 설정(x) TotalCount
				else if(storeId == -1) { 
					sql += " ORDER BY rental_id LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+customerName+"%");
				}
			}
			// 5번째 경우 : 대여 마지막 날짜만 설정  + store_id 설정(o) TotalCount
			else if(storeId!=-1&&beginDate.equals("")&&!endDate.equals("")) {
				sql = sql + " AND s.store_id=? AND r.rental_date BETWEEN STR_TO_DATE('0000-01-01','%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d') ";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1, "%"+customerName+"%");
				stmt.setInt(2, storeId);
				stmt.setString(3,endDate);
			}
			// 6번째 경우 : 대여 시작 날짜만 설정  + store_id 설정(o) TotalCount
			else if(storeId!=-1&&!beginDate.equals("")&&endDate.equals("")) {
				sql = sql + " AND s.store_id=? AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND NOW()  ";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1, "%"+customerName+"%");
				stmt.setInt(2, storeId);
				stmt.setString(3, beginDate);
			}
			// 7번째 경우 : 대여 마지막 날짜만 설정 + store_id 설정(x) TotalCount
			else if(storeId==-1&&beginDate.equals("")&&!endDate.equals("")) { 
				sql = sql + " AND r.rental_date BETWEEN STR_TO_DATE('0000-01-01','%Y-%m-%d') AND STR_TO_DATE(?,'%Y-%m-%d') ";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1, "%"+customerName+"%");
				stmt.setString(2, endDate);
			}
			// 8번째 경우 : 대여 시작 날짜만 설정 + store_id 설정(x) TotalCount
			else if(storeId==-1&&!beginDate.equals("")&&endDate.equals("")) {
				sql = sql + " AND r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d') AND NOW() ";
				stmt= conn.prepareStatement(sql);
				stmt.setString(1, "%"+customerName+"%");
				stmt.setString(2, beginDate);
			}
			
			// SQL 쿼리 저장
			rs = stmt.executeQuery(); 
			// 데이터 변환
			while(rs.next()) {
				row = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 자원 반납
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return row;
	}
} 