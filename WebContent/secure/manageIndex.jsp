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
<style>
#tabs{
		
		width:98%;
		padding-bottom:0px;
}
#top{
		/*margin-left:10%;*/ 
   		margin-top:10px;
   		margin-bottom:10px;
}
#tabs-1{
width:100%;height:90%;overflow:auto
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

</style>
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
<script src="<c:url value="/js/manage.js"/>"></script>
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

<div id="article">
<!-- START Write -->
<div id="tabs">
  <ul>
    <li><a href="#tabs-1"  id="modifyfood">修改食物</a></li>
    <li><a href="#tabs-2"  id="modifypackage">修改套餐</a></li>
    <li><a href="#tabs-3"  id="modifycount">修改折扣</a></li>
  </ul>
  		<div id="tabs-1">
		
		共${foodcount }筆 
		<button onclick="myFunction()">新增一筆資料</button>
	
		
		<table border="1">
		<thead>
		<tr>
		<th class="thstyle">食物名稱</th>
		<th class="point thstyle" id="foodPrice" onclick="goURL('<c:url value='/secure/ManageServlet?act=sort&type=price' />')">食物價錢</th>
		<th class="point thstyle" id="foodQty" onclick="goURL('<c:url value='/secure/ManageServlet?act=sort&type=qty' />')">庫存量</th>
		<th class="thstyle">說明</th>
		<th class="thstyle">種類</th>
		<th class="thstyle">功能</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="food" items="${resultFood }">
		
		<form action="<c:url value='/secure/ManageServlet?act=updatefood'/>" method="post">
		<tr id="TRfood${food.fdId}">
		<input type="hidden" value="${food.fdId }" name="fid">
<%-- 		<c:if test="${food.fdId==param.fid }"> --%>
<%-- 		<td><input class="textstyle" name="foodname" type="text" value="${food.name }"></td> --%>
<%-- 		<td><input class="textstyle" name="foodprice" type="text" value="${food.price }"></td> --%>
<%-- 		<td><input class="textstyle" name="foodqty" type="text" value="${food.qty }"></td> --%>
<%-- 		<td><input class="textstyle" name="fooddescount" type="text" value="${food.descript }"></td> --%>
<%-- 		<td><input class="textstyle" name="foodkind" type="text" value="${food.bdyFoodkind.name}"></td> --%>
<%-- 		<td><input class="textstyle" name="foodperiod" type="text" value="${food.bdyFoodkind.period}"></td> --%>
<!-- 		<td><input  type="submit"  name="btn"  value="完成" > -->
<!-- 		<input type="submit"  name="btn"  value="刪除"> -->
<!-- 		</td> -->
<%-- 		</c:if> --%>
		
		<c:if test="${food.fdId!=param.fid }">
		<td name="fname">${food.name }</td>
		<td name="fprice">${food.price }</td>
		<td name="fqty">${food.qty }</td>
		<td name="fdesc">${food.descript }</td>
		<td name="ffkind"><div id="foodk${food.fdId}">${food.bdyFoodkind.name}</div></td>
		<td>
		<input type="button" id="foodupdate" name="btn"  value="修改" onclick="fupdate(${food.fdId});">
		<input type="button" id="fooddelete" name="btn"  value="刪除" onclick="">
		</td>
		</c:if>
		</tr>
		</form>
		</c:forEach>
		</tbody>
		</table>
		
		</div>
		
		 <div id="tabs-2">
		 <input type="button" id="insert2" value="新增一筆資料">
		<table border="1">		
		<thead>
		<tr>
		<th>套餐名稱</th>
		<th>食物類別</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="detail" items="${resultDetail }">
		<tr>
		<td>${detail.bdySet.name}</td>
		<td>${detail.bdyFoodkind.name}</td>		
		<td><input type="button" id="update2" value="修改"></td>
		<td><input type="button" id="delete2" value="刪除"></td>
		</tr>
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
</div>
</body>
</html>