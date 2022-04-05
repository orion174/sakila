<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "vo.Customer" %>
<%@ page import = "dao.CustomerDao" %>
<%	
	// 페이징 코드
	int currentPage = 1;
	if(request.getParameter("currentPage") != null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	
	int rowPerPage = 15; // rowPerPage는 변경될 수 있음
	// 현재 페이지 currentPage 변경 -> beginRow로 
	int beginRow = (currentPage-1) * rowPerPage;
	
	// Customer
	Customer cus = new Customer();
	// CustomerDao
	CustomerDao dao = new CustomerDao();
	// SelectCustomerListByPage
	List<Customer> list = dao.selectCustomerListByPage(beginRow, rowPerPage);
		
	// 마지막 페이지 변수 값 저장 코드
	int lastPage = 0;
	int totalCount = dao.selectCustomerTotalRow();
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
<title>Insert title here</title>
</head>
<body>
	<a href="<%=request.getContextPath()%>/index.jsp">index</a>
	<h1>Customer List</h1>
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
				<th>notes</th>
				<th>SID</th>				
			</tr>
		</thead>
		<tbody>
			<%
				for(Customer c : list) {
			%>
				<tr>
					<td><%=c.getCustomerId()%></td>
					<td><%=c.getName()%></td>	
					<td><%=c.getAddress()%></td>
					<td><%=c.getPostalCode()%></td>
					<td><%=c.getPhone()%></td>
					<td><%=c.getCity()%></td>
					<td><%=c.getCountry()%></td>
					<td><%=c.getActive()%></td>
					<td><%=c.getStoreId()%></td>
			<%
				}
			%>
				</tr>
		</tbody>

	</table>
	<!-- 페이징 -->
	<div>
		<%
			if(currentPage > 1) {
		%>
				<a href="<%=request.getContextPath()%>/ViewTable/customerList.jsp?currentPage=<%=currentPage-1%>">이전</a>
		<%		
			}
		%>
		<%
			if(currentPage < lastPage) {
		%>
				<a href="<%=request.getContextPath()%>/ViewTable/customerList.jsp?currentPage=<%=currentPage+1%>">다음</a>
		<%
			}
		%>
	</div>
</body>
</html> 