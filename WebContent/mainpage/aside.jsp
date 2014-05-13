<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<p><span class="topItem">:工作人員:</span></p>
<p><span class="topItem">${empData.name}</span></p>
<div><img id="functionLogo" src="<c:url value="/images/function.jpg"/>"></div>
<div id="functionTXT">功能選單</div>
<input class="MainBtnColor asideBtn" type="button" value="公告" onclick="goURL('<c:url value="/mainpage.jsp" />')"><br>
<input class="MainBtnColor asideBtn" type="button" value="訂位"><br>
<input class="MainBtnColor asideBtn" type="button" value="現場動態"><br>
<input class="MainBtnColor asideBtn" type="button" value="結帳"><br>
<input class="MainBtnColor asideBtn" type="button" value="點餐" onclick="goURL('<c:url value="/order/order.jsp" />')"><br>
<input class="MainBtnColor asideBtn" type="button" value="餐桌擺設" onclick="goURL('<c:url value="/table/tableset.jsp" />')"><br>
<input class="MainBtnColor asideBtn" type="button" value="出餐" onclick="goURL('<c:url value="/kitchen/kitchenView.action"/>')"><BR>
<input class="MainBtnColor asideBtn" type="button" value="報表" onclick="goURL('<c:url value="/report/reportmenu.jsp" />')"><BR>
<input class="MainBtnColor asideBtn" type="button" value="員工管理" onclick="goURL('<c:url value="/secure/modifyEmp.jsp" />')"><BR>
<input class="MainBtnColor asideBtn" type="button" value="餐廳管理" onclick="goURL('<c:url value="/secure/ManageServlet?act=show" />')"><BR>
<input class="MainBtnColor asideBtn" type="button" value="登出" onclick="goURL('<c:url value="/index.jsp" />')">