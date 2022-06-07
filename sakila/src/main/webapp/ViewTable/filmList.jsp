<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "dao.FilmListDao" %>
<%@ page import = "vo.FilmList" %>
<%	
	// 페이징 코드
	int currentPage = 1;
	if(request.getParameter("currentPage") != null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	
	int rowPerPage = 15; // rowPerPage는 변경될 수 있음
	// 현재 페이지 currentPage 변경 -> beginRow로 
	int beginRow = (currentPage-1) * rowPerPage;
	
	// FilmList
	FilmList film = new FilmList();
	// FilmListDao
	FilmListDao filmDao = new FilmListDao();
	// SelectFilmListByPage
	List<FilmList> list = filmDao.selectFilmListByPage(beginRow, rowPerPage);
		
	// 마지막 페이지 변수 값 저장 코드
	int lastPage = 0;
	int totalCount = filmDao.selectFilmListTotalRow();
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
	<h1>Film List</h1>
	<table border="1">
		<thead>
			<tr>
				<th>FID</th>
				<th>title</th>
				<th>description</th>
				<th>category</th>
				<th>price</th>	
				<th>length</th>	
				<th>rating</th>	
				<th>actors</th>					
			</tr>
		</thead>
		<tbody>
			<%
				for(FilmList f : list) {
			%>
				<tr>
					<td><%=f.getFid()%></td>
					<td><%=f.getTitle()%></td>	
					<td><%=f.getDescription()%></td>
					<td><%=f.getCategory()%></td>
					<td><%=f.getPrice()%></td>
					<td><%=f.getLength()%></td>
					<td><%=f.getRating()%></td>
					<td><%=f.getActors()%></td>
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
				<a href="<%=request.getContextPath()%>/ViewTable/filmList.jsp?currentPage=<%=currentPage-1%>">이전</a>
		<%		
			}
		%>
		<%
			if(currentPage < lastPage) {
		%>
				<a href="<%=request.getContextPath()%>/ViewTable/filmList.jsp?currentPage=<%=currentPage+1%>">다음</a>
		<%
			}
		%>
	</div>
</body>
</html> 