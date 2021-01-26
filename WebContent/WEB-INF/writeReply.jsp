<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓 글 쓰 기</title>
<style type="text/css">
	#rTable{
		width : 900px;
		margin-left : auto;
		margin-right : auto;
		padding : 15px;
	}
	.topline{
		border-top : 3px solid #4646CD;
		padding : 8px;
	}
	.bottomline{
		border-bottom : 3px solid #4646CD;	
		padding : 8px;
	}
	.rwbtn{
		width : 100%;
		text-align : right;
		padding : 8px;
	}
	#rwbtn{
		width : 70px;
		height : 45px;
		background-color : #1478FF;
		color : white;
		border : 0;
		border-bottom : 3px solid #0064FF;
		font-size : 17px;
		outline : 0;
		border-radius : 3px;
	}
	.smalltext{
		font-size : 15px;
		padding : 10px;
		font-style : bold;
		border-radius : 3px;
	}
	#blank{
		padding:10px;
		width:200px;
	}
</style>
</head>
<body>
	
	<form method = "post" action = "writeR">
		<table id = "rTable">
			<tr>
				<td class = "topline" colspan = "3"></td>
			</tr>
			<tr>
				<c:choose>
					<c:when test="${sId!=null}">
						<td style="font-size:17px;font-weight:bolder;">${sNick}</td>
					</c:when>
					<c:otherwise>
						<td><input type = "text" name = "id" placeholder = "ID" class = "smalltext"/></td>
					</c:otherwise>
				</c:choose>
						<td rowspan = "2"><textarea rows="5" cols="80" name = "content" class = "smalltext" style="resize:none;"></textarea></td>
			</tr>
			<tr>
				<c:choose>
					<c:when test="${sId!=null}">
						<td id="blank"></td>
					</c:when>
					<c:otherwise>
						<td><input type = "password" name = "pw" placeholder = "비밀번호" class = "smalltext"/></td>
					</c:otherwise>
				</c:choose>
			</tr>
			<tr>
				<td colspan = "2" class = "rwbtn">
					<input type = "submit" value = "등록" id = "rwbtn"/>
					<input type="hidden" value="<c:out value='${param.searchOption}'/>" name="searchOption"/>
				</td>
			</tr>
			<tr>
				<td class = "bottomline" colspan = "3"></td>
			</tr>
		</table>
	</form>
</body>
</html>