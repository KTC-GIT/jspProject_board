<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 중복 확인</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		$("#nick").keyup(function(){
			var keyValue = /^[0-9a-zA-Z가-힣ㄱ-ㅎㅏ-ㅣ]*$/g;
			var pw = $("#nick").val();
			if(!keyValue.test(pw)){
				$("#nick").val("");
			}
		});
	});
	
	function windowClose(){
		
		var nickText = "<c:out value='${param.nick}'/>";
		opener.document.getElementById("nickTextBox").innerHTML = "<input type = 'text' name = 'nick' id = 'nick' class='txtbox' size = '40' maxlength='10' value='"+nickText+"' style='background-color:#aaaaaa;' readonly/>";
		
		self.opener = self;
		window.close();
	}
	
	function nickCheckFunc(){
		var nick = document.getElementById("nick").value;
		
		if(nick==""){
			alert("아이디를 입력하세요");
			$("#nick").focus();
		}
		else if(nick.length<1 || nick.length>10){
			alert("닉네임은 1~10길이만 가능합니다!");
			$("#nick").focus();
		}
		else{
			location.href = "nickCheck?nick="+nick;
		}
	}
</script>
<style>
	#duplicateCheck{
		width : 600px;
		text-align : left;
	}
	#nickText{
		color : blue;
		font-weight : bolder;
		font-size : 20px;
	}
	#btn{
		padding-left : 35%;
		padding-top : 10%;
	}
	.txt{
		font-size : 15px;
		padding : 15px;
	}
	#nick{
		padding : 10px;
		font-size : 15px;
	}
	#checkbtn,#closebtn{
		width : 135px;
		height : 45px;
		background-color : #1478FF;
		color : white;
		border : 0;
		border-bottom : 3px solid #0064FF;
		font-size : 17px;
		outline : 0;
		border-radius : 3px;
	}
	#closebtn{
		background-color : #3c3c3c;
		border-bottom : 3px solid #282828;
	}
	
</style>
</head>
<body>
	<c:if test="${param.checkResult==1}"> <!-- 1일때는 사용가능! -->
		<table id="duplicateCheck">
			<tr>
				<td class="txt"><span id="nickText">${param.nick}</span>는 사용 가능합니다!</td>
			</tr>
			<tr>
				<td class="txt">닉네임 검색
					<input type = "text" name = "nick" id = "nick" size = "35" maxlength="10" placeholder = "1~10자리 가능"/>
					<input type = "button" id="checkbtn" value="닉네임 중복확인" onclick="nickCheckFunc()"/>
				</td>
			</tr>
			<tr>
				<td id="btn"><input type="button" value = "닫기" id="closebtn" onclick="windowClose()"/></td>
			</tr>
		</table>
	</c:if>
	
	<c:if test="${param.checkResult==0}">  <!-- 0일때는 중복이 존재함 -->
		<table id="duplicateCheck">
			<tr>
				<td id="txt"><span id="nickText">${param.nick}</span>는 사용중입니다!</td>
			</tr>
			<tr>
				<td>닉네임 검색
					<input type = "text" name = "nick" id = "nick" size = "35" maxlength="10" placeholder = "1~10자리 가능"/>
					<input type = "button" id="checkbtn" value="닉네임 중복확인" onclick="nickCheckFunc()"/>
				</td>
			</tr>
		</table>
	</c:if>
</body>
</html>