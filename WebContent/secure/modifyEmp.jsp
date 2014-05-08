<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 說明 : 此 mainpage.jsp 即是一個樣版 整份複製後 修改檔案名稱
		      最後 將你要做的功能以及介面 都寫在 article -->
<!-- 所有的 "路徑" 都必須加上  ＜c:url＞ 方法 所以掛載 JSTL 是必要的 (勿刪) -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 27~29行JavaScript程式碼 作用等同於 ＜c:url＞的功能 
	   如果有掛載JS檔案 且 利用JavaScript產生有包含路徑讀取專案內其他文件或檔案的時候
	   請在路徑前面 加上 第28行程式碼所產生的變數 "contextPath" 
	   以確保能夠與 ＜c:url＞ 有相同的功能 正確的讀取專案內的文件或檔案
	 for example: (掛載的JS檔案中必須要 動態加入images資料夾下的一張圖片 圖片名稱為 01.jpg )
	 
	 function showIMG(){
	 	var createIMG = document.createElement("img");
	 	var url_IMG = contextPath + "/images/01.jpg";
	 	createIMG.setAttribute( "src" ,  url_Pic );
	 	$('body').append(createIMG);
	 }
	 
	 以上可以確保檔案移動時  不用更改路徑 還是可以正確的讀取所需要的文件及圖片!
-->
<!-- 必要的 Script 與 CSS 外掛 (以下) -->
<script type="text/javascript">
var contextPath='<%=request.getContextPath()%>';
</script>
<script src="<c:url value="/js/jquery.js"/>"></script>
<script src="<c:url value="/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/js/main.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui.css"/>">
<!-- 必要的 Script 與 CSS 外掛  (以上)-->
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以下)-->

<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以上)-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 詳細說明2 : 把 Welcome 改成你個功能名稱  請使用"English"不知道怎麼取可以請教 ［Kevin］ -->
<title>BDY RSM - ModifyEmployee</title>
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
<div id="mainBox">
<div id="header">
<jsp:include page="/mainpage/header.jsp" />
</div>
<div id="aside">
<jsp:include page="/mainpage/aside.jsp" />
</div>

<div id="article">

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
</div>
<div id="footer">
<jsp:include page="/mainpage/footer.jsp" />
</div>
</div>
</body>
</html>