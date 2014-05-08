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
#tabs{
		height:100%;
		width:100%;
}
#top{
		/*margin-left:10%;*/ 
   		margin-top:10px;
   		margin-bottom:10px;
}
#mid{
		/*margin-left:40%;*/ 
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
 
 <script src="//code.jquery.com/jquery-1.10.2.js"></script>
 <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
 
<link rel="stylesheet" type="text/css" id="cssStyle" href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="<c:url value="/js/main.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 詳細說明2 : 把 Welcome 改成你個功能名稱  請使用"English"不知道怎麼取可以請教 ［Kevin］ -->
<title>BDY RSM - manageIndex</title>
</head>

<body>
<script type="text/javascript">
$(function(){
	$( "#tabs" ).tabs();
	
});
</script>
<script src="<c:url value="/js/Food.js"/>"></script>
<div id="mainBox">
<div id="header">
<jsp:include page="/mainpage/header.jsp" />
</div>
<div id="aside">
<jsp:include page="/mainpage/aside.jsp" />
</div>

<div id="article">
<!-- START Write -->
<form action="manageIndex.controller" method="post">
<div id="tabs">
  <ul>
    <li><a href="#tabs-1"  id="modifyfood">修改食物</a></li>
    <li><a href="#tabs-2"  id="modifypackage">修改套餐</a></li>
    <li><a href="#tabs-3"  id="modifycount">修改折扣</a></li>
  </ul>
  		<div id="tabs-1">		
		<div id="mid">
		<div id="div1" style="display: block">
		共${count }s筆
		<table border="1">
		<thead>
		<tr>
		<th>食物名稱</th>
		<th>食物價錢</th>
		<th>庫存量</th>
		<th>說明</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="food" items="${resultfood }">
		<tr>
		<td>${food.name }</td>
		<td>${food.price }</td>
		<td>${food.qty }</td>
		<td>${food.descript }</td>
		<td>修改</td>
		<td>刪除</td>
		</tr>
		</c:forEach>
		</tbody>
		</table>
		
		</div>
		
		</div></div>
		
		 <div id="tabs-2">
		 	修改套餐
		 <div id="div2" style="display: block">
		<input type="button" value="新增套餐">
		<input type="button" value="刪除套餐">
		<input type="button" value="修改套餐">
		<input type="button" value="查詢套餐">
		</div>
		 </div>
		 <div id="tabs-3">
		 	修改折扣
		 </div>
 
</div>
</form>
<!-- END Write-->
</div>
<div id="footer">
<jsp:include page="/mainpage/footer.jsp" />
</div>
</div>
</body>
</html>