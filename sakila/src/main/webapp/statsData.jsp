<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.*" %>
<%@ page import ="java.util.*" %>
<%
	// 요청값 받기 + NULL 값 체크 + 디버깅
	int key = 0;
	if(request.getParameter("key") != null && !request.getParameter("key").equals("")) {
		key = Integer.parseInt(request.getParameter("key"));
		System.out.println(key + "<--key");
	}
	
	// dao 값 요청
	StatsDataDao statsDataDao = new StatsDataDao();
	// 1) customer 별 총 amount 180$ 이상
	List<Map<String,Object>> amountByCoustomer = statsDataDao.amountByCoustomer();
	// 2) 영화 제일 많이 빌려간 customer 5명
	List<Map<String,Object>> countByCoustomer = statsDataDao.countByCoustomer();
	// 3) rental_rate별 영화 갯수
	List<Map<String,Object>> filmCountByRentalRate =statsDataDao.filmCountByRentalRate();
	// 4) rating별 영화 갯수
	List<Map<String,Object>> filmCountByRating = statsDataDao.filmCountByRating();
	// 5) language 별 영화 갯수
	List<Map<String,Object>> filmCountByLanguage = statsDataDao.filmCountByLanguage();
	// 6) length 별 영화 갯수
	List<Map<String,Object>> filmCountByLength = statsDataDao.filmCountByLength();
	// 7) actor 별 영화 가장 많이 출현한 배우 5명
	List<Map<String,Object>> actorByFilmCount = statsDataDao.actorByFilmCount();
	// 8) 영화별 빌려간 횟수와 총 매출
	List<Map<String,Object>> filmByRentalAmount =statsDataDao.filmByRentalAmount();
	// 9) store 각 매장마다 요일별 매출(월, 화, 수, 목, 금, 토, 일)
	List<Map<String,Object>> amountByDayOfWeek =statsDataDao.amountByDayOfWeek();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
	<title>statsData</title>
</head>
<body class="container">
	<h1>통계자료 요구사항</h1>
	<div class="list-group">
	<!--customer 별 총 amount 180$이상  -->
		<h2>1. customer 별 총 amount 180$이상</h2>
		<table class="table table-bordered">
			<thead>
				<th>customerId</th>
				<th>name</th>
				<th>total</th>
			</thead>
			<tbody>
		<%
			for(Map m : amountByCoustomer){
				
		%>
			<tr>
				<td><%=m.get("customerId") %></td>
				<td><%=m.get("name") %></td>
				<td><%=m.get("total") %></td>
			</tr>
		<%
			}
		%>
		</tbody>
	</table>

	<!--customer 별 가장 많이 영화 빌린 5명  -->
		<h2>2. customer 별 가장 많이 영화 빌린 5명</h2>
		<table class="table table-bordered">
			<thead>
				<th>customerId</th>
				<th>name</th>
				<th>total</th>
			</thead>
			<tbody>
			<%
				for(Map m : countByCoustomer){
					
			%>
				<tr>
					<td><%=m.get("customerId") %></td>
					<td><%=m.get("name") %></td>
					<td><%=m.get("total") %></td>
				</tr>
			<%
				}
			%>
			</tbody>
		</table>

	<!--rental_rate별 영화 갯수  -->
		<h2>3. rental_rate별 영화 갯수</h2>
			<table class="table table-bordered">
				<thead>
					<th>rentalRate</th>
					<th>total</th>
				</thead>
				<tbody>
				<%
					for(Map m : filmCountByRentalRate){
						
				%>
					<tr>
						<td><%=m.get("rentalRate") %></td>
						<td><%=m.get("total") %></td>
					</tr>
				<%
					}
				%>
		</tbody>
	</table>
	<!--rating별 영화 갯수 -->
	<h2>4. rating별 영화 갯수</h2>
			<table class="table table-bordered">
				<thead>
					<th>rating</th>
					<th>total</th>
				</thead>
				<tbody>
				<%
					for(Map m : amountByCoustomer){
						
				%>
					<tr>
						<td><%=m.get("rating") %></td>
						<td><%=m.get("total") %></td>
					</tr>
				<%
					}
				%>
				</tbody>
			</table>
	<!-- language 별 영화 갯수 -->
		<h2>5. language 별 영화 갯수</h2>
			<table class="table table-bordered">
				<thead>
					<th>name</th>
					<th>total</th>
				</thead>
				<tbody>
				<%
					for(Map m : filmCountByLanguage){
						
				%>
					<tr>
						<td><%=m.get("name") %></td>
						<td><%=m.get("total") %></td>
					</tr>
				<%
					}
				%>
				</tbody>
			</table>
	<!--length 별 영화 갯수  -->
		<h2>6. length 별 영화 갯수</h2>
			<table class="table table-bordered">
				<thead>
					<th>filmLength</th>
					<th>total</th>
				</thead>
				<tbody>
				<%
					for(Map m : filmCountByLength){
						
				%>
					<tr>
						<td><%=m.get("filmLength") %></td>
						<td><%=m.get("total") %></td>
					</tr>
				<%
					}
				%>
				</tbody>
			</table>

	<!--actor 별 영화 가장 많이 출현한 배우 5명  -->
		<h2>7. actor 별 영화 가장 많이 출현한 배우 5명</h2>
			<table class="table table-bordered">
				<thead>
					<th>actorName</th>
					<th>totalFilmCount</th>
				</thead>
				<tbody>
				<%
					for(Map m : actorByFilmCount){
						
				%>
					<tr>
						<td><%=m.get("actorName") %></td>
						<td><%=m.get("totalFilmCount") %></td>
					</tr>
				<%
					}
				%>
				</tbody>
			</table>
	<!--영화별 빌려간 횟수와 총 매출  -->
		<h2>8. 영화별 빌려간 횟수와 총 매출 5개만</h2>
			<table class="table table-bordered">
				<thead>
					<th>filmTitle</th>
					<th>totalFilmCount</th>
					<th>totalFilmAmount</th>
				</thead>
				<tbody>
				<%
					for(Map m : filmByRentalAmount){
						
				%>
					<tr>
						<td><%=m.get("title") %></td>
						<td><%=m.get("total") %></td>
						<td><%=m.get("amount") %></td>
					</tr>
				<%
					}
				%>
				</tbody>
			</table>
	<!--  -->
		<h2>9. store 각 매장마다 요일별 1주 매출(월, 화, 수, 목, 금, 토, 일)</h2>
			<table class="table table-bordered">
				<thead>
					<th>store</th>
					<th>day</th>
					<th>totalAmountCount</th>
				</thead>
				<tbody>
				<%
					for(Map m : amountByDayOfWeek){
						
				%>
					<tr>
						<td><%=m.get("storeId") %></td>
						<td><%=m.get("DAYOFWEEK") %></td>
						<td><%=m.get("cnt") %></td>
					</tr>
				<%
					}
				%>
				</tbody>
			</table>
	</div>	
</body>
</html> 