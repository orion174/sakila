<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*"%>
<%@ page import = "dao.SalesByFilmCategoryDao"%>
<%@ page import = "vo.SalesByFilmCategory" %>
<%	
	// 페이징 코드
	int currentPage = 1;
	if(request.getParameter("currentPage") != null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	
	int rowPerPage = 15; // rowPerPage는 변경될 수 있음
	// 현재 페이지 currentPage 변경 -> beginRow로 
	int beginRow = (currentPage-1) * rowPerPage;
	
	// SalesByFilmCategory
	SalesByFilmCategory sbfc = new SalesByFilmCategory();
	// SalesByFilmCategoryDao 
	SalesByFilmCategoryDao dao = new SalesByFilmCategoryDao();
	// selectSalesByFilmCategory
	List<SalesByFilmCategory> list = dao.selectSalesByFilmCategory(beginRow, rowPerPage);
	
	// 마지막 페이지 변수 값 저장 코드
	int lastPage = 0;
	// NBSFDao 클래스 호출
	int totalCount = dao.selectSalesByFilmCategoryListTotalRow();
	// 마지막 페이지는 (전체 데이터 수 / 화면당 보여지는 데이터 수) 가 됨
	/*
	lastPage = totalCount / rowPerPage;
	if(totalCount % rowPerPage != 0) {
		lastPage++;
	}
	*/
	lastPage = (int)(Math.ceil((double)totalCount / (double)rowPerPage)); 
	// 4.0 / 2.0 = 2.0 -> 2.0
	// 5.0 / 2.0 = 2.5 -> 3.0
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sales By Film Category</title>
</head>
<body>
	<a href="<%=request.getContextPath()%>/index.jsp">index</a>
	<h1>Sales By Film Category</h1>
	<table border="1">
		<thead>
			<tr>
				<th>category</th>
				<th>total_sales</th>
			</tr>
		</thead>
		<tbody>
			<%
				for(SalesByFilmCategory s : list) {
			%>
					<tr>
						<td><%=s.getName()%></td>
						<td><%=s.getTotalSales()%></td>	
					</tr>
			<%
				}
			%>
		</tbody>
	</table>
	<!-- 페이징 -->
	<!-- 테이블 데이터 총 수가 적으므로 미구현 -->
</body>
</html> 