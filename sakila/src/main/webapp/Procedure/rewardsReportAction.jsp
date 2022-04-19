<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.RewardsReport" %>
<%@ page import="java.util.*" %>
<%
	// rewardsReportsForm
	// 변수 선언후 초기화
	int minMonthlyPurchase = 9999;
	minMonthlyPurchase = Integer.parseInt(request.getParameter("minMonthlyPurchase"));
	double minDollarAmountPurchase = 9999;
	minDollarAmountPurchase = Double.parseDouble(request.getParameter("minDollarAmountPurchase"));
	
	// rewardsReportsForm null값 체크 코드 + 디버깅 코드
	if((request.getParameter("minMonthlyPurchases")!= null && request.getParameter("minMonthlyPurchases")!= "") && (request.getParameter("minDollarAmountPurchased")!= null && request.getParameter("minDollarAmountPurchased")!= "")) {
		minMonthlyPurchase = Integer.parseInt(request.getParameter("minMonthlyPurchases"));
		System.out.println(minMonthlyPurchase + " <--minMonthlyPurchases");
		minDollarAmountPurchase = Double.parseDouble(request.getParameter("minDollarAmountPurchased"));
		System.out.println(minDollarAmountPurchase + " <--minDollarAmountPurchased");
	}
	
	// RewardsReport 
	RewardsReport r = new RewardsReport();
	// RewardsReport Map
	Map<String, Object> map = r.rewardsReport(minMonthlyPurchase, minDollarAmountPurchase);
	// Hash Map으로 데이터 저장
	ArrayList<HashMap<String,Object>> list = (ArrayList<HashMap<String,Object>>)map.get("list"); 
	
	// SQL 쿼리에서 받은 count
	int count = 0;
	count = (Integer)map.get("count"); 
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Rewards Report</title>
</head>
<body>
	<h1>Rewards Report</h1>
	<div><%=minMonthlyPurchase%>번 이상 구매하여 <%=minDollarAmountPurchase%>달러이상 지출을 한 고객은 총 <%=count%>명입니다.</div>
	<table border="1">
		<thead>
			<th>customer_id</th>
			<th>store_id</th>
			<th>first_name</th>
			<th>last_name</th>
			<th>email</th>
			<th>address_id</th>
			<th>active</th>
			<th>create_date</th>
			<th>update_date</th>
		</thead>
	<tbody>
		<tr>
			<%
				for(Map m : list){
			%>
				<td><%=(Integer)m.get("customerId")%></td>
				<td><%=(Integer)m.get("storeId")%></td>
				<td><%=(String)m.get("firstName")%></td>
				<td><%=(String)m.get("lastName")%></td>
				<td><%=(String)m.get("email")%></td>
				<td><%=(Integer)m.get("addressId")%></td>
				<td><%=(Integer)m.get("active")%></td>
				<td><%=(String)m.get("createDate")%></td>
				<td><%=(String)m.get("updateDate")%></td>
			<%
				}
			%>
		</tr>
	</tbody>
	</table>
</body>
</html> 