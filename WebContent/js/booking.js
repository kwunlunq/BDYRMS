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
	$("table").on('click','editBooking',function(){
		editBooking($(this));
	});
	$("table").on('click','cancelBooking',function(){
		cancelBooking($(this));
	});
    hideLoading();
});

function editBooking(btn){
	
}

function cancelBooking(btn){
	
}

function loadBookingData(eatDate){
	var bookingJson = {
			"act" : "searchByDate",
			"date": eatDate
	};
	console.log(bookingJson);
	$.ajax({
	    url: bookingUrl,
	    type: 'POST',
	    data: JSON.stringify(bookingJson),
	    contentType: 'application/json; charset=utf-8',
	    dataType :'json',
	    success: function(bookingList) {
	    	$('#bookingTable tbody').empty();
	    	console.log(bookingList.data);
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
	    			tbStr = "無";
	    		}
	    		var content = bookingData.content;
	    		if(bookingData.content.length < 1 || bookingData.content ==null || bookingData.content == ""){
	    			content = "無";
	    		}
	    		var state = "正常";
	    		if(bookingData.state == 1){
	    			state = "已取消";
	    		}
	    		var editBtn = $("<input id='editBooking' type='button' class='MainBtnColor' value='編輯'>");
	    		var cancelBtn = $("<input id='cancelBooking' bookingId='"+bookingData.bkId+"' type='button' class='MainBtnColor' value='取消訂位'>");
	    		var tr = $('<tr>');
	    		$(tr).append("<td>"+state+"</td>");
	    		$(tr).append("<td>"+bookingData.name+"</td>");
	    		$(tr).append("<td>"+bookingData.phone+"</td>");
	    		$(tr).append("<td>"+bookingData.custNum+"</td>");
	    		$(tr).append("<td>"+eatDate+"</td>");
	    		$(tr).append("<td>"+tbStr+"</td>");
	    		$(tr).append("<td>"+content+"</td>");
	    		var td = $("<td></td>");
	    		$(td).append(editBtn);
	    		$(td).append(cancelBtn);
	    		$(tr).append(td);
	    		$('#bookingTable tbody').append(tr);
	    	}
	    	showState("資料載入完畢");
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
	var tbId = $("#addBookingForm input[name=tbId]").val();
	var empId = $("#addBookingForm input[name=empId]").val();
	var assignTable1 = $('#assignTable label[for=assignTable1]').attr("aria-pressed");
	var send = true;
	if(name.length == 0 || name == null || name == "" || name.length > 10){
		send = false;
		$("#nameError").text("必填,小於10個字");
	}else{
		bookingData.name = name;
		$("#nameError").text("");
	}
	var expr = /09\d{8}/;
	if(phone.length == 0 || phone == null || phone == "" || phone.length != 10){
		send = false;
		$("#phoneError").text("必填,須輸入10個數字");
	}else if(!expr.test(phone)){
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
	
	if(tbId == -1 && assignTable1 == true){
		send = false;
		$("#tableChooseError").text("指定座位失敗");
	}else{
		bookingData.tbId = tbId;
		$("#tableChooseError").text("");
	}
	
	if(send){
		bookingData.hour = hour;
		bookingData.min = min;
		bookingData.empId = empId;
		bookingData.content = content;
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
		    }
		});
	}
}