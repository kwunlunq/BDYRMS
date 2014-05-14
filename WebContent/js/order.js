var setNames   = [];
var setIds     = [];
var setdetails = [];
var fks        = [];
var orderlistIndex = 0;


$(function() {
	getFoods();	// 取得所有食物
	getSets(); 	// 取得套餐資訊
	getFks(); 	// 取得fk資訊
	listenerInitial(); // 掛載listener
	
	// 解決IE緩存問題
	$.ajaxSetup({ cache: false });
});


function listenerInitial() {
	$('#finishDialog').dialog({
		autoOpen: false});
	$('#orderConfirm').click(confirmClick);
	$('#orderarea').on('click','input',function(){
		var canAdd = checkAddable($(this));
		if (canAdd) {
			addOrderAreaBtn('orderlist-'+orderlistIndex,
							$(this).attr("fdId"),
							$(this).attr("isMain"),
							$(this).val(), 
							$(this).attr("fkId"));
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
	
	$( "#orderlist" ).delegate( "span.ui-icon-close", "click", function() {
		 var panelId = $( this ).closest( "li" ).remove().attr("divid");
	      $( "#" + panelId ).remove();
	      $( "#orderlist" ).tabs( "refresh" );
	});
}

function confirmClick() {
	$("#finishDialog").dialog("open");
	var dialog = $("#finishDialog");
//	console.log($(this).attr("id"));
	$.each($("div[id^='orderlist-']"), function (index, child) {
//		console.log(index+" : "+$(this).attr("id"));
		if (index == 0) {
			$(dialog).append("<h2>單點</h2>");
		} else {
			var setId = $(this).attr("setid");
			console.log("setId="+setId);
			console.log("setNames="+setNames);
			var aryi = 0;
			for (; aryi < setIds.length; aryi++) {
				if (setIds[aryi] == setId) {
					break;
				}
			}
			var setName = setNames[aryi];
			$(dialog).append("<h2>"+setName+"</h2>");
		}
		
		$.each($(this).children("input"), function (index, cchild) {
			$(dialog).append("<h3>"+$(this).val()+"</h3>");
				
				
//			console.log($(this).attr("id"));
			});
//		$(dialog).append(ul);
	});
}

function checkAddable(thisbtn) {
	var active = $("#orderlist").tabs("option", "active");
	if (active == 0) {
		return true;
	}
	
	console.log("=====================");
	var div = $("#orderlist-"+active);
	var thisFk = $(thisbtn).attr("fkid");
	console.log("this.fkid="+thisFk);
	var setid = div.attr("setid");
	var aryi = 0;
	for (; aryi < setIds.length; aryi++) {
		if (setIds[aryi] == setid) {
			break;
		}
	}
	var detail = setdetails[aryi];
	console.log("detail="+setdetails[aryi]);
	var detailCount = 0;
	for (var i = 0; i < detail.length; i++) {
		if (detail[i]==thisFk) {
			detailCount++;
		}
	}
	console.log("detailCount="+detailCount);
	var childCount = 0;
	$.each($(div).children("input"), function( index, childBtn ) {
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
	}else{
		$(this).effect("highlight");
	}
}
var setCount = 1;
function setOnClick() {
	// 新的div id
	var tagsid = "orderlist-"+ setCount;
	// 新增新的li/div
	$("#ul-detail").append("<li divid='"+tagsid+"'><a href='#"+ tagsid +"'>"+$(this).val()+"</a><span class='ui-icon ui-icon-close' role='presentation'>Remove Tab</span></li>");
	$("#orderlist").append("<div id='"+tagsid+"' setId='"+$(this).attr("setid")+"'></div>");
	$("#orderlist").tabs("refresh");
	
	// 建立不同品項div
	var fkdiv = document.createElement("div");
	$(fkdiv).attr("id", "fkdiv-");
//	console.log("setid="+$(this).attr("setid"));
//	var setid = $(this).attr("setid");
//	console.log(setdetails[setid]);
	// 將點選要搭配套餐的主餐新增到新的div(id="orderlist-x")
	var fdBtn = $('#'+$(this).attr("FBId"));
	addOrderAreaBtn(tagsid, $(fdBtn).attr("fdId"), true, $(fdBtn).val(), $(fdBtn).attr("fkId"));
	$(fdBtn).remove();
	
	$("#ChooseSetDialog").dialog( "close" );
	// 將active移到新的tab
	$("#orderlist").tabs( "option", "active", setCount);
	setCount++;
}
function getSets() {
	var url = contextPath+"/order/getSetServlet";
	$.getJSON(url, function(result) {
		for (var i = 0; i < result.length; i++) {
			setIds[i] = result[i].id;
			setNames[i] = result[i].name;
			setdetails[i] = result[i].detail;
		}
	});
}
function getFks() {
	var url = contextPath+"/order/getFoodkindServlet";
	$.getJSON(url, function(result) {
		for (var i = 0; i < result.length; i++) {
			fks[i] = {
					fkName:result[i].fkName,
					fkId:result[i].fkId
			};
//			console.log(fks[i]);
		}
	});
}

function getFoods() {
	var url = contextPath+"/order/getAllFoodsServlet";
	$.getJSON(url, function(result) {
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
		}
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
		}
	}

	$("#orderarea").tabs({ heightStyle: "fill", 
		hide : { effect: "fade", duration: 150 }, 
		show : { effect: "fade", duration: 150 }});
	$("#orderlist").tabs({ heightStyle: "fill", 
		hide : { effect: "fade", duration: 150 }, 
		show : { effect: "fade", duration: 150 }});
}
var FBId = 0;
function addOrderAreaBtn(foodTag,fdId,isMain,foodName,fkId) {
	var foodBtnId = "foodBtnId"+FBId;
	var newOABtn = $("<input class='MainBtnColor' type='button'>");
	newOABtn.attr({
		fdId:fdId, 
		isMain:isMain,
		value:foodName,
		id:foodBtnId,
		fkId: fkId
	});
	$('#'+foodTag).append(newOABtn);
	FBId++;
}




