$(function(){
	$("a").button();
	
	$('#table1').DataTable({
	    "jQueryUI": true,
	    "scrollY": ($('#aside').height()-275),
	    "scrollCollapse": true,
	    "paging": false,
	    "aoColumnDefs":[{
	    	bSortable:false
	    }],
	});
	
	$('#table2').DataTable({
	    "jQueryUI": true,
	    "scrollY": ($('#aside').height()-275),
	    "scrollCollapse": true,
	    "paging": false,
	    "aoColumnDefs":[{
	    	bSortable:false
	    }],
	});
	
	$('#table3').DataTable({
	    "jQueryUI": true,
	    "scrollY": ($('#aside').height()-275),
	    "scrollCollapse": true,
	    "paging": false,
	    "aoColumnDefs":[{
	    	bSortable:false
	    }],
	});
	
	$('#table4').DataTable({
	    "jQueryUI": true,
	    "scrollY": ($('#aside').height()-275),
	    "scrollCollapse": true,
	    "paging": false,
	    "aoColumnDefs":[{
	    	bSortable:false
	    }],
	});
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
function updateMA(maId){
	var str = $('#maTD'+maId).text();
	$('#maTD'+maId).html("<input size='10' type='text' value='"+str+"'>");
	$('#mabtn'+maId).html("<input class='MainBtnColor' type='button' value='確認' onclick='confirmMA("+maId+")'>" +
						  "<input class='MainBtnColor' type='button' value='取消' onclick='cancelMA("+maId+",\""+str+"\")'>");
}

function cancelMA(maId,text){
	$('#maTD'+maId).text(text);
	$('#mabtn'+maId).html("<input class='MainBtnColor' type='button' value='修改' onclick='updateMA("+maId+")'>" +
						  "<input class='MainBtnColor' type='button' value='刪除' onclick='deleteMA("+maId+")'>");
}
function confirmMA(maId){
	var str = $('#maTD'+maId+'>input').val();
	window.location.href = contextPath+"/secure/updateMK?maId="+maId+"&name="+str;
}










