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
<title>BDY RSM - Meal Ordering</title>
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
<!-- START Write -->

<!-- <script src="//code.jquery.com/jquery-1.9.1.js" ></script> -->

<script type="text/javascript">
var contextPath='<%=request.getContextPath()%>';
</script>
<script src="<c:url value="/js/jquery.js"/>"></script>
<script src="<c:url value="/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/js/main.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/ordermain.css"/>">
<script type="text/javascript">
$(function() {
	 getSetdetail();
	//  css掛載
// 	$('#btnArea>:button').mouseover(function() {
// 					$(this).css("background", "#44C3B6");})
// 				.mouseout(function() {
// 					$(this).css("background", "#3BB0A4");});
	/*
	*	解決IE緩存問題
	*/
	$.ajaxSetup({ cache: false });
});

var contextPath = "${pageContext.request.contextPath}";

/*
 * 當"點套餐"按鈕按下時, 利用$.getJSON呼叫/order/getSetDetailServlet
 * 將回傳結果(result)傳給showSetBtnWithDetail()
 */
function getSetdetail() {
	var url = contextPath+"/order/GetMainServlet";
	$.getJSON(url, function(result) {
		$('.page1').hide();
		showMains(result);
	});
}

 
 /*
  * <div id="mainArea">
 		<h2 id="mainTitle">牛排</h2>
 		<hr>
 		<h4 id="mainContent">牛小排</h4>
 		<h4 id="mainContent">菲力牛排</h4>
 		<br>
 		<h2 id="mainTitle">披薩</h2>
 		<h4 id="mainContent">夏威夷披薩</h4>
 	</div>
 */

 /*
 [  {"牛排":["牛小排","菲力牛排","肋眼牛排"]}, 
    {"披薩":["夏威夷披薩","海鮮披薩","龍蝦披薩"]},
    {"義大利麵":["奶油蛤蠣義大利麵","青醬海鮮義大利麵","好吃的義大利麵"]},
    {"燉飯":["OO燉飯","XX燉飯","VV燉飯"]}
   ]
 */
function showMains(mains) {
	 var div = document.getElementById("mainArea");
	 
	 for (var i = 0; i < mains.length; i++) {
		 var title = document.createElement("h2");
		 title.setAttribute("id", "mainTitle");
		 
		 $(div).append(title)
	 	   .append('<hr>');
		 for (key in mains[i]) {
			 var titleText = document.createTextNode(key);
			 console.log(key);
			 $(title).append(titleText);
			 
			 var content = document.createElement("h4");
			 content.setAttribute("id", "mainContent");
			 var contents = mains[i][key];
			 for (var j = 0; j < contents.length; j++) {
				 console.log(contents[j]);
				 var contextText = document.createTextNode(contents[j]);
				 console.log(contextText);
				 $(content).append(contextText)
				  		   .append('<br>');
				 $(div).append(content);
			 }
		 }
		 $(div).append('<br>');
	 }
}
 
</script>
</head>
<body>
	<div id="mainArea">
	</div>
</body>
<!-- END Write-->
</div>
<div id="footer">
<jsp:include page="/mainpage/footer.jsp" />
</div>
</div>
</body>
</html>