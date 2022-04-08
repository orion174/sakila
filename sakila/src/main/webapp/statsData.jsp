<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="dao.*" %>
<%	
	// StatsDataDao 호출
	StatsDataDao statsDataDao = new StatsDataDao();
	// 1. AMOUNT BY CUSTOMER (MORE THAN 180 DOLLAR) 데이터 저장
	List<Map<String, Object>> list_1 = statsDataDao.amountByCoustomer();
	// 2. FILM COUNT BY RENTALRATE 데이터 저장
	List<Map<String, Object>> list_2 = statsDataDao.countByRentalRate();
	// 3. FILM COUNT BY RATING 데이터 저장
	List<Map<String, Object>> list_3 = statsDataDao.countByRating();
	// 4. FILM COUNT BY LANGUAGE 데이터 저장
	List<Map<String, Object>> list_4 = statsDataDao.countByLanguage();
	// 5. FILM COUNT BY LENHTH 데이터 저장
	List<Map<String, Object>> list_5 = statsDataDao.countByRunningTime();	
	// 6. FILM SALES DAY OF WEEK 데이터 저장
	List<Map<String, Object>> list_6 = statsDataDao.salesDayOfWeek();
	/*
	// 7. Film Sales Ranking Of City
	List<Map<String, Object>> list_7 = statsDataDao.salesRankingOfCity();	
	*/  
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>1. AMOUNT BY CUSTOMER (MORE THAN 180 DOLLAR)</h1>
	<table border="1">
		<thread>
			<tr>
				<th>customer_id</th>
				<th>name</th>
				<th>total</th>
			</tr>
		</thread>

		<%
			for(Map m : list_1) {
		%>
			<tr>
				<td><%=m.get("customerId")%></td>
				<td><%=m.get("name")%></td>
				<td><%=m.get("total")%></td>
			</tr>
		<%
			}
		%>
	</table>
	
	<h1>2. FILM COUNT BY RENTALRATE</h1>
	<table border="1">
		<thread>
			<tr>
				<th>rental_rate</th>
				<th>COUNT</th>
			</tr>
		</thread>


		<%
			for(Map m : list_2) {
		%>
			<tr>
				<td><%=m.get("rentalRate")%></td>
				<td><%=m.get("cnt")%></td>
			</tr>
		<%
			}
		%>
	</table>
	
	<h1>3. FILM COUNT BY RATING</h1>
	<table border="1">
		<thread>
		<tr>
			<th>rental_rating</th>
			<th>ranking</th>
		</tr>
		</thread>
	
		<%
			for(Map m : list_3) {
		%>
			<tr>
				<td><%=m.get("rating")%></td>
				<td><%=m.get("cnt2")%></td>
			</tr>
		<%
			}
		%>
	</table>
	<h1>4. FILM COUNT BY LANGUAGE</h1>
	<table border="1">
		<thread>
		<tr>
			<th>name</th>
			<th>COUNT</th>
		</tr>
		</thread>
	
		<%
			for(Map m : list_4) {
		%>
			<tr>
				<td><%=m.get("language")%></td>
				<td><%=m.get("cnt3")%></td>
			</tr>
		<%
			}
		%>
	</table>
	
	<h1>5. FILM COUNT BY LENHTH</h1>
	<table border="1">
		<thread>
		<tr>
			<th>LENGTH</th>
			<th>COUNT</th>
		</tr>
		</thread>
	
		<%
			for(Map m : list_5) {
		%>
			<tr>
				<td><%=m.get("lengthTime")%></td>
				<td><%=m.get("cnt4")%></td>
			</tr>
		<%
			}
		%>
	</table>
	
	<h1>6. SALES DAYOFWEEK</h1>
	<table border="1">
		<thread>
		<tr>
			<th>staff_id</th>
			<th>w</th>
			<th>DAYOFWEEK</th>
			<th>c</th>
		</tr>
		</thread>
	
		<%
			for(Map m : list_6) {
		%>
			<tr>
				<td><%=m.get("staff_id")%></td>
				<td><%=m.get("w")%></td>
				<td><%=m.get("DAYOFWEEK")%></td>
				<td><%=m.get("c")%></td>
			</tr>
		<%
			}
		%>
	</table>
	
</body>
</html>