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
		$("#id").keyup(function(){
			var keyValue = /^[0-9a-zA-Z]*$/g;
			var pw = $("#id").val();
			if(!keyValue.test(pw)){
				$("#id").val("");
			}
		});
	});
	
	function windowClose(){
		
		var idText = "<c:out value='${param.id}'/>";
		opener.document.getElementById("idTextBox").innerHTML = "<input type='text' name = 'id' id = 'id' class='txtbox' size = '40' maxlength = '20' value = '"+idText+"' style='background-color:#aaaaaa;' readonly/>";
		
		self.opener = self;
		window.close();
	}
	
	function idCheckFunc(){
		var id = document.getElementById("id").value;
		
		if(id==""){
			alert("아이디를 입력하세요");
			$("#id").focus();
		}
		else if(id.length<6 || id.length>20){
			alert("아이디는 6~20길이만 가능합니다!");
			$("#id").focus();
		}
		else{
			location.href = "idCheck?id="+id;
		}
	}
</script>
<style>
	#duplicateCheck{
		width : 600px;
		text-align : left;
	}
	#idText{
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
	#id{
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
				<td class="txt"><span id="idText">${param.id}</span>는 사용 가능합니다!</td>
			</tr>
			<tr>
				<td class="txt">아이디 검색
					<input type="text" name = "id" id = "id" size = "35" maxlength = "20" placeholder = "6~20자리 가능/영문대소문자와 숫자만 가능"/>
					<input type = "button" id="checkbtn" value="ID중복확인" onclick="idCheckFunc()"/>
				</td>
			</tr>
			<tr>
				<td id="btn"><input type="button" id="closebtn" value = "닫기" onclick="windowClose()"/></td>
			</tr>
		</table>
	</c:if>
	
	<c:if test="${param.checkResult==0}">  <!-- 0일때는 중복이 존재함 -->
		<table id="duplicateCheck">
			<tr>
				<td><span id="idText">${param.id}</span>는 사용중입니다!</td>
			</tr>
			<tr>
				<td class="txt">아이디 검색
					<input type="text" name = "id" id = "id" size = "35" maxlength = "20" placeholder = "6~20자리 가능/영문대소문자와 숫자만 가능"/>
					<input type = "button" id="checkbtn" value="ID중복확인" onclick="idCheckFunc()"/>
				</td>
			</tr>
		</table>
	</c:if>
</body>
</html>