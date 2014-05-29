<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 說明 : 此 mainpage.jsp 即是一個樣版 整份複製後 修改檔案名稱
		      最後 將你要做的功能以及介面 都寫在 article -->
<!-- 所有的 "路徑" 都必須加上  ＜c:url＞ 方法 所以掛載 JSTL 是必要的 (勿刪) -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/checkOutDetail.css"/>">
<!-- 必要的 Script 與 CSS 外掛  (以上)-->
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以下)-->
<script src="<c:url value="/js/mainpage.js"/>"></script>
<script src="<c:url value="/js/checkOutDetail.js"/>"></script>
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以上)-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 詳細說明2 : 把 Welcome 改成你個功能名稱  請使用"English"不知道怎麼取可以請教 ［Kevin］ -->
<title>BDY RMS - checkOutDetail</title>
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

	<h3>第${table.name}號桌</h3>
	<div id="writeCodeInThisDiv">		
<fieldset style="width:70%">
	<legend >結帳明細</legend>
<form action="<c:url value="" />" method="post">		
<div id="tabs">
  <ul>
  <c:forEach var="map" items="${checkout.setDetailMap}">
    <li><a href="#tabs${map.key.setId}">${map.key.name}-數量${fn:length(map.value['0'])}</a></li>
   </c:forEach>
   <li><a href="#tabs-single">單點</a></li>
  </ul>
  <c:forEach var="map" items="${checkout.setDetailMap}">
  	<div id="tabs${map.key.setId}">
    	<h4>套餐價錢${map.key.price}</h4> 
	     	<c:forEach var="mainfood" items="${map.value['0']}" >
	     	<span>主餐:${mainfood.name}-價錢${mainfood.price}</span><br>
	     	</c:forEach>
	     	<hr>
	     	<c:forEach var="notMianFood" items="${map.value['1']}" >
	     	<c:choose>
	     		<c:when test="${notMianFood.addmoney == 0.0}">
	     			<span>餐點品項:${notMianFood.bdyFood.bdyFoodkind.name}-${notMianFood.bdyFood.name}</span><br>
	     		</c:when>
	     		<c:otherwise>
	     		<span>餐點品項:${notMianFood.bdyFood.bdyFoodkind.name}-${notMianFood.bdyFood.name}-(補差額:${notMianFood.addmoney})</span><br>
	     		</c:otherwise>
	     	</c:choose>
	     		
	     	</c:forEach> 
 	 </div>
  </c:forEach>
  <div id="tabs-single">
  	<h4>單點</h4>
  	<c:forEach var="orders" items="${checkout.orders}">
			<c:forEach var="orderlist" items="${orders.bdyOrderlists}">			
				<c:if test="${empty orderlist.bdySet}">
					餐點:${orderlist.bdyFood.name}-價錢:${orderlist.bdyFood.price}<br>
				</c:if>
				
			</c:forEach>
		</c:forEach>	
  </div>	
</div>
<label  id="disText">優惠方案:</label ><s:select
			headerKey="-1"
			headerValue="選擇折扣"
 			value="dis"  
 			name="dis"
 			id="dis" 
			listKey="%{disId}" 
			listValue="%{name}" 
			list="discounts"
			onchange="getDisCount(this.value)"
			/><span id="disShow"></span><br>
		
應收:<input type="hidden" id="price" value="${checkout.price}"><span id="showprice">${checkout.price}</span><br>	
<input type="button" class="MainBtnColor" value="結帳" id="checkout">
<input type="button" class="MainBtnColor" value="+" onclick="showDiscription()">
<label  name="realpriceText">扣除金額</label ><input type="text" name="realprice" value="" onblur="calculatePrice()">
<label  name="discriptionText">備註</label >
<select id="discription" name="discriptionSelect">
<option value="none">請選擇</option>
<option value="招待">招待</option>
<option value="貴賓">貴賓</option>
<option value="其他">其他</option>
</select>
	</form>
</fieldset>		
<script>
	$('#checkout').click(function(){
		checkout();
	});
	
	function checkout(){
		var finalPrice=parseFloat($('#showprice').html());
 		var disId=document.getElementById("dis").options[document.getElementById("dis").selectedIndex].value;
 		discription = document.getElementById("discription").options[document.getElementById("discription").selectedIndex].value;
 		window.location=contextPath+"/checkout/checkBill.action?disId="+disId+"&finalPrice="+finalPrice+"&discription="+discription;
		}
</script>
		
		
	</div><!-- 	id="writeCodeInThisDiv" -->
</div>
</div>
<div id="footer">
<jsp:include page="/mainpage/footer.jsp" />
</div>
</div>
</body>
</html>