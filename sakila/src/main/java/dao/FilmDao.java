package dao;

import java.util.*;
import java.sql.*;

import util.DBUtil;
import vo.FilmList;

/* film 테이블 FilmDao
 * 프로시져 film_in_stock + film_not_in_stock 구현
 * film 테이블 검색기능 SELECT film price 구현 
 * film 테이블 검색기능 SELECT film FilmListSearch 구현 
 * film 테이블 검색기능 페이징 추가
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
	
	/* 4) 검색기능구현
	* (제목 + 배우) <-- 기본
	* 1가지만 선택 : + price(대여료) / + length(시간) / + catergory(장르) / + rating(등급) -- 총 4가지
	* 
	* 2가지 선택 : + price(대여료) + length(시간) / + price(대여료) + catergory(장르) / + price(대여료) + rating(등급) /
	* 			+ length(시간) + catergory(장르) / + length(시간) + rating(등급) / + catergory(장르) + rating(등급) -- 총 6가지
	* 
	* 3가지 선택 : + price(대여료) + length(시간) + catergory(장르) / + price(대여료) + length(시간) + rating(등급) /
	* 			+ catergory(장르) + rating(등급) + price(대여료) / catergory(장르) + rating(등급) + length(시간) -- 총 4가지
	* 
	* 모두 선택 : + price(대여료) + length(시간) + catergory(장르) + rating(등급) -- 총 1가지
	*/ 
	public List<FilmList> selectFilmListSearch(int beginRow, int rowPerPage, String category, String rating, double price, int length, String title, String actor) {		
		// List에 저장
		List<FilmList> list = new ArrayList<FilmList>();
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DBUtill 호출
		conn = DBUtil.getConnection();
		try {
			// SQL 쿼리 (영화제목, 배우이름 검색 기본 세팅)
			String sql = "SELECT fid,title,description,category,price,length,rating,actors FROM film_list WHERE title LIKE ? AND actors LIKE ?";
			
			// (제목 + 배우) <-- 기본
			if(category.equals("") && rating.equals("") && price==-1 && length==-1) {
				sql += " ORDER BY fid LIMIT ?, ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
				stmt.setInt(3, beginRow);
				stmt.setInt(4, rowPerPage);
			} 
			
			 // 1가지만 선택 : + price(대여료) / + length(시간) / + catergory(장르) / + rating(등급) -- 총 4가지
			
			 // 1) + length(시간)
			 else if(category.equals("") && rating.equals("") && price==-1 && length!=-1) { 
				if(length == 0) {
					sql += " AND length<60 ORDER BY fid LIMIT ?, ?";
				} else if(length == 1) {
					sql += " AND length>=60 ORDER BY fid LIMIT ?, ?";
				}
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
				stmt.setInt(3, beginRow);
				stmt.setInt(4, rowPerPage);
			} 
			
			 // 2) + price(대여료) 
			 else if(category.equals("") && rating.equals("") && price!=-1 && length==-1) {
				sql += " AND price=? ORDER BY fid LIMIT ?, ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
				stmt.setDouble(3, price);
				stmt.setInt(4, beginRow);
				stmt.setInt(5, rowPerPage);
			} 
			
			 // 3) + catergory(장르)
			 else if(!category.equals("") && rating.equals("") && price==-1 && length==-1) { 
					sql += " AND category=? ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, category);
					stmt.setInt(4, beginRow);
					stmt.setInt(5, rowPerPage);
			} 
			
			 // 4) + rating(등급)
			 else if(category.equals("") && !rating.equals("") && price==-1 && length==-1) { 
					sql += " AND rating=? ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, rating);
					stmt.setInt(4, beginRow);
					stmt.setInt(5, rowPerPage);
			}
			
			/*2가지 선택 : + price(대여료) + length(시간) / + price(대여료) + catergory(장르) / + price(대여료) + rating(등급) /
			 *		  + length(시간) + catergory(장르) / + length(시간) + rating(등급) / + catergory(장르) + rating(등급) -- 총 6가지
			*/
			
			// 1) + price(대여료) + length(시간)
			 else if(category.equals("") && rating.equals("") && price!=-1 && length!=-1) {
					if(length == 0) {
						sql += " AND price=? AND length<60 ORDER BY fid LIMIT ?, ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setDouble(3, price);
						stmt.setInt(4, beginRow);
						stmt.setInt(5, rowPerPage);
					} else if(length == 1) {
						sql += " AND price=? AND length>=60 ORDER BY fid LIMIT ?, ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setDouble(3, price);
						stmt.setInt(4, beginRow);
						stmt.setInt(5, rowPerPage);
					}
			}
			
			 // 2) + price(대여료) + catergory(장르)
			 else if(!category.equals("") && rating.equals("") && price!=-1 && length==-1) { 
					sql += " AND category=? AND price=? ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, category);
					stmt.setDouble(4, price);
					stmt.setInt(5, beginRow);
					stmt.setInt(6, rowPerPage);
			}
	
			// 3) + price(대여료) + rating(등급)
			else if(category.equals("") && !rating.equals("") && price!=-1 && length==-1) { 
					    sql += " AND rating=? AND price=? ORDER BY fid LIMIT ?, ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setString(3, rating);
						stmt.setDouble(4, price);
						stmt.setInt(5, beginRow);
						stmt.setInt(6, rowPerPage);
			}
					
			// 4) + length(시간) + catergory(장르)
			 else if(!category.equals("") && rating.equals("") && price==-1 && length!=-1) { 
					if(length == 0) {
						sql += " AND category=? AND length<60 ORDER BY fid LIMIT ?, ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setString(3, category);
						stmt.setInt(4, beginRow);
						stmt.setInt(5, rowPerPage);
					} else if(length == 1) {
						sql += " AND category=? AND length>=60 ORDER BY fid LIMIT ?, ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setString(3, category);
						stmt.setInt(4, beginRow);
						stmt.setInt(5, rowPerPage);
					}	
			} 
			
			// 5) + length(시간) + rating(등급)
			 else if(category.equals("") && !rating.equals("") && price==-1 && length!=-1) {
					if(length == 0) {
						sql += " AND rating=? AND length<60 ORDER BY fid LIMIT ?, ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setString(3, rating);
						stmt.setInt(4, beginRow);
						stmt.setInt(5, rowPerPage);
					} else if(length == 1) {
						sql += " AND rating=? AND length>=60 ORDER BY fid LIMIT ?, ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setString(3, rating);
						stmt.setInt(4, beginRow);
						stmt.setInt(5, rowPerPage);
					}
			 }
					
			// 6) + catergory(장르) + rating(등급)
			else if(!category.equals("") && !rating.equals("") && price==-1 && length==-1) {
					sql += " AND category=? AND rating=? ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, category);
					stmt.setString(4, rating);
					stmt.setInt(5, beginRow);
					stmt.setInt(6, rowPerPage);
			} 
			
			/* 3가지 선택 : + price(대여료) + length(시간) + catergory(장르) / + price(대여료) + length(시간) + rating(등급) /
			 * 			+ catergory(장르) + rating(등급) + price(대여료) / catergory(장르) + rating(등급) + length(시간) -- 총 4가지
			 */
			
			// 1) + price(대여료) + length(시간) + catergory(장르)
			else if(!category.equals("") && rating.equals("") && price!=-1 && length!=-1) { 
				if(length == 0) {
					sql += " AND category=? AND price=? AND length<60 ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, category);
					stmt.setDouble(4, price);
					stmt.setInt(5, beginRow);
					stmt.setInt(6, rowPerPage);
				} else if(length == 1) {
					sql += " AND category=? AND price=? AND length>=60 ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, category);
					stmt.setDouble(4, price);
					stmt.setInt(5, beginRow);
					stmt.setInt(6, rowPerPage);
				}
			}
			
			// 2)  + price(대여료) + length(시간) + rating(등급)
			else if(category.equals("") && !rating.equals("") && price!=-1 && length!=-1) { 
				if(length == 0) {
					sql += " AND rating=? AND price=? AND length<60 ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, rating);
					stmt.setDouble(4, price);
					stmt.setInt(5, beginRow);
					stmt.setInt(6, rowPerPage);
				} else if(length == 1) {
					sql += " AND rating=? AND price=? AND length>=60 ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, rating);
					stmt.setDouble(4, price);
					stmt.setInt(5, beginRow);
					stmt.setInt(6, rowPerPage);
				}
			}
			// 3) + catergory(장르) + rating(등급) + price(대여료)
			else if(!category.equals("") && !rating.equals("") && price!=-1 && length==-1) { 
				sql += " AND category=? AND rating=? AND price=? ORDER BY fid LIMIT ?, ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
				stmt.setString(3, category);
				stmt.setString(4, rating);
				stmt.setDouble(5, price);
				stmt.setInt(6, beginRow);
				stmt.setInt(7, rowPerPage);
			}
			
			// 4) catergory(장르) + rating(등급) + length(시간)
			 else if(!category.equals("") && !rating.equals("") && price==-1 && length!=-1) { // price 선택 X
					if(length == 0) {
						sql += " AND category=? AND rating=? AND length<60 ORDER BY fid LIMIT ?, ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setString(3, category);
						stmt.setString(4, rating);
						stmt.setInt(5, beginRow);
						stmt.setInt(6, rowPerPage);
					} else if(length == 1) {
						sql += " AND category=? AND rating=? AND length>=60 ORDER BY fid LIMIT ?, ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setString(3, category);
						stmt.setString(4, rating);
						stmt.setInt(5, beginRow);
						stmt.setInt(6, rowPerPage);
					}
				} 
			
			// 모두 선택 : + price(대여료) + length(시간) + catergory(장르) + rating(등급) -- 총 1가지
			 else {
					if(length == 0) {
						sql += " AND category=? AND rating=? AND price=? AND length<60 ORDER BY fid LIMIT ?, ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setString(3, category);
						stmt.setString(4, rating);
						stmt.setDouble(5, price);
						stmt.setInt(6, beginRow);
						stmt.setInt(7, rowPerPage);
					} else if(length == 1) {
						sql += " AND category=? AND rating=? AND price=? AND length>=60 ORDER BY fid LIMIT ?, ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setString(3, category);
						stmt.setString(4, rating);
						stmt.setDouble(5, price);
						stmt.setInt(6, beginRow);
						stmt.setInt(7, rowPerPage);
					}
				}
			
			rs = stmt.executeQuery();
			
			// 데이터변환
			while(rs.next()) {
				FilmList f = new FilmList();
				f.setFid(rs.getInt("fid"));
				f.setTitle(rs.getString("title"));
				f.setDescription(rs.getString("description"));
				f.setCategory(rs.getString("category"));
				f.setPrice(rs.getDouble("price"));
				f.setLength(rs.getInt("length"));
				f.setRating(rs.getString("rating"));
				f.setActors(rs.getString("actors"));
				list.add(f);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// Film Search 페이징 코드 
	// List페이지의 totalCount 
	public int selectFilmSearchTotalRow (String category, String rating, double price, int length, String title, String actor) {
		int row = 0; // 결과값(정수) 반환해줄 변수 선언 후 초기화
		
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// DBUtil 호출
		conn = DBUtil.getConnection();
		
		// 
		try {
			// SQL 쿼리 (영화제목, 배우이름 검색 기본 세팅)
			String sql = 
			 "SELECT count(*) cnt FROM film_list where title LIKE ? AND actors LIKE ?";
			
			// (제목 + 배우) totalCount
			if(category.equals("") && rating.equals("") && price==-1 && length==-1) {
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
			} 
			
			 // 1가지만 선택 : + price(대여료) / + length(시간) / + catergory(장르) / + rating(등급) -- 총 4가지
			
			 // 1) + length(시간) totalCount
			 else if(category.equals("") && rating.equals("") && price==-1 && length!=-1) { 
				if(length == 0) {
					sql += " AND length<60";
				} else if(length == 1) {
					sql += " AND length>=60";
				}
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
			} 
			
			 // 2) + price(대여료) totalCount
			 else if(category.equals("") && rating.equals("") && price!=-1 && length==-1) {
				sql += " AND price=?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
				stmt.setDouble(3, price);
			} 
			 
			 // 3) + catergory(장르) totalCount
			 else if(!category.equals("") && rating.equals("") && price==-1 && length==-1) { 
					sql += " AND category=?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, category);
			} 
			
			 // 4) + rating(등급) totalCount
			 else if(category.equals("") && !rating.equals("") && price==-1 && length==-1) { 
					sql += " AND rating=?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, rating);
			}
			
			/*2가지 선택 : + price(대여료) + length(시간) / + price(대여료) + catergory(장르) / + price(대여료) + rating(등급) /
			 *		  + length(시간) + catergory(장르) / + length(시간) + rating(등급) / + catergory(장르) + rating(등급) -- 총 6가지
			*/
			
			// 1) + price(대여료) + length(시간) totalCount
			 else if(category.equals("") && rating.equals("") && price!=-1 && length!=-1) {
					if(length == 0) {
						sql += " AND price=? AND length<60";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setDouble(3, price);
					} else if(length == 1) {
						sql += " AND price=? AND length>=60";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setDouble(3, price);
					}
			}
			
			 // 2) + price(대여료) + catergory(장르) totalCount
			 else if(!category.equals("") && rating.equals("") && price!=-1 && length==-1) { 
					sql += " AND category=? AND price=?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, category);
					stmt.setDouble(4, price);
			}
	
			// 3) + price(대여료) + rating(등급) totalCount
			else if(category.equals("") && !rating.equals("") && price!=-1 && length==-1) { 
					    sql += " AND rating=? AND price=?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setString(3, rating);
						stmt.setDouble(4, price);
			}
					
			// 4) + length(시간) + catergory(장르) totalCount
			 else if(!category.equals("") && rating.equals("") && price==-1 && length!=-1) { 
					if(length == 0) {
						sql += " AND category=? AND length<60";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setString(3, category);
					} else if(length == 1) {
						sql += " AND category=? AND length>=60";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setString(3, category);
					}	
			} 
			
			// 5) + length(시간) + rating(등급) totalCount
			 else if(category.equals("") && !rating.equals("") && price==-1 && length!=-1) {
					if(length == 0) {
						sql += " AND rating=? AND length<60";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setString(3, rating);
					} else if(length == 1) {
						sql += " AND rating=? AND length>=60";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setString(3, rating);
					}
			 }
					
			// 6) + catergory(장르) + rating(등급) totalCount
			else if(!category.equals("") && !rating.equals("") && price==-1 && length==-1) {
					sql += " AND category=? AND rating=?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, category);
					stmt.setString(4, rating);
			} 
			
			/* 3가지 선택 : + price(대여료) + length(시간) + catergory(장르) / + price(대여료) + length(시간) + rating(등급) /
			 * 			+ catergory(장르) + rating(등급) + price(대여료) / catergory(장르) + rating(등급) + length(시간) -- 총 4가지
			 */
			
			// 1) + price(대여료) + length(시간) + catergory(장르) totalCount
			else if(!category.equals("") && rating.equals("") && price!=-1 && length!=-1) { 
				if(length == 0) {
					sql += " AND category=? AND price=? AND length<60";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, category);
					stmt.setDouble(4, price);
				} else if(length == 1) {
					sql += " AND category=? AND price=? AND length>=60";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, category);
					stmt.setDouble(4, price);
				}
			}
			
			// 2)  + price(대여료) + length(시간) + rating(등급) totalCount
			else if(category.equals("") && !rating.equals("") && price!=-1 && length!=-1) { 
				if(length == 0) {
					sql += " AND rating=? AND price=? AND length<60";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, rating);
					stmt.setDouble(4, price);
				} else if(length == 1) {
					sql += " AND rating=? AND price=? AND length>=60";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+title+"%");
					stmt.setString(2, "%"+actor+"%");
					stmt.setString(3, rating);
					stmt.setDouble(4, price);
				}
			}
			// 3) + catergory(장르) + rating(등급) + price(대여료) totalCount
			else if(!category.equals("") && !rating.equals("") && price!=-1 && length==-1) { 
				sql += " AND category=? AND rating=? AND price=?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
				stmt.setString(3, category);
				stmt.setString(4, rating);
				stmt.setDouble(5, price);
			}
			
			// 4) catergory(장르) + rating(등급) + length(시간) totalCount
			 else if(!category.equals("") && !rating.equals("") && price==-1 && length!=-1) { 
					if(length == 0) {
						sql += " AND category=? AND rating=? AND length<60";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setString(3, category);
						stmt.setString(4, rating);
					} else if(length == 1) {
						sql += " AND category=? AND rating=? AND length>=60";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setString(3, category);
						stmt.setString(4, rating);
					}
				} 
			
			// 모두 선택 : + price(대여료) + length(시간) + catergory(장르) + rating(등급)  totalCount
			 else {
					if(length == 0) {
						sql += " AND category=? AND rating=? AND price=? AND length<60";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setString(3, category);
						stmt.setString(4, rating);
						stmt.setDouble(5, price);
					} else if(length == 1) {
						sql += " AND category=? AND rating=? AND price=? AND length>=60";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setString(3, category);
						stmt.setString(4, rating);
						stmt.setDouble(5, price);
					}
		}
			
			rs = stmt.executeQuery();
		
			if(rs.next()) {
				 row = rs.getInt("cnt");
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
		return row; // 반환값
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