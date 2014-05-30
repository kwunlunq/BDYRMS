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
<%-- <script src="<c:url value="/js/jquery.js"/>"></script> --%>
<script src=http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js></script>
<script src="<c:url value="/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/js/main.js"/>"></script>
<script src="<c:url value="/js/jquery.dataTables.min.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui.css"/>">

<!-- 必要的 Script 與 CSS 外掛  (以上)-->
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以下)-->
<script src="<c:url value="/js/mainpage.js"/>"></script>
<script src="<c:url value="/js/manageInside.js"/>"></script> 
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以上)-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 詳細說明2 : 把 Welcome 改成你個功能名稱  請使用"English"不知道怎麼取可以請教 ［Kevin］ -->
<title>BDY RMS - ManageIndise</title>
<style>
.setCol{
 	text-align:center;			
	float:left;
	width:32%;
	height:100%;
/* 	border-left:1px solid blue; */
}
</style>
</head>
<body>
	<div id="loadingControl"></div>
	<div id="loading" style=""><img src="<c:url value="/images/loading.gif"/>"></div>
<div id="mainBox">
<div id="header">
<jsp:include page="/mainpage/header.jsp" />
</div>
<div id="mainPageContent">
<div id="aside">
<jsp:include page="/mainpage/aside.jsp" />
</div>
<div id="article">

	<div id="writeCodeInThisDiv">
	
	
	
	
	
	<div class="setCol">
	<a href="<c:url value="/secure/insertNewDiscount.jsp" />">新增折扣</a>
	<table id="table1"  align="center">
	<thead>
	<tr>
	<th>折扣名稱</th>
	<th>折扣優惠</th>
	<th>功能</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach var="disc" items="${disc }">
	<tr>
	<td>${disc.name }</td>
	<td>${disc.disPrice }</td>
	<td><input class='MainBtnColor' type="button" value="刪除" onclick="deleteDis(${disc.disId})"></td>
	</tr>
	</c:forEach>
	</tbody>
	</table>
	</div>
		
	<div class="setCol">
	<a href="<c:url value="/secure/insertNewMA.jsp" />">新增製作區域</a>
	<table id="table2"  align="center">
	<thead>
	<tr >
	<th>製作區域名稱</th>
	<th>功能</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach var="ma" items="${ma }">
	<tr>
	<td id="maTD${ma.maId}">${ma.name }</td>
	<td id="mabtn${ma.maId}">
		<input class='MainBtnColor' type="button" value="修改" onclick="updateMA(${ma.maId})">
		<input class='MainBtnColor' type="button" value="刪除" onclick="deleteMA(${ma.maId})">
	</td>
	</tr>
	</c:forEach>
	</tbody>
	</table>
	</div>
	
	<div class="setCol">
	<a href="<c:url value="/secure/insertNewMK.jsp" />">新增主餐種類</a>
	<table id="table3"  align="center">
	<thead>
	<tr>
	<th>主餐種類</th>
	<th>功能</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach var="mk" items="${mk }">
	<tr>
	<td>${mk.name }</td>
	<td><input class='MainBtnColor' type="button" value="刪除" onclick="deleteMK(${mk.mkId})"></td>
	</tr>
	</c:forEach>
	</tbody>
	</table>
	</div>
	
	
<!-- 	<div class="setCol"> -->
<%-- 	<a href="<c:url value="/secure/insertNewSet.jsp" />">新增套餐</a> --%> 
<!-- 	<h3>套餐類別</h3> -->
<!-- 	<table id="table4" border="1" align="center"> -->
<!-- 	<thead> -->
<!-- 	<tr> -->
<!-- 	<th>套餐名稱</th> -->
<!-- 	<th>套餐價位</th> -->
<!-- 	<th>功能</th> -->
<!-- 	</tr> -->
<!-- 	</thead> -->
<!-- 	<tbody> -->
<%-- 	<c:forEach var="set" items="${set }"> --%>
<!-- 	<tr> -->
<%-- 	<td>${set.name }</td> --%>
<%-- 	<td>${set.price }</td> --%>
<%-- 	<td><input class='MainBtnColor' type="button" value="刪除" onclick="deleteSet(${set.setId})"></td> --%>
<!-- 	</tr> -->
<%-- 	</c:forEach> --%>
<!-- 	</tbody> -->
<!-- 	</table> -->
<!-- 	</div> -->
	</div><!-- 	id="writeCodeInThisDiv" -->
</div>
</div>
<div id="footer">
<jsp:include page="/mainpage/footer.jsp" />
</div>
</div>
</body>
</html>