$(function(){
	$("a").button();
})

function cancelDis(){
	document.getElementsByName("disName")[0].value="";
	document.getElementsByName("disNum")[0].value="";	
}
function deleteDis(disId){
	var b = window.confirm("確定刪除");
	if(b){
	window.location.href= contextPath+"/secure/deleteDis?disId="+disId;
	}
}
function cancelSet(){
	document.getElementsByName("setName")[0].value="";
	document.getElementsByName("setPrice")[0].value="";
}

function deleteSet(setId){
	var b = window.confirm("確定刪除");
	if(b){
	window.location.href= contextPath+"/secure/deleteSet?setId="+setId;
	}
}

function cancelMA(){
	document.getElementsByName("maName")[0].value="";
}

function deleteMA(maId){
	var b = window.confirm("確定刪除");
	if(b){
	window.location.href= contextPath+"/secure/deleteMA?maId="+maId;
	}
}
function cancelMK(){
	document.getElementsByName("mkName")[0].value="";
}

function deleteMK(mkId){
	var b = window.confirm("確定刪除");
	if(b){
	window.location.href= contextPath+"/secure/deleteMK?mkId="+mkId;
	}
}












