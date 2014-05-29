var setIds     = [];
var setdetails = [];
var fks        = [];
var tableoptions = [[]];
var setNames     = [];
var orderlistIndex = 0;
var setCount = 1;
// 要傳送的訂單所有資訊
var currentStatus = {"FId":null,  
					 "FName":null,
					 "TableId":null, 
					 "TableName":null, 
					 "CustNum":null, 
					 "EmpId":null, 
					 "Foods":[], 
					 };
$(function() {
	dwr.engine.setActiveReverseAjax(true);
	currentStatus.EmpId = empId;
	if (empId==null || empId=="") {
		alert("請先登入!");
		window.location.href=contextPath+"/index.jsp?backurl="+window.location.href;
	}
	getFoods();	// 取得所有食物
	getSets(); 	// 取得套餐資訊
	getFks(); 	// 取得fk資訊
	getTables();
	getEmp(empId);
	listenerInitial(); // 掛載listener
	readTableInfo();
	console.log(currentStatus);
	// 解決按鈕被腰斬問題
//	$( "#orderarea" ).tabs( "refresh" );
	// 解決IE緩存問題
	$.ajaxSetup({ cache: false });
});

function readTableInfo() {
	currentStatus.TableId = $("#tableNum").attr("tbid");
	currentStatus.TableName = $("#tableNum").text();

	currentStatus.FId = $("#floor").attr("floorid");
	currentStatus.FName = $("#floor").text();
	
	currentStatus.CustNum = $("#peopleCount").text();
}
function getCookie(cname) {
var name = cname + "=";
var ca = document.cookie.split(';');
for(var i=0; i<ca.length; i++) 
  {
  var c = ca[i].trim();
  if (c.indexOf(name)==0) return c.substring(name.length,c.length);
  }
return "";
}

function getEmp(empId) {
	var url = contextPath+"/order/getOrderDataServlet";
	$.getJSON(url, {"data":"emp", "empId":empId});
}
function listenerInitial() {
	// 視窗改變大小的時候重新調整按鈕大小
	$(window).resize(function() {
		$( "#orderlist" ).tabs( "refresh" );
		$( "#orderarea" ).tabs( "refresh" );
	});
	$('body').on('click','#num',function(){
		var textValue = $('#setNumberOfCust').val();
		if($(this).val() == "clear"){
			$('#setNumberOfCust').val("0");
		}else if($(this).val()=="back"){
			textValue = textValue.substring(0,textValue.length-1);
			$('#setNumberOfCust').val(textValue);
			if(textValue.length <= 0 || textValue=='0')
				$('#setNumberOfCust').val("0");
		}else{
			if(textValue == '0')
				textValue="";
			$('#setNumberOfCust').val(textValue+$(this).val());
		}
	});
	$('#finishDialog').dialog({
		autoOpen: false,
		modal:true,
		resizable: false,
	    width:650,
		buttons: [{
				text: "繼續點餐",
				click: function() {
					$( this ).dialog( "close" );
				}},
				{
				text: "送出",
				click: function() {
//					Service.update({value:"有人點餐"});
					Service.sendMes("新消息");
					sendOrder();
				}}]
	});
	$('#orderConfirm').click(function() {
			var peopleCount = $('#peopleCount').text();
			var tableNum = $('#tableNum').text();
			var setleft = $('span[id^=fkCountSpan-]').length;
			
			// 判斷桌號人數
			if (peopleCount=='-' || tableNum=="") {
				$('#ChooseTableAndPeopleDialog').dialog("open");
				showState("請輸入桌號及人數");
			} else if (peopleCount=='0') {
				$('#ChooseTableAndPeopleDialog').dialog("open");
				showState("請輸入人數");
			// 判斷套餐內容還有沒點的
			} else if (setleft != 0) {
				var divid = "";
					$.each($('span[id^=fkCountSpan-]'), function (index, obj) {
						$.each($(obj).parents('div[id^="orderlist-"]'), function (index2, obj2) {
							divid=$(obj2).attr("id");
							return false;
						});
						return false;
					});
				showState("套餐還有餐點未選");
				$("#orderlist").tabs( "option", "active", IdToActive(divid));
			// 判斷是否有點餐
			} else if (checkEmpty()) {
				showState("尚未點餐");
			} else {
				confirmClick();
			};
		}
	);
	$('#orderarea').on('click','input',function(){
		var canAdd = checkAddable($(this));
		if (canAdd) {
			addOrderAreaBtn("fkdiv-"+$(this).attr("fkId"),
							$(this).attr("fdId"),
							$(this).attr("isMain"),
							$(this).val(), 
							$(this).attr("fkId"),
							activeToId($("#orderlist").tabs("option", "active")));
			$( "#orderlist" ).tabs( "refresh" );
		}else{
			if($("#orderlist").tabs("option", "active") != 0)
				$(this).effect("highlight");
		}
	});

	$( "#orderlist" ).on("tabsactivate", function( event, ui ) {
  		orderlistIndex = $("#orderlist").tabs("option", "active");
  		
  	} );
	
	// 單點點餐, 變套餐
	$('#orderlist-0').on('click','input', orderlistClick);

	$("#ChooseSetDialog").dialog({
		autoOpen: false,
		modal:true,
		width: 200,
	      show: {
	        effect: "clip",
	        duration: 800
	      },
	      hide: {
	        effect: "clip",
	        duration: 800
	      }
	});
	
	$( "#orderlist" ).delegate( "span.ui-icon-close", "click", function(){
			deletSetTab($(this).closest( "li" ));
		});
	
	//選擇桌號與人數的Dialog
	$('body').on('click','#setTableAndPeople',function(){
		$('#ChooseTableAndPeopleDialog').dialog("open");
	});
	$("#ChooseTableAndPeopleDialog").dialog({
		autoOpen: false,
		modal:true,
		width: 300,
	    show: {
	        effect: "clip",
	        duration: 800
	    },
	    hide: {
	        effect: "clip",
	        duration: 800
	    },
		buttons: [{
				text: "取消",
				click: function() {
					$('#floor').text("-");
					$('#floor').attr("value","");
					$('#tableNum').text("-");
					$('#tableNum').attr("value","");
					$('#peopleCount').text("-");
					$('#peopleCount').attr("value","");
					$( this ).dialog( "close" );
				}},
				{
				text: "確定",
				click: function() {
					$('#floor').text($("#setFloor").find(":selected").text());
					$('#floor').attr("value",$("#setFloor").find(":selected").val());
					$('#tableNum').text($("#setTableNum").find(":selected").text());
					$('#tableNum').attr("value",$("#setTableNum").find(":selected").val());
					$('#peopleCount').text($("#setNumberOfCust").val());
					$('#peopleCount').attr("value",$("#setNumberOfCust").val());
					$( this ).dialog( "close" );
					
					currentStatus.FId = $("#setFloor").find(":selected").val();
					currentStatus.FName = $("#setFloor").find(":selected").text();
					currentStatus.TableId = $("#setTableNum").find(":selected").val();
					currentStatus.TableName = $("#setTableNum").find(":selected").text();
					currentStatus.CustNum = $("#setNumberOfCust").val();
				}}]
	});
}

function sendOrder() {
	var tableUrl = contextPath + "/table/tableset";
	var updateTable ={
			"act" : "tbOpen",
			"tbId" : currentStatus.TableId,
			"tbState" : 2,
			"custNum" : currentStatus.CustNum,
			"floor" : "-1"
	};
	$.ajax({
	    url: tableUrl,
	    type: 'POST',
	    data: JSON.stringify(updateTable),
//	    async: false,
	    contentType: 'application/json; charset=utf-8'
	});
	
	
	$.ajax({
	    url: contextPath+'/order/SendOrderServlet',
	    type: 'POST',
	    data: JSON.stringify(currentStatus),
	    contentType: 'application/json; charset=utf-8',
	    dataType: 'json',
	    async: false,
	    success: function(msg) {
	    	alert("123");
			showState(msg);
			showState("123");
	    }
	});
	showState("點餐單已送出");
	


	
	setTimeout(function(){
		window.location = contextPath+"/table/opentable.jsp?f="+currentStatus.FId;
	}, 800);
}

function cookieDecorder( str ) {
    return decodeURIComponent(str.replace(/\g\s/,' '));
}

function getTables() {
	var url = contextPath+"/order/getOrderDataServlet";
	$.getJSON(url, {"data":"table"}, function(result) {
		getTablesCallback(result);
	});
}

function getTablesCallback(result) {
	for (var i = 0; i < result.length; i++) {
		var fId = result[i].fId;
		var fName = result[i].fName;
		var option = document.createElement("option");
		$(option).attr("value", fId);
		$(option).attr("index", i);
		$(option).append(document.createTextNode(fName));
		$("#setFloor").append(option);
		
		// 將桌子資訊做成option存入tableoptions[[]]陣列中
		// 以便之後選擇樓層時取出
		var tbs = result[i].tables;
		tableoptions[i] = [];
		for (var j = 0; j < tbs.length; j++) {
			var tbName = tbs[j].tbName;
			var tbId = tbs[j].tbId;
			var option = document.createElement("option");
			$(option).attr("value", tbId);
			$(option).append(document.createTextNode(tbName));

			tableoptions[i][j] = option;
			if (i == 0) {
				$('#setTableNum').append(option);
			}
		};
	}

	$("#setFloor").change(function() {
		$('#setTableNum').empty();
		var index = $(this).find(":selected").attr("index");
		// 如果選擇的樓層有桌子
		// 將桌子options掛到選擇桌子的選單中
		if (index < tableoptions.length) {
			for (var i = 0; i < tableoptions[index].length; i++) {
				$('#setTableNum').append(tableoptions[index][i]);
			};
		};
	});
	
}
function createGarbageCan(locationId,canId){
	var Gcan = $('<div id="garbageCan'+canId+'" class="garbageCanOriginal"></div>');
	$('#'+locationId).append(Gcan);
	$(Gcan).droppable({ //將垃圾桶 加入droppable事件
		hoverClass: "garbageCanHover",
	  	drop: function( event, ui ) {
	  		var fkid = ui.draggable.attr("fkid");
	  		ui.draggable.remove();
			var foodCount = 0;
			var thisTab = $('#ul-detail>li[divid="orderlist-'+canId+'"]');
			$('#orderlist-'+canId).find('input').each(function(index,foodBtn){
				foodCount++;
			});
			if(foodCount <= 0 && canId != 0){
				deletSetTab(thisTab);
			}
			if(canId != 0){
				var fkCountSpan = document.createElement("span");
				$(fkCountSpan).attr("id", "fkCountSpan-"+fkid);
				$(fkCountSpan).css({
					"border-radius":"10px",
					"margin-top":"5px",
					"margin-bottom":"-12px",
					"margin-right":"2px",
					"margin-left":"2px",
					"border":"1px dotted darkblue",
					"width":"19%",
					"height":"70%",
					"display":"inline-block",
				});
				$('#orderlist-'+canId).find('#fkdiv-'+fkid).append(fkCountSpan);
			};
	  	}
	});
}

function deletSetTab(thisTab){
//	 setCount--;
	 var panelId = $( thisTab ).remove().attr("divid");
	 var canId = panelId.substring(panelId.length-1);
	 console.log("panelId, canId = "+panelId+","+canId);
	 var index = parseInt(canId);
	 

	 
	 console.log(index);
	 $('#garbageCan'+canId).remove();
	 $( "#" + panelId ).remove();
     $( "#orderlist" ).tabs( "refresh" );
     
     
}

function confirmClick() {
	$("#finishDialog").empty();
	
	
	var dialog = $("#finishDialog");
	// 依序抓出每個分頁的div
	var foods = [];
	$.each($("div[id^='orderlist-']"), function (index, child) {
		var setDiv = $('<div style="width:200px;float:left;"></div>');
		var setName = null;
		var setId = null;
		if (index == 0) {
			var foodCount = 0;
			$('#orderlist-0>input').each(function(index3,content){
				foodCount ++;
			});
			if(foodCount>0)
				$(setDiv).append("<h3 style='height:10px'>單點</h3>");
		} else {
			setId = $(this).attr("setid");
			var aryi = 0;
			for (; aryi < setIds.length; aryi++) {
				if (setIds[aryi] == setId) {
					break;
				};
			}
			setName = setNames[aryi];
			$(setDiv).append("<h3 style='height:10px'>"+setName+"</h3>");
		}
		
		// 取出每個餐點
		$.each($(this).find("input"), function (index, cchild) {
			$(setDiv).append("<p style='margin-left:20px'>"+$(this).val()+"</p>");
			foods.push(
					{
						"fdid":$(this).attr("fdid"), 
						"fdName":$(this).val(), 
						"setId":setId, 
						"setName":setName }
					);
			});
		$(dialog).append(setDiv);
	});
	currentStatus.Foods = foods;
	$(dialog).dialog("open");
}

function checkAddable(thisbtn) {
	var active = $("#orderlist").tabs("option", "active");
	if (active == 0) {
		addOrderAreaBtn('orderlist-'+active,
				$(thisbtn).attr("fdId"),
				$(thisbtn).attr("isMain"),
				$(thisbtn).val(), 
				$(thisbtn).attr("fkId"));
		$( "#orderlist" ).tabs( "refresh" );
		return false;
	}
	
	console.log("=====================");
	
//	var obj = $('div[id^="orderlist-"]');
//	var divid ="";
//	console.log("active="+active);
//	for (var i = 0; i <= active; i++) {
//		divid = $(obj[i]).attr("id");
//	}
//	console.log("divid="+divid);
//	console.log(divid.substring(divid.length-1));
//	
//	var div = $('#'+divid);
	var div = $("#orderlist-"+activeToId(active));
	var thisFk = $(thisbtn).attr("fkid");
	console.log("this.fkid="+thisFk);
	var setid = div.attr("setid");
	var aryi = 0;
	for (; aryi < setIds.length; aryi++) {
		if (setIds[aryi] == setid) {
			break;
		};
	}
	var detail = setdetails[aryi];
	console.log("detail="+setdetails[aryi]);
	var detailCount = 0;
	for (var i = 0; i < detail.length; i++) {
		if (detail[i]==thisFk) {
			detailCount++;
		};
	}
	console.log("detailCount="+detailCount);
	var childCount = 0;
	$.each($(div).find("input"), function( index, childBtn ) {
		if (parseInt($(childBtn).attr("fkid")) ==thisFk) {
			  console.log($(childBtn).val());
			  childCount ++;
		  }
	});
	console.log("childCount="+childCount);
	console.log("=====================");
	if (detailCount > childCount) {
		return true;
	}
	
	console.log("超過數量!");
	
	return false;
}

function activeToId(active) {
	var obj = $('div[id^="orderlist-"]');
//	var divid ="";
//	console.log("active="+active);
//	for (var i = 0; i <= active; i++) {
//		divid = $(obj[i]).attr("id");
//	}
	divid = $(obj[active]).attr("id");
//	console.log("divid="+divid);
//	console.log(divid.substring(divid.length-1));
	
	return parseInt(divid.substring(divid.length-1));
}

function IdToActive(divid) {
	var obj = $('div[id^="orderlist-"]');
	for (var i = 0; i < obj.length; i++) {
		if (divid == $(obj[i]).attr("id")) {
			return i;
		}
	}
}

function checkEmpty() {
	// 分頁數目為1
	if ($('div[id^="orderlist-"]').length > 1) {
		return false;
	// 單點中沒有按鈕
	} else if ($('#orderlist-0').find('input').length == 0) {
		return true;
	}
	return false;
}

function orderlistClick() {
	$("#ChooseSetDialog").empty();
	if($(this).attr("isMain") == 'true'){
		for (var i = 0; i < setNames.length; i++) {
			// 選套餐dialog中的套餐按鈕
			var btn = document.createElement("input");
			$(btn).attr({
				type:"button", 
				"class":"MainBtnColor", 
				setId:setIds[i],
				FBId:$(this).attr("id"),
			});
			$(btn).val(setNames[i]);
			$(btn).click(setOnClick);
			$("#ChooseSetDialog").append(btn);
		}
		$("#ChooseSetDialog").dialog( "open" );
	} else{
		$(this).effect("highlight");
	};
}

function setOnClick() {
	// 新的div id
	var tagsid = "orderlist-"+ setCount;
	// 新增新的li/div
	$("#ul-detail").append("<li divid='"+tagsid+"'><a href='#"+ tagsid +"'>"+$(this).val()+"</a><span class='ui-icon ui-icon-close' role='presentation'>Remove Tab</span></li>");
	$("#orderlist").append("<div id='"+tagsid+"' setId='"+$(this).attr("setid")+"'></div>");
	$("#orderlist").tabs("refresh");
	
	var setid = $(this).attr("setid");
	var aryi = 0;
	for (; aryi < setIds.length; aryi++) {
		if (setIds[aryi] == setid) {
			break;
		};
	}
	var fkDetail = setdetails[aryi];
	for(var i=0 ; i<fkDetail.length;i++){
		if($('#'+tagsid).find('#fkdiv-'+fkDetail[i]).length<=0){
			var fkdiv = document.createElement("div");
			$(fkdiv).attr("id", "fkdiv-"+fkDetail[i]);
			$(fkdiv).css({
				"margin-top":"5px",
				"height":"50px",
				"border-bottom":"2px solid black",
				"padding-left":"5px",
				"padding-right":"5px",
			});
			for(var j=0;j<fks.length;j++){
				if(fks[j].fkId == fkDetail[i]){
					$(fkdiv).append("<p style='display:inline-block;width:100px;'>"+fks[j].fkName+"</span>");
				};
			}
			$('#'+tagsid).append(fkdiv);
		};
	}
	
	// 將點選要搭配套餐的主餐新增到新的div中所對應的食物類別DIV(id="fkdiv-x")
	var fdBtn = $('#'+$(this).attr("FBId"));
	addOrderAreaBtn("fkdiv-"+$(fdBtn).attr("fkId"), $(fdBtn).attr("fdId"), true, $(fdBtn).val(), $(fdBtn).attr("fkId"),setCount);
	$(fdBtn).remove();
	createGarbageCan(tagsid,setCount);//放入垃圾桶
	
	addCanOrderCountSpan($(this),tagsid,$(fdBtn).attr("fkId"));
	
	$("#ChooseSetDialog").dialog( "close" );
	// 將active移到新的tab
	var obj = $('div[id^="orderlist-"]');
//	$("#orderlist").tabs( "option", "active", setCount);
	console.log(obj.length);
	$("#orderlist").tabs( "option", "active", obj.length-1);
	setCount++;
}

function getSets() {
	var url = contextPath+"/order/getOrderDataServlet";
	$.getJSON(url, {"data":"set"}, function(result) {
		getSetCallback(result);
	});
}

function getSetCallback(result) {
	for (var i = 0; i < result.length; i++) {
		setIds[i] = result[i].id;
		setNames[i] = result[i].name;
		setdetails[i] = result[i].detail;
	};
}
function getFks() {
//	var fkscookie = getCookie("fks");
//	if (fkscookie != "") {
//		var result = cookieDecorder(fkscookie);
//		var obj = $.parseJSON( result );
////		console.log(obj);
//		getFksCallback(obj);
//	} else {
	var url = contextPath+"/order/getOrderDataServlet";
	$.getJSON(url, {"data":"fk"}, function(result) {
		getFksCallback(result);
	});
//	};
}

function getFksCallback(result) {
	for (var i = 0; i < result.length; i++) {
		fks[i] = {
				fkName:result[i].fkName,
				fkId:result[i].fkId
		};
	};
}

function getFoods() {
	var url = contextPath+"/order/getOrderDataServlet";
	$.getJSON(url, {"data":"food"}, function(result) {
		drawTab(result);
	});
}

function drawTab(result) {
	var mains = result.isMain;
	var tagscount = 0;
	for (; tagscount < mains.length; tagscount ++) {
		$("#orderarea").append("<div id='orderarea-"+tagscount+"'></div>");
		for (key in mains[tagscount]) {
			var li = document.createElement("li");
			$(li).append("<a href='#orderarea-"+tagscount+"'>"+key+"</a></li>");
			var foods = mains[tagscount][key];
			for (var j = 0; j < foods.length; j++) {
				addOrderAreaBtn("orderarea-"+tagscount,foods[j].fdId,true,foods[j].fdName,foods[j].fkId);
			}
			$("#ul-order").append(li);
		};
	}


	var others = result.notMain;
	for (var i = 0; i < others.length; i++) {
		tagscount++;
		$("#orderarea").append("<div id='orderarea-"+tagscount+"'></div>");
		for (key in others[i]) {
			var li = document.createElement("li");
			$(li).append("<a href='#orderarea-"+tagscount+"'>"+key+"</a></li>");
			var foods = others[i][key];
			for (var j = 0; j < foods.length; j++) {
				addOrderAreaBtn("orderarea-"+tagscount,foods[j].fdId,false,foods[j].fdName,foods[j].fkId);
			}
			$("#ul-order").append(li);
		};
	}
	
	$('#ul-detail').css("display","block");
	createGarbageCan('orderlist-0',0);

	$("#orderarea").tabs({ heightStyle: "fill", 
		hide : { effect: "fade", duration: 150 }, 
		show : { effect: "fade", duration: 150 }});
	$("#orderlist").tabs({ heightStyle: "fill", 
		hide : { effect: "fade", duration: 150 }, 
		show : { effect: "fade", duration: 150 }});
	hideLoading();
}

var FBId = 0;
function addOrderAreaBtn(foodTag,fdId,isMain,foodName,fkId,orderlistActive) {
	var foodBtnId = "foodBtnId"+FBId;
	var newOABtn = $("<input class='MainBtnColor FoodBtnStyle' type='button'>");
	newOABtn.attr({
		fdId:fdId, 
		isMain:isMain,
		value:foodName,
		id:foodBtnId,
		fkId: fkId
	});
	if(foodTag.indexOf("fkdiv-") != -1 || foodTag.indexOf("orderlist-") != -1 ){
		$(newOABtn).draggable({cancel:false,revert: "invalid"});
	}
	if(orderlistActive == -1 || orderlistActive == null){
		$('#'+foodTag).append(newOABtn);
	}else{
		var CanOrderCountSpan_Count = $('#orderlist-'+orderlistActive).find('#fkdiv-'+fkId).find('span').length;
		if(CanOrderCountSpan_Count > 0){
			$('#orderlist-'+orderlistActive).find('#fkdiv-'+fkId).find('span[id^="fkCountSpan-"]').remove();
		}
		$('#orderlist-'+orderlistActive).find('#'+foodTag).append(newOABtn);
		for(var i=0;i<CanOrderCountSpan_Count-1;i++){
			var fkCountSpan = document.createElement("span");
			$(fkCountSpan).attr("id", "fkCountSpan-"+fkId);
			$(fkCountSpan).css({
				"border-radius":"10px",
				"margin-top":"5px",
				"margin-bottom":"-12px",
				"margin-right":"2px",
				"margin-left":"2px",
				"border":"1px dotted darkblue",
				"width":"19%",
				"height":"70%",
				"display":"inline-block",
			});
			$('#orderlist-'+orderlistActive).find('#fkdiv-'+fkId).append(fkCountSpan);
		};
	}
	FBId++;
//	this refresh cause error!
//	$( "#orderarea" ).tabs( "refresh" );
}

function addCanOrderCountSpan(SetBtn,tagsid,firstFkId){
	// 建立不同品項div
	var setid = $(SetBtn).attr("setid");
	var aryi = 0;
	for (; aryi < setIds.length; aryi++) {
		if (setIds[aryi] == setid) {
			break;
		};
	}
	var fkDetail = setdetails[aryi];
	var fkCanOrderCount = [];
	for(var i=0 ; i<fkDetail.length;i++){
		fkCanOrderCount[fkDetail[i]] = 0;
	}
	for(var i=0 ; i<fkDetail.length;i++){
		fkCanOrderCount[fkDetail[i]]++;
	}
	fkCanOrderCount[firstFkId]--;
	//$(fkdiv).append("<span style='display:inline-block;width:100px;'>"+fks[j].fkName+"</span>");
	for(var i=0 ; i<fkDetail.length;i++){
		var fkdiv = $('#'+tagsid).find('#fkdiv-'+fkDetail[i]);	
		if($(fkdiv).find('#fkCountSpan-'+fkDetail[i]).length<=0){
			for(var j=0;j<fks.length;j++){
				if(fks[j].fkId == fkDetail[i]){
					for(var k=0;k<fkCanOrderCount[fkDetail[i]];k++){
						var fkCountSpan = document.createElement("span");
						$(fkCountSpan).attr("id", "fkCountSpan-"+fkDetail[i]);
						//var fkCountDiv = $("<div>"+fks[j].fkName+"</div>");
						$(fkCountSpan).css({
							"border-radius":"10px",
							"margin-top":"5px",
							"margin-bottom":"-12px",
							"margin-right":"2px",
							"margin-left":"2px",
							"border":"1px dotted darkblue",
							"width":"19%",
							"height":"70%",
							"display":"inline-block",
						});
						$('#'+tagsid).find(fkdiv).append(fkCountSpan);
					};
				};
			};
		}
	}
}


