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
<style>

#top{
		/*margin-left:10%;*/ 
   		margin-top:10px;
   		margin-bottom:10px;
}
#top>input[type=button]{
width: 200px;
height: 80px;
font-size: 30px;
font-weight: bold;
background-color: paleturquoise;
border-radius: 10px;
}
.point{
cursor: pointer;
text-decoration: underline;
font-style: italic;
}
.textstyle{
width:95%;
}
.thstyle{
width:110px;
max-width:110px;

}
}

</style>
<script type="text/javascript">
var contextPath='<%=request.getContextPath()%>';
var pags = "${pags}";
var judge = "<s:property value="%{fieldErrors.foodname[0]}"/>"+"<s:property value="%{fieldErrors.foodPrice[0]}"/>";
var b=false;
if(judge!=""){
	b=true;
}
</script>
<script src="<c:url value="/js/jquery.js"/>"></script>
<script src="<c:url value="/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/js/main.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui.css"/>">
<!-- 必要的 Script 與 CSS 外掛  (以上)-->
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以下)-->
<script src="<c:url value="/js/managefood.js"/>"></script>
<script src="<c:url value="/js/manageset.js"/>"></script>
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以上)-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 詳細說明2 : 把 Welcome 改成你個功能名稱  請使用"English"不知道怎麼取可以請教 ［Kevin］ -->
<title>BDY RSM - Welcome</title>

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
<!-- START Write -->
<div id="tabs">
  <ul>
    <li><a href="#tabs-1"  id="modifyfood">修改食物</a></li>
    <li><a href="#tabs-2"  id="modifypackage">修改套餐</a></li>
    <li><a href="#tabs-3"  id="modifycount">修改折扣</a></li>
  </ul>
  		<div id="tabs-1">
		共${foodcount }筆 
		<a href="javascript:void(0)" id="foodDialog-link" class="ui-state-default ui-corner-all" ">新增一筆資料</a>
		<div id="foodInsertDialog" title="新增食物" style="display:none">
		<p>食物名稱:<br><input type="text" id="insertFoodName"><s:property value="%{fieldErrors.foodname[0]}"/></p>
		<p>食物價錢:<br><input type="text" id="insertFoodPrice" name="foodPrice"><s:property value="%{fieldErrors.foodPrice[0]}"/></p>
		<p>庫存量    :<br><input type="text" id="insertFoodQTY"><s:property value="%{fieldErrors.foodQTY[0]}"/></p>
		<p>說明:<br><input type="text" id="insertFoodDiscount"></p>
		<p>種類:<br><span id="insertFoodKind"></span></p>
		</div>
		
		<table width="100%" border="1">
		<thead>
		<tr>
		<th width="25%" class="thstyle">食物名稱</th>
		<th width="10%" class="point thstyle" id="foodPrice" onclick="goURL('<c:url value='/secure/sort?act=sort&type=price' />')">食物價錢</th>
		<th width="10%" class="point thstyle" id="foodQty" onclick="goURL('<c:url value='/secure/sort?act=sort&type=qty' />')">庫存量</th>
		<th width="30%" class="thstyle">說明</th>
		<th width="10%" class="thstyle">種類</th>
		<th width="15%" class="thstyle">功能</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="food" items="${resultFood }">
		
		<form action="<c:url value='/secure/Delete'/>" method="post">
		<tr id="TRfood${food.fdId}">
 		<input type="hidden" value="${food.fdId }" name="fid"/> 
<%-- 		<input type="hidden" value="${food.name }" name="fname"/> --%>
<%-- 		<input type="hidden" value="${food.price }" name="fprice"/> --%>
<%-- 		<input type="hidden" value="${food.qty }" name="fqty"/> --%>
<%-- 		<input type="hidden" value="${food.descript }" name="fdesc"/> --%>
<%-- 		<input type="hidden" value="${food.bdyFoodkind.fkId}" name="ffkind"/> --%>

		
		<c:if test="${food.fdId!=param.fid }">
		<td id="fname${food.fdId}">${food.name}</td>
		<td id="fprice${food.fdId}">${food.price}</td>
		<td id="fqty${food.fdId}">${food.qty}</td>
		<td id="fdesc${food.fdId}">${food.descript}</td>
		<td id="ffkind${food.fdId}"><div id="foodk${food.fdId}">${food.bdyFoodkind.name}</div></td>
		<td id="foodbtn${food.fdId}">
		<input class='MainBtnColor' type="button"   value="修改" onclick="fupdate(${food.fdId})">
		<input class='MainBtnColor' type="button"   value="刪除" onclick="fdeleteFood(${food.fdId})">
		</td>
		</c:if>
		</tr>
		</form>
		</c:forEach>
		</tbody>
		</table>
		
		</div>
		
		 <div id="tabs-2">
		 <input class='MainBtnColor' type="button"  value="新增一筆資料">
		<table border="1">		
		<thead>
		<tr>
		<th>套餐名稱</th>
		<th>食物類別</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="detail" items="${resultDetail}">
		<form>
		<tr id="TRdetail${detail.sdId }">
		<input type="hidden" name="sid" value="${detail.bdySet.setId}">
		<input type="hidden" name="fkid" value="${detail.bdyFoodkind.fkId}">
		
		<td><div id="sname${detail.sdId }">${detail.bdySet.name} </div> </td>
		<td><div id="fkname${detail.sdId }">${detail.bdyFoodkind.name}</td>		
		<td>
		
		<input class='MainBtnColor' type="button" value="修改" onclick="setoption(${detail.sdId },${detail.bdySet.setId},${detail.bdyFoodkind.fkId})">
		<input class='MainBtnColor' type="button" value="刪除" onclick="setdelete(${detail.sdId })">
		</td>
		</tr>
		</form>
		</c:forEach>
		</tbody>
		</table>
		</div>
		
		 
		 <div id="tabs-3">
		 <input type="button" id="insert3" value="新增一筆資料">
		 	<table border="1">
		<thead>
		<tr>
		<th>折扣名稱</th>
		<th>則扣優惠</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="discount" items="${resultdiscount }">
		<tr>
		<td>${discount.name}</td>
		<td>${discount.disPrice}</td>		
		<td><input type="button" id="update2" value="修改"></td>
		<td><input type="button" id="delete2" value="刪除"></td>
		</tr>
		</c:forEach>
		</tbody>
		</table>
		 </div>
 
	</div><!-- 	id="writeCodeInThisDiv" -->

</div>
</div>
<div id="footer">
<jsp:include page="/mainpage/footer.jsp" />
</div>

</body>
</html>