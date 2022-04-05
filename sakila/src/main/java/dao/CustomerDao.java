package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.Customer;

public class CustomerDao {
	
	public List<Customer> selectCustomerListByPage(int beginRow, int rowPerPage) {
		List<Customer> list = new ArrayList<Customer>();
		
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// static으로 선언해서 ~
		// MariaDB 드라이버 로딩 메서드 호출
		conn = DBUtil.getConnection();
		String sql = "SELECT ID customerId, name name, address address, 'zip code' postalCode, phone phone, city city, country country, notes active, SID storeId FROM customer_list ORDER BY ID limit ?,?";
	    // !! 데이터베이스 컬럼명안에 띄어쓰기 있으면 '내용1 내용2' 이렇게 처리해야함... 구글링..
		try {
			// SQL 쿼리 저장, 페이증 처리
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery();
			// 데이터 변환
			while(rs.next()) {
				Customer cus = new Customer(); // 다형성 
				cus.setCustomerId(rs.getInt("customerId"));
				cus.setName(rs.getString("name"));
				cus.setAddress(rs.getString("address"));
				cus.setPostalCode(rs.getString("postalCode"));
				cus.setPhone(rs.getString("phone"));
				cus.setCity(rs.getString("city"));
				cus.setCountry(rs.getString("country"));
				cus.setActive(rs.getString("active"));
				cus.setStoreId(rs.getString("storeId"));
				list.add(cus);
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
	
	public int selectCustomerTotalRow() throws Exception {
		int row = 0; // 함수 결과값(쩡수) 반환해줄 변수 선언 후 초기화
		
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// static으로 선언해서 ~
		conn = DBUtil.getConnection();
		String sql = "SELECT COUNT(*) cnt FROM customer_list";
		
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()) {
				row = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				//DB자원반납
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return row;
	}
	
}
