<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BDY modifyEmp</title>
<script src="js/modify.js"></script>
<link rel=stylesheet type="text/css" href="css/ModifyEmp.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script>
	$(function(){
		$(function() {
		    $( "#datepicker" ).datepicker();
		  });
		$('#insertBTN').click(function(){
			$('#insertDIV').css("display","block");
			$('#modifyDIV').css("display","none");
			$('#selectDIV').css("display","none");
		});
		$('#modifyBTN').click(function(){
			$('#modifyDIV').css("display","block");
			$('#insertDIV').css("display","none");
			$('#selectDIV').css("display","none");
		});
		$('#selectBTN').click(function(){
			$('#selectDIV').css("display","block");
			$('#modifyDIV').css("display","none");
			$('#insertDIV').css("display","none");
		});
	});
</script>
</head>
<body>

<header>
<input  id="modifyBTN"  type="button" value="修改資料" >
<input 	id="insertBTN"  type="button" value="新增員工">
<input  id="selectBTN"  type="button" value="查詢員工" >
</header>
<article>
<div id="modifyDIV" style="display:none">
<h3>修改資料</h3>
</div>

<div id="insertDIV" style="display:none">
<script src="js/insert.js"></script>
<h3>新增員工</h3>
<form name="modify" method="post">
<div>身分證字號<input type="text" id="id" >
<img src="images/currect.gif" style="display:block"></div><br>
員工密碼:0000<br>
性別    <input type="radio" name="sex" name="male" >男
	<input type="radio" name="sex" name="female">女<br>
權限  :<select>
		<option value="priority" >1</option>
		<option value="priority" >2</option>
		<option value="priority" >3</option></select><br>
到職日期:<input type="text" id="datepicker"><br>
薪資 :<input type="text" id="salary"><br>
電話 :<input type="text" id="phone_number"><br>
地址 :<input type="text" id="address"><br>
<input type="button" value="確認" onclick="insert()">
<input type="button" value="取消">
</form>
</div>



<div id="selectDIV" style="display:none">
<h3>查詢員工</h3>
</div>
</article>
</body>
</html>