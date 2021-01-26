<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>실시간 검색</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		$("#allCheck").click(function(){
			var check = $("#allCheck").prop("checked");
			$("input[name=checkBtn]").prop("checked",check);
		});
		$("#reloadBtn").click(function(){
			location.reload();
		});
		$("#searchField").change(function(){
			$("#searchName").focus();
		});
	});
	
	function searchFunc(){
		var queryString = $("#myform").serialize();
		
		$.ajax({
			type: "POST",
			url: "realtimeCheck",
			data: queryString,
			success: function(jArray){
				$("#realTimeTable").empty();
				for(var i=0;i<jArray.length;i++){
					$("#realTimeTable").append('<tr><td><input type="checkbox" name="checkBtn" value="'+jArray[i].idx+'"/>'+jArray[i].idx+'</td><td>'+jArray[i].name+'</td><td>'+jArray[i].age+'</td><td>'+jArray[i].gender+'</td><td>'+jArray[i].address+'</td></tr>');
				}
			},
			error: function(error,request,status){
				alert('code: '+request.status+'\n message: '+request.responseText+'\n error: '+error);
			}
		});
	}
	/* var searchRequest = new XMLHttpRequest();	//현 웹사이트에서 서버에 요청객체 생성
	function searchFunc(){
		var searchField = $("#searchField").val();
		var searchName = $("#searchName").val();
		
		searchRequest.open("POST","realtime?searchFiled="+searchName+"&searchName="+searchName,true)	//ajax요청시 매개변수(전송방식,url,필드명)
		searchRequest.onreadystatechange = searchProcess;
		searchRequest.send(null);
	}
	
	function searchProcess(){
		var rtTable = document.getElementById("realTimeTable");
		rtTable.innerHTML = "";
		
		if(searchRequest.readyState == 4 && searchRequest.readyState == 200){
			var data = searchRequest.responseText;
			var parsed = JSON.parse(data);
			var result = parsed.result;
			
			for(var i=0;i<result.length;i++){
				var row = table.insertRow(0);
				for(var j=0;j<result[i].length;j++){
					var cell = row.insertCell(j);
					if(j==0){
						cell.innerHTML = "<input type='checkbox' name = 'checkBtn' value='"+result[i][j].item+"'/>"+result[i][j].item;
					}
					else{
						cell.innerHTML = result[i][j].item;
					}
				}
			}
		}
	} */
</script>
</head>
<body>
	<form name="myform" id="myform">
		<input type="button" value="선택 삭제" id="delBtn"/>
		<input type="button" value="새로고침" id="reloadBtn"/>
		<div>
			<select name="searchField" id="searchField">
				<option value="name">성명</option>
				<option value="age">나이</option>
				<option value="gender">성별</option>
				<option value="address">주소</option>
			</select>
			<input type="text" name="searchName" id="searchName" onkeyup="searchFunc();" autofocus/>
			<input type="button" value="검색" onclick="searchFunc();"/>
		</div>
	</form>
	<table style="text-align:center;">
		<tr>
			<th><input type="checkbox" id="allCheck" />번호</th>
			<th>성명</th>
			<th>나이</th>
			<th>성별</th>
			<th>주소</th>
		</tr>
		<tbody id = "realTimeTable">
		<c:forEach var="vo" items="${list}">
			<tr>
				<td><input type="checkbox" name="checkBtn" value="${vo.idx}"/>${vo.idx}</td>
				<td>${vo.name }</td>
				<td>${vo.age }</td>
				<td>${vo.gender}</td>
				<td>${vo.address }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>