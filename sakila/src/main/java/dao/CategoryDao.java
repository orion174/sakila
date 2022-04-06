package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import vo.Category;

/* Table - category
 * vo 사용
 */
public class CategoryDao {
	
   public List<Category> selectCategoryList() {
	  // vo 사용 -> list
      List<Category> list = new ArrayList<Category>();
      
      // DB 초기화
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      
      // DBUtil 호출
      conn = DBUtil.getConnection();
      // SQL 쿼리 문자열 저장
      String sql = 
    	"SELECT category_id categoryId, name, last_update lastUpdate FROM category";
      
      try {
         stmt = conn.prepareStatement(sql);
         rs = stmt.executeQuery();
         // 데이터 변환
         while(rs.next()) {
            Category c = new Category();
            c.setCategoryId(rs.getInt("categoryId"));
            c.setName(rs.getString("name"));
            c.setLastUpdate(rs.getString("lastUpdate"));
            list.add(c);
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
}