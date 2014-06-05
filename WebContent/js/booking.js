var bookingUrl = contextPath + "/booking";
var d = new Date();
var month = (d.getMonth() + 1);
if((d.getMonth() + 1) < 10){
	month = "0" + month;
}
var day = d.getDate();
if(d.getDate() < 10){
	day = "0" + day;
}
var today = d.getFullYear() + "-" + month + "-" + day;

$(function() {	
	setTableToMaxStyle("bookingTable");
	$.datepicker.setDefaults($.datepicker.regional["zh-TW"]);
	$( "#bookingDatePicker" ).datepicker({
		changeMonth: true,
		changeYear: true,
		dateFormat: "yy-mm-dd",
		showAnim:"slideDown",
		showButtonPanel: true
	});
	$('#addBookingBtn').click(showAddBookingDialog);	
	$('#floorSelect').change(function(){
		getTables($(this).val());
	});
	$('#assignTable1').click(function(){
		$('#tableChoose').css("display","block");
		$('#tbId').val($('#tableSelect').val());
	});
	$('#assignTable2').click(function(){
		$('#tableChoose').css("display","none");
		$('#tbId').val("-1");
	});
	$('#tableSelect').change(function(){
		$('#tableSelect').val();
	});
	$('#hour').empty();
	for(var i=8;i<=24;i++)
	{
		var hour = 0;
		if(i<10){
			hour = "0"+i;
		}else{
			hour = ""+i;
		}
		$('#hour').append("<option value='"+hour+"'>"+hour+"</option>");
	}
	$('#min').empty();
	for(var i=0;i<=59;i++)
	{
		var min = 0;
		if(i<10){
			min = "0"+i;
		}else{
			min = ""+i;
		}
		if(i % 5 == 0)
			$('#min').append("<option value='"+min+"'>"+min+"</option>");
	}
	$('#bookingDatePicker').val(today);
	loadBookingData(today);
	$('#bookingDatePicker').change(function(){
		loadBookingData($('#bookingDatePicker').val());
	});
    hideLoading();
});

function deleteBooking(btn){
	var deleteBookingJson = {
		"act" : "deleteBooking",
		"bkId": $(btn).attr("bkId"),
	};
	$.ajax({
		   url: bookingUrl,
		   type: 'POST',
		   data: JSON.stringify(deleteBookingJson),
		   contentType: 'application/json; charset=utf-8',
		   success: function() {
			   loadBookingData($('#bookingDatePicker').val());
			   showState("已刪除");
		   }
	});
}

function cancelBooking(btn){
	var editBookingJson = {
			"act" : "updateBookingState",
			"bkId": $(btn).attr("bkId"),
			"bkState" : $(btn).attr("bkState")
	};
	$.ajax({
	    url: bookingUrl,
	    type: 'POST',
	    data: JSON.stringify(editBookingJson),
	    contentType: 'application/json; charset=utf-8',
	    success: function() {
	    	loadBookingData($('#bookingDatePicker').val());
	    	showState("已"+$(btn).val());
	    	if($(btn).attr("bkState")==2){
	    		window.location = contextPath + "/table/opentable.jsp?f="+$(btn).attr("FloorId");
	    	}
	    }
	});
}

function loadBookingData(eatDate){
	var bookingJson = {
			"act" : "searchByDate",
			"date": eatDate
	};
	$.ajax({
	    url: bookingUrl,
	    type: 'POST',
	    data: JSON.stringify(bookingJson),
	    contentType: 'application/json; charset=utf-8',
	    dataType :'json',
	    success: function(bookingList) {
	    	$('#bookingTable tbody').empty();
	    	$('#bookingCount').text(bookingList.data.length);
	    	for(var i=0;i<bookingList.data.length;i++){
	    		var bookingData = bookingList.data[i];
	    		var eatDate = bookingData.eatDate.substring(10,16);
	    		var tbId = "";
	    		var tbName = "";
	    		var tbFloorName = "";
	    		var tbStr = "無";
	    		if(bookingData.tbId != -1){
	    			tbId = bookingData.tbId;
	    			tbName = bookingData.tbName;
	    			tbFloorName = bookingData.tbFloorName;
	    			tbStr = "("+tbFloorName+") "+tbName;
	    		}else{
	    			tbStr = "不指定";
	    		}
	    		var content = bookingData.content;
	    		if(bookingData.content.length < 1 || bookingData.content ==null || bookingData.content == ""){
	    			content = "無";
	    		}
	    		var cancelBtn = $("<input class='btnInTable' id='cancelBooking' bkState='1' bkId='"+bookingData.bkId+"' type='button' value='取消訂位'>");
	    		var comeBtn = $("<input class='btnInTable' id='comeBooking' floorId='"+bookingData.tbFloorId+"' bkState='2' bkId='"+bookingData.bkId+"' type='button' value='到場'>");
	    		var state = "等待";
	    		if(bookingData.state == 1){
	    			state = "取消";
	    			comeBtn = "";
	    			cancelBtn = $("<input class='btnInTable' id='cancelBooking' bkState='0' bkId='"+bookingData.bkId+"' type='button' value='復原訂位'>");
	    		}else if(bookingData.state == 2){
	    			state = "到場";
	    			comeBtn = "";
	    			cancelBtn = "";
	    		}
	    		var deleteBtn = $("<input class='btnInTable' id='deleteBooking' type='button' bkId='"+bookingData.bkId+"' value='刪除'>");
	    		var tr = $('<tr>');
	    		$(tr).append("<td>"+state+"</td>");
	    		$(tr).append("<td>"+eatDate+"</td>");
	    		$(tr).append("<td>"+bookingData.name+"</td>");
	    		$(tr).append("<td>"+bookingData.phone+"</td>");
	    		$(tr).append("<td>"+bookingData.custNum+"</td>");
	    		$(tr).append("<td>"+tbStr+"</td>");
	    		$(tr).append("<td>"+content+"</td>");
	    		var td = $("<td></td>");
	    		$(td).append(cancelBtn);
	    		$(td).append(comeBtn);
	    		$(td).append(deleteBtn);
	    		$(tr).append(td);
	    		$('#bookingTable tbody').append(tr);
	    	}
    		$("input[id=deleteBooking]").click(function(){
    			deleteBooking($(this));
    		});
    		$("input[id=cancelBooking]").click(function(){
    			cancelBooking($(this));
    		});
    		$("input[id=comeBooking]").click(function(){
    			cancelBooking($(this));
    		});
	    	setTableToMaxStyle("bookingTable");
	    }
	});
}

function showAddBookingDialog(){
	$( "#FormDatePicker" ).datepicker({
		changeMonth: true,
		changeYear: true,
		dateFormat: "yy-mm-dd",
		showAnim:"slideDown",
		showButtonPanel: true
	});
	$('#assignTable').buttonset();
	$("#addBooking").dialog({
		resizable:false,
		modal:true,
		position: { 
			my: "center center", 
			at: "center center", 
			of: window 
		},
		show: {
	        effect: "blind",
	        duration: 800
	      },
	    hide: {
	        effect: "blind",
	        duration: 500
	    },
	    buttons:{
	    	"取消":function(){
	    		$('#tableChoose').css("display","none");
	    		$("#addBookingForm")[0].reset();
	    		$("#addBooking").dialog('close');
	    	},
	    	"送出":function(){
	    		chcekAndSendFormData();
	    	}
	    }
	});
	getFloor();
}

var tableUrl = contextPath + "/table/tableset";

function getFloor(){
	var tablesDataForSaveInJson = {
			"act" : "init",
			"floor" : "-1",
			"tables" : [],
			"delTBlist" : [] 
	};
	$.ajax({
	    url: tableUrl,
	    type: 'POST',
	    data: JSON.stringify(tablesDataForSaveInJson),
	    contentType: 'application/json; charset=utf-8',
	    dataType :'json',
	    success: function(floorList) {
	    	$('#floorSelect').empty();
		    if(floorList != null && floorList != "" && floorList.length != 0){
		    	var firstFloor = 0;
				for(var i=0 ;i<floorList.length;i++){
					var floorData = floorList[i];
					if(i==0){
						firstFloor = floorData.floorId;
					}
					$('#floorSelect').append("<option value='"+floorData.floorId+"'>"+ floorData.floorName +"</option>");
				}
				getTables(firstFloor);
	    	}else{
	    		$('#floorSelect').append("<option value='-1'>無資料</option>");
	    	}
	    }
	});
}

function getTables(floor){
	var tablesDataForSaveInJson = {
			"act" : "load",
			"floor" : floor+"",
			"tables" : [],
			"delTBlist" : [] 
	};
	$.ajax({
	    url: tableUrl,
	    type: 'POST',
	    data: JSON.stringify(tablesDataForSaveInJson),
	    contentType: 'application/json; charset=utf-8',
	    dataType:'JSON',
	    success: function(tableList) {
	    	$('#tableSelect').empty();
	    	if(tableList != null && tableList != "" && tableList.length != 0){
	    		$('#tableSelect').append("<option value='-1'>不指定</option>");
		    	for(var i=0; i<tableList.length;i++){
					var tableData = tableList[i];
					var tbId = tableData.tbId;
					var tbName = tableData.tbName;
					$('#tableSelect').append("<option value='"+tbId+"'>"+ tbName +"</option>");
				}
	    	}else{
	    		$('#tableSelect').append("<option value='-1'>無資料</option>");
	    	}
	    }
	});
}

function chcekAndSendFormData(){
	var bookingData = {
			"act" : "insertBooking",
			"name" : "",
			"phone": "",
			"number" : "",
			"eatDate" : "",
			"hour" : "",
			"min" : "",
			"content": "",
			"tbId": "-1",
			"empId":""
	};
	var name = $("#addBookingForm input[name=name]").val();
	var phone = $("#addBookingForm input[name=phone]").val();
	var number = $("#addBookingForm input[name=number]").val();
	var eatDate = $("#addBookingForm input[name=eatDate]").val();
	var hour = $("#hour").val();
	var min = $("#min").val();
	var content = $("#addBookingForm input[name=content]").val();
	var tbId = $("#addBookingForm select[id=tableSelect]").val();
	var empId = $("#addBookingForm input[name=empId]").val();
	var send = true;
	if(name.length == 0 || name == null || name == "" || name.length > 4){
		send = false;
		$("#nameError").text("必填,小於5個字");
	}else{
		bookingData.name = name;
		$("#nameError").text("");
	}
	var expr = /09\d{8}/;
	if(phone.length == 0 || phone == null || phone == "" || phone.length != 10){
		send = false;
		$("#phoneError").text("必填,須輸入10個數字");
	}else if(!expr.test(phone)){
		send = false;
		$("#phoneError").text("格式錯誤-09XXXXXXXX");
	}else{
		bookingData.phone = phone;
		$("#phoneError").text("");
	}
	if(number.length == 0 || number == null || number == "" || isNaN(number)){
		send = false;
		$("#numberError").text("必填,須為數字");
	}else if(number < 1 || number > 999){
		send = false;
		$("#numberError").text("0<人數<1000");
	}else{
		bookingData.number = number;
		$("#numberError").text("");
	}
	if(eatDate.length == 0 || eatDate == null || eatDate == ""){
		send = false;
		$("#eatDateError").text("必填");
	}else{
		bookingData.eatDate = eatDate;
		$("#eatDateError").text("");
	}
	
	if(send){
		bookingData.hour = hour;
		bookingData.min = min;
		bookingData.empId = empId;
		bookingData.content = content;
		bookingData.tbId = tbId;
		console.log(JSON.stringify(bookingData));
		$.ajax({
		    url: bookingUrl,
		    type: 'POST',
		    data: JSON.stringify(bookingData),
		    contentType: 'application/json; charset=utf-8',
		    success: function() {
		    	showState("新增成功");
		    	loadBookingData(eatDate);
		    	$('#bookingDatePicker').val(eatDate);
		    	$("#addBooking").dialog('close');
		    	$("#addBookingForm")[0].reset();
		    }
		});
	}
}