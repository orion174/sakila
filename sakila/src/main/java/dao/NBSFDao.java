package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.NBSF;

// Film List View 테이블이랑 actors 칼럼 데이터값의 영대소문자 구분만 다른 View 테이블
public class NBSFDao {

	public List<NBSF> selectNBSFListByPage(int beginRow, int rowPerPage) {
		List<NBSF> list = new ArrayList<NBSF>();
		
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// static으로 선언해서 ~
		// MariaDB 드라이버 로딩 메서드 호출
		conn = DBUtil.getConnection();
		String sql = 
			"SELECT FID filmId, title title, description description, category name, price rentalRate, length length, rating rating, actors actor FROM nicer_but_slower_film_list ORDER BY FID limit ?,?";
	
		try {
			// SQL 쿼리 저장, 페이증 처리
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs = stmt.executeQuery();
			// 데이터 변환
			while(rs.next()) {
				NBSF f = new NBSF(); // 다형성 
				f.setFilmId(rs.getInt("filmId"));
				f.setTitle(rs.getString("title"));
				f.setDescription(rs.getString("description"));
				f.setName(rs.getString("name"));
				f.setRentalRate(rs.getInt("rentalRate"));
				f.setLength(rs.getInt("length"));
				f.setRating(rs.getString("rating"));
				f.setActor(rs.getString("actor"));
				list.add(f);
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
	
	public int selectNBSFTotalRow() throws Exception {
		int row = 0; // 함수 결과값(쩡수) 반환해줄 변수 선언 후 초기화
		
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// static으로 선언해서 ~
		conn = DBUtil.getConnection();
		String sql = "SELECT COUNT(*) cnt FROM nicer_but_slower_film_list";
		
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
