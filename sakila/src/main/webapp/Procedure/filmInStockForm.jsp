<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>filmInStockForm</title>
</head>
	<body>
		<a href="<%=request.getContextPath()%>/index.jsp">index</a>
	
		<h1>Film In Stock</h1>
		<form method="post" action="<%=request.getContextPath()%>/Procedure/filmInStockAction.jsp">
			<table border="1">
				<tr>
					<td>영화 번호 입력 film_id</td>
					<td><input type="text" name="filmId"></td>
				</tr>
				<tr>
					<td>가게 번호 입력 store_id</td>
					<td><input type="text" name="storeId"></td>
				</tr>
			</table>
			<button type="submit">입력 </button>
		</form>
	</body>
</html> 