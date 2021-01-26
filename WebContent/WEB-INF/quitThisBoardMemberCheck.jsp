<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>탈퇴 확인</title>
<style type="text/css">
	#pwCheckTable{
		width : 400px;
		margin-right : auto;
		margin-left : auto;
		padding : 15%;
		text-align : center;
	}
	.btn1{
		text-align : left;
		width : 200px;
		padding-top : 10px;
		
	}
	.btn2{
		text-align : right;
		width : 200px;
		padding-top : 10px;
	}
	#title{
		width : 100%;
		font-size : 25px;
		padding : 20px;
	}
	#pw{
		font-size : 15px;
		padding : 10px;
		font-size : 15px;
		border-radius : 3px;
	}
	#modifybtn{
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
	#deletebtn, #backbtn{
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
	
</style>
<script>
	function submitFunc(){
		var result = confirm("탈퇴하시겠습니까?");
		if(result){
			pwCheckForm.submit();
		}
	}
</script>
</head>
<body>
	<form name = "pwCheckForm" method = "post" action = "requestDeleteAccount">
		<table id = "pwCheckTable">
		<tr>
			<td id = "title" colspan = "2"><label for = "pw"><b>비밀 번호 입력</b> </label></td>
		</tr>
		<tr>
			<td colspan = "2"><input type = "password" name = "pw" id = "pw"/></td>
		</tr>
		<tr>
			<td class = "btn1">
				<input type = "button" value = "확인" id = "modifybtn" onclick = "submitFunc()"/>
			</td>
			<td class = "btn2">
				<input type = "button" value = "뒤로" id = "backbtn" onclick = "history.back();"/>
			</td>
		</tr>
		</table>
	</form>
</body>
</html>