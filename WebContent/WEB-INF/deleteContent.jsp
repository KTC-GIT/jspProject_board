<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게 시 물 삭 제</title>
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
	function confFunc(){
		var val = confirm('삭제하시겠습니까?');
		if(val){
			deleteForm.submit();
		}
	}
</script>
</head>
<body>
	<form name = "deleteForm" method = "post" action = "delete">
		<table id = "pwCheckTable">
		<tr>
			<td id = "title" colspan = "2"><label for = "pw"><b>비밀 번호 입력</b> </label></td>
		</tr>
		<tr>
			<td colspan = "2"><input type = "password" name = "pw" id = "pw"/></td>
		</tr>
		<tr>
			<td class = "btn1">
				<input type = "button" value = "삭제" id = "deletebtn" onclick = "confFunc()"/>
			</td>
			<td class = "btn2">
				<input type = "button" value = "뒤로" id = "backbtn" onclick = "history.back();"/>
			</td>
		</tr>
		</table>
	</form>
</body>
</html>