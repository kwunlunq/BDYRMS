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
	   以確保能夠與 ＜c:url＞有相同的功能 正確的讀取專案內的文件或檔案
	 for example: (掛載的JS檔案中必須要 動態加入images資料夾下的一張圖片 圖片名稱為 01.jpg )
	 
	 function showIMG(){
	 	var createIMG = document.createElement("img");
	 	var url_IMG = contextPath + "/images/01.jpg";
	 	createIMG.setAttribute( "src" ,  url_Pic );
	 	$('body').append(createIMG);
	 }
	 
	 以上可以確保檔案移動時  不用更改路徑 還是可以正確的讀取所需要的文件及圖片!
-->
<style>
#top{
		margin-left:10%; 
   		margin-top:20px;
   		border-bottom:1px solid black;
}
#mid{
		margin-left:40%; 
   		
}
#top>input[type=button]{
width: 200px;
height: 80px;
font-size: 30px;
font-weight: bold;
background-color: paleturquoise;
border-radius: 10px;
}
</style>
<script type="text/javascript">
var contextPath='<%=request.getContextPath()%>';
</script>
<script src="<c:url value="/js/jquery-1.11.0.min.js"/>"></script>
<script src="<c:url value="/js/jquery-ui-1.10.4.min.js"/>"></script>
<link rel="stylesheet" type="text/css" id="cssStyle" href="<c:url value="/css/main.css"/>">
<script src="<c:url value="/js/main.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 詳細說明2 : 把 Welcome 改成你個功能名稱  請使用"English"不知道怎麼取可以請教 ［Kevin］ -->
<title>BDY RSM - ModifyMenu</title>
</head>

<body>
<script type="text/javascript">
$(function(){
	$('#modifyFood').click(function(){
		$('#div1').css("display","block");
		$('#div2').css("display","none");
	});
	
	$('#modifyPackage').click(function(){
		$('#div1').css("display","none");
		$('#div2').css("display","block");
	});
});
</script>
<div id="mainBox">
<div id="header">
<jsp:include page="/mainpage/header.jsp" />
</div>
<div id="aside">
<jsp:include page="/mainpage/aside.jsp" />
</div>

<div id="article">
<!-- START Write -->
		<div id="top">
		<input id="modifyFood" type="button" value="修改食物">
		<input id="modifyPackage" type="button" value="修改套餐">		
		</div>
		<div id="mid">
		<div id="div1" style="display: none">
		<input type="button" value="新增食物">
		<input type="button" value="刪除食物">
		<input type="button" value="修改食物">
		<input type="button" value="查詢食物">
		</div>
		<div id="div2" style="display: none">
		<input type="button" value="新增套餐">
		<input type="button" value="刪除套餐">
		<input type="button" value="修改套餐">
		<input type="button" value="查詢套餐">
		</div>
		</div>
		<div id="bot">bbbb</div>
		
		
<!-- END Write-->
</div>
<div id="footer">
<jsp:include page="/mainpage/footer.jsp" />
</div>
</div>
</body>
</html>