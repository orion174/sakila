package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.SalesByStore;

public class SalesByStoreDao {
	
	public List<SalesByStore> selectSalesByStore(int beginRow, int rowPerPage) {
		List<SalesByStore> list = new ArrayList<SalesByStore>();
		
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// static으로 선언해서 ~
		// MariaDB 드라이버 로딩 메서드 호출
		conn = DBUtil.getConnection();
		String sql = 
			"SELECT store store, manager manager, total_sales totalSales FROM sales_by_store ORDER BY store LIMIT ?,?";
	
		try {
			// SQL 쿼리 저장, 페이증 처리
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery();
			// 데이터 변환
			while(rs.next()) {
				SalesByStore sbs = new SalesByStore(); // 다형성 
				sbs.setStore(rs.getString("store"));
				sbs.setManager(rs.getString("manager"));
				sbs.setTotalSales(rs.getLong("totalSales"));
				list.add(sbs);
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
	public int selectSalesByStoreTotalRow() {
		int row = 0;
		Connection conn = null;
		PreparedStatement  stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		String sql = "SELECT COUNT(*) cnt FROM sales_by_store";
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()) {
			row = rs.getInt("cnt");
			}
		} catch(SQLException e) {
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
		return row;
	}
	
}