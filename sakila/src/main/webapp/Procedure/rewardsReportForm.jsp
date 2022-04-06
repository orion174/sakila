<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Rewards Report Form</title>
</head>
<body>
	<a href="<%=request.getContextPath()%>/index.jsp">index</a>
		
	<h1>Rewards Report Form</h1>
		<form method="post" action="<%=request.getContextPath()%>/Procedure/rewardsReportAction.jsp">
			<table>
				<tr>
					<td>월간 구매량 minMonthlyPurchase</td>
					<td><input type="text" name="minMonthlyPurchase"></td>
				</tr>
				<tr>
					<td>지출 금액 minDollarAmountPurchase</td>
					<td><input type="text" name="minDollarAmountPurchase"></td>
				</tr>
			</table>
			<button type="submit">검색</button>
		</form>
</body>
</html> 