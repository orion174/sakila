package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.StaffListView;

public class StaffListViewDao {
	
	public List<StaffListView> selectStaffListView(int beginRow, int rowPerPage) {
		List<StaffListView> list = new ArrayList<StaffListView>();
		
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// static으로 선언해서 ~
		// MariaDB 드라이버 로딩 메서드 호출
		conn = DBUtil.getConnection();
		String sql = 
			"SELECT ID staffId, name name, address address, 'zip code' postalCode, phone phone, city city, country country, SID storeId FROM staff_list LIMIT ?,?";
	
		try {
			// SQL 쿼리 저장, 페이증 처리
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery();
			// 데이터 변환
			while(rs.next()) {
				StaffListView slv = new StaffListView(); // 다형성 
				slv.setStaffId(rs.getInt("staffId"));
				slv.setName(rs.getString("name"));
				slv.setAddress(rs.getString("address"));
				slv.setPostalCode(rs.getString("postalCode"));
				slv.setPhone(rs.getString("phone"));
				slv.setCity(rs.getString("city"));
				slv.setCountry(rs.getString("country"));
				slv.setStoreId(rs.getInt("storeId"));
				list.add(slv);
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
	public int selectStaffListViewTotalRow() {
		int row = 0;
		Connection conn = null;
		PreparedStatement  stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		String sql = "SELECT COUNT(*) cnt FROM staff_list";
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