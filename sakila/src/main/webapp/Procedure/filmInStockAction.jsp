<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.FilmDao" %>
<%@ page import="java.util.*" %>
<%	
	// filmInStockForm에서 값 받아옴
	int filmId = 0;
	filmId = Integer.parseInt(request.getParameter("filmId"));
	int storeId = 0;
	storeId = Integer.parseInt(request.getParameter("storeId"));
	
	// filmInStockForm null값 체크 코드 + 디버깅 코드
	if((request.getParameter("filmId")!= null && request.getParameter("filmId")!= "") && (request.getParameter("storeId")!= null && request.getParameter("storeId")!= "")) {
		filmId = Integer.parseInt(request.getParameter("filmId"));
		System.out.println(filmId + " <--filmId");
		storeId = Integer.parseInt(request.getParameter("storeId"));
		System.out.println(storeId + " <--storeId");
	}
	
	// FilmDao 
	FilmDao filmDao = new FilmDao();
	// Map
	Map<String, Object> map = filmDao.filmInStock(filmId, storeId);
	// List
	List<Integer> list = (List<Integer>)map.get("list");
	
	// SQL count 저장
	int count = 0;
	count = (int) map.get("count"); 
	
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Film In Stock</title>
</head>
<body>
	<a href="<%=request.getContextPath()%>/index.jsp">index</a>
		
		<h1>Film In Stock</h1>
		<div><%=filmId%>번 영화가 <%=storeId%>번 가게에 <%=count%>개 남았습니다. </div>
		<div>inventory_id 목록</div>
		<%
			for(int id : list){
		%>
			<%=id %>
		<%
			}
		%>
</body>
</html>