<%@page import="dao.RentalDao"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// 페이징 코드
	int currentPage = 1; 
	// 요청값 받는 코드
	if(request.getParameter("currentPage") != null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	int rowPerPage = 10; // rowPerPage는 변경될 수 있음
	// 현재 페이지 currentPage 변경 -> beginRow로 
	int beginRow = (currentPage-1)*rowPerPage; 
	
	// rentalSearchForm 요청값
	// 1) storeId ( -1 : 선택안된 기본값)
	int storeId = -1; 
	if(!request.getParameter("storeId").equals("")) {
		storeId = Integer.parseInt(request.getParameter("storeId"));
		System.out.println("storeId : " + storeId);
	}
	// 2) customerName, beginDate, endDate
	String customerName = request.getParameter("customerName");
	System.out.println("customerName : " + customerName);
	String beginDate = request.getParameter("beginDate");
	System.out.println("beginDate : " + beginDate);
	String endDate = request.getParameter("endDate");
	System.out.println("endDate : " + endDate);

	// RentalDao 
	RentalDao rentalDao = new RentalDao();
	// 데이터 저장 Map 사용
	List<Map<String, Object>> list = rentalDao.selectRentalSearchList(beginRow, rowPerPage, storeId, customerName, beginDate, endDate);
	
	// 마지막 페이지 변수 값 저장 코드
	int lastPage = 0;
	int totalCount = rentalDao.selectRentalSearchTotalRow(storeId, customerName, beginDate, endDate); 
	
	lastPage = totalCount / rowPerPage;
	if(totalCount % rowPerPage != 0) {
		lastPage++;
	}
	lastPage = (int)(Math.ceil((double)totalCount / (double)rowPerPage)); 
	// 4.0 / 2.0 = 2.0 -> 2.0
	// 5.0 / 2.0 = 2.5 -> 3.0
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Rental Search Action</title>
</head>
<body>
	
		<h1>Rental Search Action</h1>
		<table border="1">
			<thead>
				<tr>
					<th>rental_id</th>
					<th>name</th>
					<th>store_id</th>
					<th>film_id</th>
					<th>title</th>
					<th>rental_date</th>
					<th>return_date</th>
				</tr>
			</thead>
			<tbody>
					<%
						for(Map m : list) {
					%>
							<tr>
								<td><%=m.get("rentalId")%></td>
								<td><%=m.get("name")%></td>
								<td><%=m.get("storeId")%></td>
								<td><%=m.get("filmId")%></td>
								<td><%=m.get("title")%></td>
								<td><%=m.get("rentalDate")%></td>
								<td><%=m.get("returnDate")%></td>
							</tr>
					<%		
						}
					%>
			</tbody>
		</table>
			<!-- 페이징 -->
			<div>
					<%
						if(currentPage > 1) {
					%>
							<a href="<%=request.getContextPath()%>/rentalSearchAction.jsp?currentPage=<%=currentPage-1%>&storeId=<%=storeId%>&customerName=<%=customerName%>&beginDate=<%=beginDate%>&endDate=<%=endDate%>">이전</a>
					<%	
						}
						if(currentPage < lastPage) {
					%>
							<a href="<%=request.getContextPath()%>/rentalSearchAction.jsp?currentPage=<%=currentPage+1%>&storeId=<%=storeId%>&customerName=<%=customerName%>&beginDate=<%=beginDate%>&endDate=<%=endDate%>">다음</a>
					<%		
						}
					%>
			</div>
		
</body>
</html> 