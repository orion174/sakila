package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.SalesByFilmCategory;

public class SalesByFilmCategoryDao {
	
	public List<SalesByFilmCategory> selectSalesByFilmCategory(int beginRow, int rowPerPage) {
		List<SalesByFilmCategory> list = new ArrayList<SalesByFilmCategory>();
		
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// static으로 선언해서 ~
		// MariaDB 드라이버 로딩 메서드 호출
		conn = DBUtil.getConnection();
		String sql = 
			"SELECT category name, total_sales totalSales FROM sales_by_film_category ORDER BY total_sales DESC LIMIT ?,?";
	
		try {
			// SQL 쿼리 저장, 페이증 처리
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery();
			// 데이터 변환
			while(rs.next()) {
				SalesByFilmCategory sbfc = new SalesByFilmCategory(); // 다형성 
				sbfc.setName(rs.getString("name"));
				sbfc.setTotalSales(rs.getLong("totalSales"));
				list.add(sbfc);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// DB자원반환
				rs.close();
				stmt.close(); 
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/* 데이터의 총 수가 적은 테이블 -> 페이징 처리 구지 안햐두댐 
	 * 데이터 확장 가능성 염두해서 코드는 짬
	 */
	public int selectSalesByFilmCategoryListTotalRow() {
		int row = 0;
		Connection conn = null;
		PreparedStatement  stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		String sql = "SELECT COUNT(*) cnt FROM sales_by_film_category";
		try {
			stmt = conn.prepareStatement(sql);
			System.out.println("sql selectSalesByFilmCategoryTotalRow : " + stmt);	//디버깅
			rs = stmt.executeQuery();
			if(rs.next()) {
			row = rs.getInt("cnt");
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("예외발생");
		} finally {
			try {
				rs.close(); stmt.close(); conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return row;
	}
	
}
