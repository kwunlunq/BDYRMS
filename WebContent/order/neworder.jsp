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
<title>BDY RMS - Welcome</title>
<script>
$(function(){
	$( "#orderarea" ).tabs();
	$( "#orderlist" ).tabs();
	$("#ChooseSetDialog").dialog({
		autoOpen: false,
		modal:true,
		width: 200,
	      show: {
	        effect: "clip",
	        duration: 800
	      },
	      hide: {
	        effect: "clip",
	        duration: 800
	      }
	});
	
	$('#orderarea').on('click','input',function(){
		var newOLBtn = $("<input class='MainBtnColor' type='button'>");
		newOLBtn.attr("fdId",$(this).attr("fdId"));
		newOLBtn.attr("isMain",$(this).attr("isMain"));
		newOLBtn.attr("value",$(this).val());
		$('#orderlist-1').append(newOLBtn);
	});
	
	$('#orderlist-1').on('click','input',function(){
		if($(this).attr("isMain") == 'true'){
			$( "#ChooseSetDialog" ).dialog( "open" );
		}else{
			alert("這不是主餐~這不是主餐");
		}
	});
});

function addOrderAreaBtn(foodTag,fdId,isMain,foodName){
	var newOABtn = $("<input class='MainBtnColor' type='button'>");
	newOABtn.attr("fdId",fId);
	newOABtn.attr("isMain",isMain);
	newOABtn.attr("value",foodName);
	$('#'+foodTag).append(newOABtn);
}
</script>
<style>
#ChooseSetDialog{
	text-align:center;
}
#ChooseSetDialog input{
	width:100%;
}
#orderarea input,#orderlist input{
	margin:2px 2px 2px 2px;
	width:19%;
}
#orderarea{
	width:95%;
	height:38%;
	margin:0px auto;
}
#orderlist{
	width:95%;
	height:58%;
	margin:0px auto;
}
</style>
</head>
<body>
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
	<div id="orderarea">
		<ul>
			<li><a href="#orderarea-1">牛排</a></li>
			<li><a href="#orderarea-2">披薩</a></li>
			<li><a href="#orderarea-3">飲料</a></li>
			<li><a href="#orderarea-4">甜點</a></li>
		</ul>
		<div id="orderarea-1">
			<input class="MainBtnColor" type="button" fdId="1" isMain="true" value="牛小排">
			<input class="MainBtnColor" type="button" fdId="2" isMain="true" value="豬大排">
			<input class="MainBtnColor" type="button" fdId="3" isMain="true" value="肋眼牛排">
			<input class="MainBtnColor" type="button" fdId="4" isMain="true" value="肋嘴牛排">
			<input class="MainBtnColor" type="button" fdId="5" isMain="true" value="肋耳牛排">
			<input class="MainBtnColor" type="button" fdId="6" isMain="true" value="肋手牛排">
		</div>
		<div id="orderarea-2">
			<input class="MainBtnColor" type="button" fdId="7" isMain="false" value="夏威夷披薩">
			<input class="MainBtnColor" type="button" fdId="8" isMain="false" value="海鮮披薩">
			<input class="MainBtnColor" type="button" fdId="9" isMain="false" value="章魚燒披薩">
		</div>
		<div id="orderarea-3">
			<input class="MainBtnColor" type="button" fdId="10" isMain="false" value="水果茶">
			<input class="MainBtnColor" type="button" fdId="11" isMain="false" value="柳橙汁">
			<input class="MainBtnColor" type="button" fdId="12" isMain="false" value="酸梅汁">
		</div>
		<div id="orderarea-4">
			<input class="MainBtnColor" type="button" fdId="13" isMain="false" value="提拉米蘇">
			<input class="MainBtnColor" type="button" fdId="14" isMain="false" value="千層派">
			<input class="MainBtnColor" type="button" fdId="15" isMain="false" value="黑糖麻糬">
		</div>
		<div id="orderarea-3"></div>
	</div>
	<div id="orderlist">
		<ul>
			<li><a href="#orderlist-1">單點</a></li>
		</ul>
		<div id="orderlist-1">
		</div>
		<div id="orderlist-2">
		</div>
		<div id="orderlist-3">
		</div>
	</div>
	
	
	<div id="ChooseSetDialog" title="選擇套餐">
		<input class="MainBtnColor" type="button" value="很怕套餐">
		<input class="MainBtnColor" type="button" value="螺絲套餐">
		<input class="MainBtnColor" type="button" value="榔頭套餐">
	</div>
	</div><!-- 	id="writeCodeInThisDiv" -->
</div>
</div>
<div id="footer">
<jsp:include page="/mainpage/footer.jsp" />
</div>
</div>
</body>
</html>