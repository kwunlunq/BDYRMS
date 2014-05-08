<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	body, html{
		width: 100%;
		height: 100%;
	}
	#orderContainer {
		width: 100%;
		height: 100%;
		border: 2px solid;
/* 		background-color: #3487BF; */
/* 		background-color: silver; */
	}
	#btnArea {
/* 		position: relative; */
  		margin-left:38%; 
   		margin-top:10%;  
/*   		padding-top:200px; */
	}
	#btnArea>input[type=button] {
		width: 200px;
		height: 80px;
		border: 0;
/* 		background-image: linear-gradient( to bottom, #5FD4C6 40%,  #3BB0A4 70%); */
/*  		background-color: #3BB0A4; */
/* 		background-color: silver; */
 		border-radius: 10px;
		margin-top: 30px;
		font-size: 35px;
		font-weight: bold;
		background-color: white;
		text-align:center;
	}
	#btnSet {
		width: 200px;
		height: 80px;
		border: 0;
		font-weight: bold;
		font-size: 35px;
		background-color: white;
/*  		background-color: #3BB0A4; */
 		border-radius: 10px;
		margin-top: 30px;
		text-align:center;
	}
	
	span {
		font-size: 1em;
		text-align:center;
	}
</style>

<!-- <script src="//code.jquery.com/jquery-1.9.1.js" ></script> -->

<script type="text/javascript">
var contextPath='<%=request.getContextPath()%>';
</script>
<script src="<c:url value="/js/jquery.js"/>"></script>
<script src="<c:url value="/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/js/main.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui.css"/>">
<script type="text/javascript">
$(function() {
	/*
	 *  為"選套餐"按鈕新增mouse on click的function (getSetdetail)
	 */
	 getSetdetail();
// 	$('#setBtn').click(getSetdetail);
	
	
// 	$("body").on("mouseover", "#btnArea>input[type=button]", function() {
// 					$('#btnArea>:button').css("background", "#44C3B6");})
// 			 .on("mouseout", function() {
// 					$('#btnArea>:button').css("background", "#3BB0A4");});

		

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
// 		showSetBtnWithDetail(result);
		showMains(result);
	});
}


// function getSet() {
// 	var url = contextPath+"/order/getSetServlet";
// 	$.getJSON(url, function(result) {
// 		$('.page1').hide();
// 		showSetBtn(result);
// 	});
	
// };

/*
 * 將回傳結果sets(JSON物件陣列)
 * [   {"納帕套餐":{"前菜沙拉":1}},
 *     {"奧克蘭套餐":{"餐前麵包":1}},
 *     {"加州套餐":{"餐前湯品":1}}
 * ]
 * 取出內容
 * 將套餐名稱做成button
 * 套餐明細放在span中, 再放進div中 
 * 掛在外層div(#btnArea)中
 * <div id="btnArea">
 *  <input type="button" id="btnSet" value="A套餐">
 *  	<div> 
 *			  <span> 類別 </span>
 *			  <span> 份數 </span>
 *		</div>
 * </div>
 */
 
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
 
function showSetBtnWithDetail(sets) {
	
	/*
	 *	動態創建一個新的div
	 *  用來掛載(append)之後產生的button, div
	 */
	var div = $('<div id="div1"> </div>');
	
	
	for (var i = 0; i < sets.length; i++) {
		// 建一個button
		var btn = document.createElement("input");
// 		var btn = $('<input type="button"></input>');
		btn.setAttribute("id", "btnSet");
		btn.setAttribute("type", "button");
		
		// 建一個div
		var dscpDiv = document.createElement("div");

		for (var key in sets[i]) {
			console.log(key);
			/*
			 * sets[0] --> {"納帕套餐":{"前菜沙拉":1}}
			 * key --> 納帕套餐
			 * 將key放進button中(value屬性)
			 */
			btn.setAttribute("value", key);
			var set = sets[i];
			/*
			 *	取出{"前菜沙拉":1}的key(前菜沙拉)及value(份數)
			 *  放進span中
			 */
			for (var keyOfkey in set[key]) {
				/*
				 * set[key] --> {"前菜沙拉":1}
				 * keyOfkey --> 前菜沙拉
				 * set[key][keyOfkey] --> 1
				 */
// 				$(dscpDiv).append($(keyOfkey));
// 				$(dscpDiv).append($(set[key][keyOfkey]));
				$(dscpDiv).append($('<span>'+keyOfkey+'  </span>'));
				$(dscpDiv).append($('<span>'+set[key][keyOfkey]+'份     </span>'));
				console.log(keyOfkey);
				console.log(set[key][keyOfkey]);
			}
		}
		
// 		btn.addEventListener("mouseover", function() {
// 				$(this).css("background", "#44C3B6");
// 			});
// 		btn.addEventListener("mouseout", function() {
// 				$(this).css("background", "#3BB0A4");
// 			});

		$(div).append(btn).append($('<br>'));
		$(div).append(dscpDiv);
	}
	
	$('#btnArea').append(div);
			
}


// function showSetBtn(set) {
// 	var form = $('<form id="form1" action=""> </form>');
// // 	for (var key in set[0]) {
// // 		console.log(key);
// // 		console.log(set[key].name);
// // 	}
// 	for (var i = 0; i < set.length; i++) {
// 		for (var key in set[i]) {
// 			console.log(key);
// 		}
// 		console.log(set[i].name);
// 		// 建立button
		
// 		var btn = document.createElement("input");
// // 		var btn = $('<input type="button"></input>');
// 		btn.setAttribute("id", "btnSet");
// 		btn.setAttribute("type", "button");
// 		btn.setAttribute("value", set[i].name);
// // 		btn.addEventListener("mouseover", function() {
// // 				$(this).css("background", "#44C3B6");
// // 			});
// // 		btn.addEventListener("mouseout", function() {
// // 				$(this).css("background", "#3BB0A4");
// // 			});
	
// 		$(form).append(btn).append($('<br>'));
// 	}
	
// 	$('#btnArea').append(form);
			
// }
</script>
</head>
<body>
<%-- 	<a href="${pageContext.request.contextPath }/MainPage.jsp?page=order2">test2.jsp</a> --%>
	<div id="mainArea">
<!-- 		<div id="btnArea"> -->
<!-- 			<input class="page1" id="setBtn" type="button" value="點套餐"> <br> -->
<!-- 			<input class="page1" id="singleBtn" type="button" value="點單點"> -->
<!-- 		</div> -->
	</div>
</body>
</html>
