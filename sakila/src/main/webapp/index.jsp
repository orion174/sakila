<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Table</h1>
	<ol>
		<li><a href="<%=request.getContextPath()%>/Table/storeList.jsp">Store List</a></li>
		<li><a href="<%=request.getContextPath()%>/Table/staffList.jsp">Staff List</a></li>
	</ol>
		<!-- view table -->
	<h1>View table</h1>
	<ol>
		<li><a href="<%=request.getContextPath()%>/ViewTable/actorInfoList.jsp">Actor Info List</a></li>
		<li><a href="<%=request.getContextPath()%>/ViewTable/customerList.jsp">Customer List</a></li>
		<li><a href="<%=request.getContextPath()%>/ViewTable/filmList.jsp">Film List</a></li>
		<li><a href="<%=request.getContextPath()%>/ViewTable/NBSFList.jsp">Nice But Slower Film List</a></li>
		<li><a href="<%=request.getContextPath()%>/ViewTable/salesByFilmCategoryList.jsp">Sales By Film Category</a></li>
		<li><a href="<%=request.getContextPath()%>/ViewTable/salesByStoreList.jsp">Sales By Store List</a></li>
		<li><a href="<%=request.getContextPath()%>/ViewTable/viewStaffList.jsp">Staff(View) List</a></li>
	</ol>
		<!-- procedure -->
	<h1>Procedure</h1>
	<ol>
		<li><a href="<%=request.getContextPath()%>/Procedure/filmInStockForm.jsp">Film In Stock</a></li>
		<li><a href="<%=request.getContextPath()%>/Procedure/filmNotInStockForm.jsp">Film Not In Stock</a></li>
		<li><a href="<%=request.getContextPath()%>/Procedure/rewardsReportForm.jsp">Rewords Report</a></li>
	</ol>
		<!-- search form -->
	<h1>Data search</h1>
	<ol>
		<li><a href="<%=request.getContextPath()%>/filmSearchForm.jsp">Film List(View) Search</a></li>
		<li><a href="<%=request.getContextPath()%>/rentalSearchForm.jsp">Rental Table Search</a></li>
	</ol>
		<!-- staitstics form -->
	<h1>Data statistics</h1>
	<ol>
		<li><a href="<%=request.getContextPath()%>/statsData.jsp">Data statistics</a></li>
	</ol>
</body>
</html>