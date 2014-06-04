<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 說明 : 此 mainpage.jsp 即是一個樣版 整份複製後 修改檔案名稱
		      最後 將你要做的功能以及介面 都寫在 article -->
<!-- 所有的 "路徑" 都必須加上  ＜c:url＞ 方法 所以掛載 JSTL 是必要的 (勿刪) -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
.sort{
text-decoration: underline;
cursor: pointer;
}
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


.DataTables_sort_icon{
	display:none;
}

</style>
<script type="text/javascript">
// $(function(){
// 	window.onload = function(){
// 		insertOption();
// 		insertSetOption();
// 	}
// })

var contextPath='<%=request.getContextPath()%>';
var pags = "${pags}";
var order='${param.sort}';
var del='${param.del}';
var judgeSet = "<s:property value="%{fieldErrors.foodname[0]}"/>"+"<s:property value="%{fieldErrors.foodPrice[0]}"/>"+"<s:property value="%{fieldErrors.foodQTY[0]}"/>";
var judgeFoodKind = "<s:property value="%{fieldErrors.fkName[0]}"/>"+"<s:property value="%{fieldErrors.fkPeriod[0]}"/>"+"<s:property value="%{fieldErrors.fkSEQ[0]}"/>";
var judgeSetDetail = "<s:property value="%{fieldErrors.setdPrice[0]}"/>";
var booleanFood=false;
var booleanFoodKind=false;
var booleanSetDetail=false;
if(judgeSet!=""){
	booleanFood=true;
}
if(judgeFoodKind!=""){
	booleanFoodKind=true;
}
if(judgeSetDetail!=""){
	booleanSetDetail=true;
}

</script>
<script src="<c:url value="/js/jquery.js"/>"></script>
<script src="<c:url value="/js/jquery-ui.js"/>"></script>
<script src="<c:url value="/js/main.js"/>"></script>
<script src="<c:url value="/js/jquery.dataTables.min.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables_themeroller.min.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.dataTables.min.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/main.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui.css"/>">
<!-- 必要的 Script 與 CSS 外掛  (以上)-->
<!-- 根據 自己的功能 增加的 Script 與 CSS 外掛  (以下)-->
<script src="<c:url value="/js/managefood.js"/>"></script>
<script src="<c:url value="/js/manageset.js"/>"></script>
<script src="<c:url value="/js/managefoodkind.js"/>"></script>

<script src="<c:url value="/js/orderinitial.js"/>"></script>
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
    <li><a href="#tabs-2"  id="modifypackage">套餐組合</a></li>
    <li><a href="#tabs-3"  id="modifycount">修改食物類別</a></li>

  </ul>
  		<div id="tabs-1">
		<div id="sortTable1Header">共${fn:length(resultFood)}筆 
		<a href="javascript:void(0)" id="foodDialog-link" class="ui-state-default ui-corner-all" ">新增一筆資料</a>
		</div>
		
		<div id="foodInsertDialog" title="新增食物" style="display:none">
		<p>食物名稱:<br><input type="text" id="insertFoodName"><s:property value="%{fieldErrors.foodname[0]}"/></p>
		<p>食物價錢:<br><input type="text" id="insertFoodPrice" name="foodPrice"><s:property value="%{fieldErrors.foodPrice[0]}"/></p>
		<p>庫存量    :<br><input type="text" id="insertFoodQTY"><s:property value="%{fieldErrors.foodQTY[0]}"/></p>
		<p>說明:<br><input type="text" id="insertFoodDiscript"></p>
		<p>種類:<span id="insertFoodKind"></span>
		<span id="divMK" style="visibility:hidden;">主餐種類:<span id="insertMK"></span></span></p>
		</div>
		
		<table id="sortTable1" border="1">
		<thead>
		<tr>
		<th>食物名稱</th>
		<th><div class="sort" onclick="goURL('<c:url value="/secure/sort?act=sort&type=price" />')">食物價錢</div></th>
		<th><div class="sort" onclick="goURL('<c:url value="/secure/sort?act=sort&type=qty" />')">庫存量</div></th>
		<th>說明</th>
		<th>種類</th>
		<th>主餐種類</th>
		<th>功能</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="food" items="${resultFood }">
		
		<form action="<c:url value='/secure/Delete'/>" method="post">
		<c:if test="${food.fdId==param.fdid }">
		<tr style="background-color: firebrick " id="TRfood${food.fdId}">
		</c:if>
		<c:if test="${food.fdId!=param.fdid }">
		<tr id="TRfood${food.fdId}">
		</c:if>
 		<input type="hidden" value="${food.fdId }" name="fid"/> 
		
		<c:if test="${food.fdId!=param.fid }">
		<td id="fname${food.fdId}">${food.name}</td>
		<td id="fprice${food.fdId}">${food.price}</td>
		<td id="fqty${food.fdId}">${food.qty}</td>
		<td id="fdesc${food.fdId}">${food.descript}</td>
		<td id="ffkind${food.fdId}"><div id="foodk${food.fdId}">${food.bdyFoodkind.name}</div></td>
		<td id="fmk${food.fdId}"><div id="foodmk${food.fdId}">${food.bdyMainkind.name}</div></td>
		<td id="foodbtn${food.fdId}">
		<c:if test="${empty food.bdyMainkind.mkId}">
			<input class='MainBtnColor' type="button"   value="修改" onclick="fupdate(${food.fdId},${food.bdyFoodkind.fkId},0)">
		</c:if>
		<c:if test="${not empty food.bdyMainkind.mkId}">
		 	<input class='MainBtnColor' type="button"   value="修改" onclick="fupdate(${food.fdId},${food.bdyFoodkind.fkId},${food.bdyMainkind.mkId})">
		</c:if>
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
<!-- 		 <a href="javascript:void(0)" id="setDialog-link" class="ui-state-default ui-corner-all" ">新增一筆套餐品項</a> -->
<%-- 		 <a href="<c:url value='/setmeal/setIndex.jsp'/>" class="ui-state-default ui-corner-all" ">新增一筆套餐</a> --%>
		 <div id="setInsertDialog" title="新增套餐品項" style="display:none">
		 <p>套餐名稱:<br><span id="insertSetName"></span></p>
		 <p>食物類別:<br><span id="insertSetFoodKind"></span></p>
		 <p>限定價錢:<input size="3" type="text" id="setDetailPrice"><s:property value="%{fieldErrors.setdPrice[0]}"/></p>
		 </div>
		<table id="sortTable2" style="width:50%; align:left" border="1">		
		<thead>
		<tr>
		<th>套餐名稱</th>
		<th>食物類別</th>
		<th>套餐類別限定價錢</th>
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
		${detail.price}
<%-- 		<input class='MainBtnColor' type="button" value="修改" onclick="setoption(${detail.sdId },${detail.bdySet.setId},${detail.bdyFoodkind.fkId})"> --%>
<%-- 		<input class='MainBtnColor' type="button" value="刪除" onclick="setdelete(${detail.sdId })"> --%>
		</td>
		</tr>
		</form>
		</c:forEach>
		</tbody>
		</table>
		</div>
		
		<div id="tabs-3">
		<a href="javascript:void(0)" id="foodKindDialog-link" class="ui-state-default ui-corner-all" ">新增一樣類別</a>
		<div id="foodKindInsertDialog" title="新增類別" style="display:none">
		<p>類別名稱:<br><input type="text" id="insertFoodKindName"><s:property value="%{fieldErrors.fkName[0]}"/></p>
		<p>製作時間:<br><input type="text" size="2" id="insertFoodKindPeriod"><s:property value="%{fieldErrors.fkPeriod[0]}"/></p>
		<p>製作區域:<br><span id="insertSetFoodKindMa"></span></p>
		<p>出餐順序:<br><input type="text" size="2" id="insertFoodKindSEQ"><s:property value="%{fieldErrors.fkSEQ[0]}"/></p>
		</div>
		<table id="sortTable3" border="1">
		
		<thead>
		<tr>
		<th>類別名稱</th>
		<th>製作時間</th>
		<th>製作區域</th>
		<th>出餐順序</th>
		<th>功能</th>
		</tr>
		</thead>
		<c:forEach var = "foodkind" items="${resultfoodkind }">
		<c:if test="${foodkind.isMain!='1'}">		
		<tr id="TRfk${foodkind.fkId }">
		<td id="foodkindname${foodkind.fkId }">${foodkind.name }</td>
		<td id="foodkindperiod${foodkind.fkId }">${foodkind.period }</td>
		<td id="maId${foodkind.fkId }">${foodkind.bdyMakearea.name }</td>
		<td id="foodkindseq${foodkind.fkId }">${foodkind.seq }</td>
		<td id="">
		<input class='MainBtnColor' type="button" value="修改" onclick="fkupdate(${foodkind.fkId },${foodkind.bdyMakearea.maId })">
		<input class='MainBtnColor' type="button" value="刪除" onclick="fkdelete(${foodkind.fkId })">
		</td>
		</tr>
		</c:if>
		</c:forEach>			
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