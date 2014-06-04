<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 說明 : 此 mainpage.jsp 即是一個樣版 整份複製後 修改檔案名稱
		      最後 將你要做的功能以及介面 都寫在 article -->
<!-- 所有的 "路徑" 都必須加上  ＜c:url＞ 方法 所以掛載 JSTL 是必要的 (勿刪) -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<link rel="shortcut icon" href="<c:url value="/images/BDYLogoHeadIcon.png"/>" />
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
<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui.css"/>">
<!-- 必要的 Script 與 CSS 外掛  (以上)-->
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以下)-->
<script src="<c:url value="/js/mainpage.js"/>"></script>
<script src="<c:url value="/js/setmeal.js"/>"></script>
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以上)-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 詳細說明2 : 把 Welcome 改成你個功能名稱  請使用"English"不知道怎麼取可以請教 ［Kevin］ -->
<style type="text/css">
.setCol{
text-align:center;	
float:left;
width: 15%;
height:100%;
}
.setColMid{
text-align:center;	
float:left;
width: 50%;
height:100%;
}

</style>
<title>BDY RMS - SetMeal</title>
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
	<div id="top" style="height: 300px;">
	<form action="<c:url value='/setmeal/setInsert'/>" method="post">
	<div class="setCol">
	<h3>套餐名稱</h3>
	<input id="setName" name="setName" type="text">
	</div>
	<div class="setColMid">
	<h3>選擇套餐種類</h3>
		<div id="format">
		<s:iterator var="fk" value="foodKind">
		<s:if test="%{#fk.isMain==0}">		
		<input class="checkbox"  type="checkbox" name="checkname" value="<s:property value="#fk.fkId" />" id="check<s:property value="#fk.fkId" />" onclick="showMe(<s:property value="#fk.fkId" />,'span<s:property value="#fk.fkId" />', this)" >
		<label ><s:property value="#fk.name" /></label>
		</s:if>
		</s:iterator></div>
		<div id="fkindChecked"></div>	
		<h4>限定價錢總合:<span id="totalPrice">0</span></h4>
		
	</div>
	
	<div id="divPrice" class="setCol">
	<s:iterator var="fk" value="foodKind">
		<div style="display: none;width:80%;hieght:150px;" id="span<s:property value="#fk.fkId" />">
		<s:property value="#fk.name" />-限定價錢:<input name="price<s:property value="#fk.fkId"/>" onblur="getTotalPrice()" size="2" type="text" id="text<s:property value="#fk.fkId"/>" fakeID="textPrice" >
		</div>
		</s:iterator>
	</div>
	<div class="setCol">
	<h3>套餐價錢:<input name="setPriceName" id="setPrice" type="text" size="3"></h3>
	<div id="changebtn"><input class='MainBtnColor' type="submit" name='buttonName' value="確定" onclick="return checkPrice()"></div>
	</div>
	
	</form>
	</div>
	<div id="showSetMeal">
		
		<c:forEach var="set" items="${set}">
		<div id="setId${set.setId }" cssId="accordion">
		<h4><span id="setname${set.setId }">${set.name}</span> -----<span id="setprice${set.setId }">${set.price }</span></h4>
		<div><c:forEach var="setdetails" items="${set.bdySetdetails }">
		<c:if test="${setdetails.bdyFoodkind.fkId != '6'}">
		<span id="detailname" detailfkid="${setdetails.bdyFoodkind.fkId }" detailid="${setdetails.sdId }">${setdetails.bdyFoodkind.name }</span>
		<span id="settext${setdetails.sdId }">${setdetails.price }</span>
		</c:if>
		</c:forEach><input class='MainBtnColor' type="button"  value="修改" onclick="updateSet(${set.setId})"><input class='MainBtnColor' type="button" value="刪除" onclick="deleteSet(${set.setId})"><br>
		</div></div>
		</c:forEach>
		
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