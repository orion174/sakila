<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "vo.RewardCustomer" %>
<%@ page import = "dao.RewardsReportDao" %>
<%@ page import = "java.util.*" %>
<%
	// 요청값 받기
	// minMonthlyPurchases
	int minMonthlyPurchases =0; 
	if(request.getParameter("minMonthlyPurchases")!=null&&!request.getParameter("minMonthlyPurchases").equals("")){
		minMonthlyPurchases = Integer.parseInt(request.getParameter("minMonthlyPurchases"));
		System.out.println(minMonthlyPurchases+"<--minMonthlyPurchases");
	}
	// minDollarAmuntPurchased
	int minDollarAmuntPurchased =0; 
	if(request.getParameter("minDollarAmuntPurchased")!=null&&!request.getParameter("minDollarAmuntPurchased").equals("")){
		minDollarAmuntPurchased = Integer.parseInt(request.getParameter("minDollarAmuntPurchased"));
		System.out.println(minDollarAmuntPurchased+"<--minDollarAmuntPurchased");
	}
	// 총명수
	int count =0;
	// Dao값 받기
	RewardsReportDao rdo = new RewardsReportDao();
	Map<String,Object> rewardsReport = rdo.rewardsReport(minMonthlyPurchases, minDollarAmuntPurchased);
	// count 받기
	count = (Integer)rewardsReport.get("count");
	List<RewardCustomer> customerList = (List<RewardCustomer>)rewardsReport.get("customer");
	// customer List 받기
	
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>RewardsReport</title>
</head>
<body>
	<h1>RewardsReport</h1>
	<a href="<%=request.getContextPath()%>/index.jsp" >index</a>
	<form method="post" action="<%=request.getContextPath()%>/Procedure/rewardsReport.jsp">
		월간 최소 대여량을 입력하세요 : <input type = "number" name = "minMonthlyPurchases">	
		최소 지불 금액을 입력하세요 : <input type = "number" name = "minDollarAmuntPurchased">
		<button type="submit"> 검색</button>	
	</form>
	<%
		if(minMonthlyPurchases!=0 && minDollarAmuntPurchased!=0) {
	%>
	<h2>최소 <%=minMonthlyPurchases%> 달 이상의 기간 중 <%=minDollarAmuntPurchased%> 달러 이상의 금액을 쓴 고객은 총 <%=count %>명이며 명단은 다음과 같습니다.</h2>
	<table>
		<thead>
			<tr>
				<th>customerId</th>
				<th>storeId</th>
				<th>firstName</th>
				<th>lastName</th>
				<th>email</th>
				<th>addressId</th>
				<th>active</th>
				<th>createDate</th>
				<th>lastUpdate</th>
			</tr>
		<tbody>
				<%
					for( RewardCustomer c : customerList){
				%>
			<tr>
				<td><%=c.getCustomerId()%></td>
				<td><%=c.getStoreId()%></td>
				<td><%=c.getFirstName()%></td>
				<td><%=c.getLastName()%></td>
				<td><%=c.getEmail()%></td>
				<td><%=c.getAddressId()%></td>
				<td><%=c.getActive()%></td>
				<td><%=c.getCreateDate()%></td>
				<td><%=c.getLastUpdate()%></td>
			</tr>
				<%
				}
			}
				 %>
		</tbody>
	</table>
</body>
</html>