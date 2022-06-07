<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*"%>
<%@ page import = "dao.StaffListViewDao"%>
<%@ page import = "vo.StaffListView" %>
<%	
	// 페이징 코드
	int currentPage = 1;
	if(request.getParameter("currentPage") != null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	
	int rowPerPage = 15; // rowPerPage는 변경될 수 있음
	// 현재 페이지 currentPage 변경 -> beginRow로 
	int beginRow = (currentPage-1) * rowPerPage;
	
	// StaffListView
	StaffListView slv = new StaffListView();
	// StaffListViewyDao 
	StaffListViewDao dao = new StaffListViewDao();
	// selectStaffListView
	List<StaffListView> list = dao.selectStaffListView(beginRow, rowPerPage);
	
	// 마지막 페이지 변수 값 저장 코드
	int lastPage = 0;
	int totalCount = dao.selectStaffListViewTotalRow();
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
<title>Staff List View</title>
</head>
<body>
	<a href="<%=request.getContextPath()%>/index.jsp">index</a>
	<h1>Staff List View</h1>
	<table border="1">
		<thead>
			<tr>
				<th>ID</th>
				<th>name</th>
				<th>address</th>
				<th>zip code</th>
				<th>phone</th>
				<th>city</th>
				<th>country</th>
				<th>SID</th>
			</tr>
		</thead>
		<tbody>
			<%
				for(StaffListView s : list) {
			%>
					<tr>
						<td><%=s.getStaffId()%></td>
						<td><%=s.getName()%></td>
						<td><%=s.getAddress()%></td>
						<td><%=s.getPostalCode()%></td>
						<td><%=s.getPhone()%></td>
						<td><%=s.getCity()%></td>
						<td><%=s.getCountry()%></td>
						<td><%=s.getStoreId()%></td>
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