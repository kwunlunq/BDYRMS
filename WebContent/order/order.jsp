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
var empId = '${empData.empId}';
var fId = '${param.fId}';
var fName = '${param.fName}';
var tbId = '${param.tbId}';
var tbName = '${param.tbName}';
var custNum = '${param.cNum}';
</script>
<script src="<c:url value="/js/jquery.js"/>"></script>
<script src="<c:url value="/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/js/main.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui.css"/>">
<!-- 必要的 Script 與 CSS 外掛  (以上)-->
<!-- 自己的  Script 與 CSS 外掛  (以下)-->
<%-- <link rel="stylesheet" type="text/css" href="<c:url value="/css/ordermain.css"/>"> --%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/order.css"/>">
<script>
$(function(){
	setTableDetial();
});
function setTableDetial(){
	$('#mainMenu span[id=floor]').text(decodeURI(decodeURI(fName)));
	$('#mainMenu span[id=floor]').attr("floorId",fId);
	$('#mainMenu span[id=tableNum]').text(decodeURI(decodeURI(tbName)));
	$('#mainMenu span[id=tableNum]').attr("tbId",tbId);
	$('#mainMenu span[id=peopleCount]').text(custNum);
}
</script>
<script type='text/javascript' src='/BDYRMS/dwr/engine.js'></script>
<script type='text/javascript' src="/BDYRMS/dwr/util.js"></script>
<script type='text/javascript' src='/BDYRMS/dwr/interface/Service.js'></script>
<script src="<c:url value="/js/order.js"/>"></script>
<!-- 自己的 Script 與 CSS 外掛  (以上)-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 詳細說明2 : 把 Welcome 改成你個功能名稱  請使用"English"不知道怎麼取可以請教 ［Kevin］ -->
<title>BDY RMS - Order</title>
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
<!-- START Write -->
<div id="mainMenu">
	<input  id="setTableAndPeople" class="MainBtnColor" type="button" value='修改桌號與人數'>
	樓層:<span id="floor" floorId="0" style="margin-left:10px;margin-right:20px;">-</span>
	桌號:<span id="tableNum" tbId="0" style="margin-left:10px;margin-right:20px;">-</span>
	人數:<span id="peopleCount" style="margin-left:10px;margin-right:20px;">-</span>
	<input id="orderConfirm" class="MainBtnColor" style='float:right' type='button' value='完成點餐'>
</div>
<div id="orderarea" class="selector">
	<ul id="ul-order">
	</ul>
</div>
<div id="orderlist" class="selector">
	<ul id="ul-detail">
		<li><a href="#orderlist-0">單點</a></li>
	</ul>
	<div id="orderlist-0">
	</div>
</div>


<div id="ChooseSetDialog" title="選擇套餐">
</div>
<div id="finishDialog" title="點餐明細">
</div>
<div id="ChooseTableAndPeopleDialog" title="選擇桌號與人數">
<!-- 選擇桌號與人數的Dialog -->
樓層:
<select id="setFloor">
<!-- <option value="0">B1</option> -->
<!-- <option value="1">1</option> -->
<!-- <option value="2">2</option> -->
</select>
桌號:
<select id="setTableNum">
<!-- <option value="0">A1</option> -->
<!-- <option value="1">A2</option> -->
<!-- <option value="2">A3</option> -->
</select>
<br>
人數:<input id="setNumberOfCust" type="text" size="13" readonly="readonly" value="0"><br>
<input id="num" class="MainBtnColor" type="button" value="1" style="width:30%">
<input id="num" class="MainBtnColor" type="button" value="2" style="width:30%">
<input id="num" class="MainBtnColor" type="button" value="3" style="width:30%"><br>
<input id="num" class="MainBtnColor" type="button" value="4" style="width:30%">
<input id="num" class="MainBtnColor" type="button" value="5" style="width:30%">
<input id="num" class="MainBtnColor" type="button" value="6" style="width:30%"><br>
<input id="num" class="MainBtnColor" type="button" value="7" style="width:30%">
<input id="num" class="MainBtnColor" type="button" value="8" style="width:30%">
<input id="num" class="MainBtnColor" type="button" value="9" style="width:30%"><br>
<input id="num" class="MainBtnColor" type="button" value="clear" style="width:30%">
<input id="num" class="MainBtnColor" type="button" value="0" style="width:30%">
<input id="num" class="MainBtnColor" type="button" value="back" style="width:30%">
</div>
<!-- END Write-->
	</div><!-- 	id="writeCodeInThisDiv" -->

</div>
</div>
<div id="footer">
<jsp:include page="/mainpage/footer.jsp" />
</div>
</div>
</body>
</html>