<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="functionTXT">功能選單</div>
<div><img id="functionLogo" src="<c:url value="/images/function.jpg"/>"></div>
<input class="MenuBtn2" type="button" value="訂位"><br>
<input class="MenuBtn2" type="button" value="劃位"><br>
<input class="MenuBtn2" type="button" value="結帳"><br>
<input class="MenuBtn2" type="button" value="點餐" onclick="goURL('<c:url value="/order/ordermainnewstyle.jsp" />')"><br>
<input class="MenuBtn"  type="button" value="餐桌擺設" onclick="goURL('<c:url value="/table/tableset.jsp" />')"><br>
<input class="MenuBtn2" type="BUTTON" value="出餐" onclick="goURL('<c:url value="/kitchen/kitchenView.action"/>')"><BR>
<input class="MenuBtn2" type="BUTTON" value="報表" onclick="goURL('<c:url value="/report/reportMenu.jsp" />')"><BR>
<input class="MenuBtn2" type="BUTTON" value="員工管理" onclick="goURL('<c:url value="/secure/modifyEmp.jsp" />')"><BR>
<input class="MenuBtn2" type="BUTTON" value="餐廳管理" onclick="goURL('<c:url value="/secure/food?act=show" />')"><BR>
<input class="MenuBtn"  type="button" value="登出" onclick="goURL('<c:url value="/index.jsp" />')">