<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이 페이지</title>
<style>
	#infoTable{
		margin-left : auto;
		margin-right : auto;
		text-align : center;
		width : 500px;
		padding-top:12%;
	}
	.btn{
		width : 100px;
		height : 45px;
		background-color : #1478FF;
		color : white;
		border : 0;
		border-bottom : 3px solid #0064FF;
		font-size : 17px;
		outline : 0;
		border-radius : 3px;
	}
	.title{
		font-size:25px;
		font-weight:bolder;
		padding : 10px;
		border : 1px solid #282828;
		border-right : 0px;
		border-radius : 3px;
	}
	.item{
		font-size : 20px;
		border : 1px solid #282828;
		border-radius : 3px;
	}
	#btns{
		padding-top:20px;
		border : 0px;
	}
	#cancelbtn{
		background-color : #3c3c3c;
		border-bottom : 3px solid #282828;
	}
	#requestbtn{
		background-color : #28288C;
		border-bottom : 3px solid #00008C;
	}
</style>
</head>
<body>
	<table id="infoTable">
		<tr>
			<td class="title"><img src="./images/iconmonstr-id-card-8-240.png" width="15px" height="15px"/>아이디</td>
			<td class="item">${sId}</td>
		</tr>
		<tr>
			<td class="title"><img src="./images/iconmonstr-tag-7-240.png" width="15px" height="15px"/>닉네임</td>
			<td class="item">${sNick}</td>
		</tr>
		<tr>
			<td class="title"><img src="./images/iconmonstr-candy-18-240.png" width="15px" height="15px"/>생년월일</td>
			<td class="item">${sBirth}</td>
		</tr>
		<tr>
			<td class="title"><img src="./images/iconmonstr-gmail-1-240.png" width="15px" height="15px"/>이메일</td>
			<td class="item">${sEmail}</td>
		</tr>
		<tr>
			<td class="title"><img src="./images/iconmonstr-mobile-thin-240.png" width="15px" height="15px"/>전화번호</td>
			<td class="item">${sTel}</td>
		</tr>
		<tr>
			<td colspan="2" id="btns">
				<a href="editAccountCheck"><button class="btn">정보 변경</button></a>
				<a href="requestDeleteAccountCheck"><button class="btn" id="requestbtn">탈퇴요청</button></a>
				<button class="btn" id="cancelbtn" onclick="history.back();">뒤로</button>
			</td>
		</tr>
	</table>
</body>
</html>