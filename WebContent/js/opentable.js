var tablesDataForSaveInJson = {
		"act" : null,
		"floor" : "-1",
		"tables" : [],
		"delTBlist" : [] 
};
var stateName = ["閒置","點餐","用餐","預約","關閉"];
var stateDesc = ["0-閒置中","1-等待點餐","2-用餐中","3-有客人預約","4-不開放使用"];
var tableUrl = contextPath + "/table/tableset";
var count = 0;
var idCount = 0;

function getFloor(){
	tablesDataForSaveInJson.act = "init";
	$.ajax({
	    url: tableUrl,
	    type: 'POST',
	    data: JSON.stringify(tablesDataForSaveInJson),
	    contentType: 'application/json; charset=utf-8',
	    dataType :'json',
	    success: function(floorList) {
			for(var i=0 ;i<floorList.length;i++){
				var floorData = floorList[i];
				$('#selectFloor').append("<input style='font-size:1.0em;width:40px;margin:2px' class='MainBtnColor' type='button' floorId='"+floorData.floorId+"' value='"+floorData.floorName+"'>");
			}
	    }
	});
}

function doLoadTable(thisBtn){
	$('#picTB>div').fadeToggle(800,function(){
		$(this).remove();
	});
	count = 0;
	idCount = 0;
	var floorId = $(thisBtn).attr("floorId");
	var floorName = $(thisBtn).val();
	tablesDataForSaveInJson.act = "load";
	tablesDataForSaveInJson.floor = floorId;
	$.ajax({
	    url: tableUrl,
	    type: 'POST',
	    data: JSON.stringify(tablesDataForSaveInJson),
	    contentType: 'application/json; charset=utf-8',
	    dataType:'JSON',
	    success: function(tableList) {
	    	$('#picTB>div').remove();
			if(tableList == null || tableList.length < 1)
			{
				showState("尚未有擺設");
			}else{
				for(var i=0; i<tableList.length;i++){
					var tableData = tableList[i];
					var tbId = tableData.tbId;
					var tbName = tableData.tbName;
					var tbSize = tableData.tbSize;
					var tbLocation = tableData.tbLocation;
					var tbState = tableData.tbState;
					var custNum = tableData.custNum;
					addTB(tbId ,tbName , tbSize , tbState , tbLocation, custNum, floorId, floorName);
				}
				showState("讀取完成");
			}
	    }
	});
}

function addTB(tbId ,tbName , tbSize , tbState , tbLocation , custNum, floorId, floorName){
	idCount++;
	count++;
	tbLocation = tbLocation.split(",");
	var topCount = parseInt(tbLocation[0]);
	var leftCount = parseInt(tbLocation[1]);

	//Create Table
	var newTbDiv = document.createElement("div");
	var newTbNameSpan = $("<span id='tbNameSpan'>"+tbName+"</span>");
	var newTbSizeSpan = null;
	if(tbState != 0){
		newTbSizeSpan = $("<span id='tbSizeSpan' style='position:absolute;right:5px;bottom:1px'>"+custNum+"("+tbSize+")</span>");
	}else{
		newTbSizeSpan = $("<span id='tbSizeSpan' style='position:absolute;right:5px;bottom:1px'>"+tbSize+"</span>");
	}
	$(newTbDiv).append(newTbNameSpan);
	$(newTbDiv).append(newTbSizeSpan);
	newTbDiv.setAttribute("style",'position:absolute;top:'+topCount+'px;left:'+leftCount+'px');
	newTbDiv.setAttribute("class",setTbState(tbState));
	newTbDiv.setAttribute("tbName",tbName);
	newTbDiv.setAttribute("tbSize",tbSize);
	newTbDiv.setAttribute("tbState",tbState);
	newTbDiv.setAttribute("custNum",custNum);
	newTbDiv.setAttribute("floorId",floorId);
	newTbDiv.setAttribute("floorName",floorName);
	newTbDiv.setAttribute("id",tbId);
	
	$('#picTB').append(newTbDiv);
	$(newTbDiv).click(function(){
		divTBclick($(this));
	});	
}

function setTbState(tbState){
	var stateClass = "divTBOrg divTB";
	if(tbState == 0 ){ //閒置 Org
		stateClass = "divTBOrg divTB";
	}else if(tbState == 1){ //點餐 InUse
		stateClass = "divTBInUse divTB";
	}else if(tbState == 2){ //用餐 Eat
		stateClass = "divTBEat divTB";
	}else if(tbState == 3){ //預約 Booking
		stateClass = "divTBBooking divTB";
	}else if(tbState == 4){ //關閉 Closed
		stateClass = "divTBClosed divTB";
	}else{                  // 例外 Org (閒置)
		stateClass = "divTBOrg divTB";
	}
	return stateClass;
}

function divTBclick(divTB){
	$('#tbClickDialog input[type=button]').remove();
	$('#peopleCount').val($(divTB).attr("custNum"));
	$('#countP').css("display","none");
	var tbId = $(divTB).attr("id");
	var tbName = $(divTB).attr("tbName");
	var tbSize = $(divTB).attr("tbSize");
	var tbState = $(divTB).attr("tbState");
	var fId = $(divTB).attr("floorId");
	var fName = $(divTB).attr("floorName");
	$(divTB).css("z-index","99999");
	$('#tbClickDialog span[id=tbNameLable]').text(tbName);
	$('#tbClickDialog span[id=tbSizeLable]').text(tbSize);
	$('#tbClickDialog span[id=tbStateLable]').text(stateName[tbState] + "(" + stateDesc[tbState] + ")");
	var tbOpenBtn = $('<input id="takeSet" value="帶位" type="button" aria-disabled="false">');
	var tbOrderBtn = $('<input id="order" value="點餐" type="button" aria-disabled="false">');
	var tbCheckoutBtn = $('<input id="checkout" value="結帳" type="button" aria-disabled="false">');
	var tbCancelBtn = $('<input id="cancel" value="離開" type="button" aria-disabled="false">');
	var tbLeaveBtn = $('<input id="leave" value="空桌" type="button" aria-disabled="false">');
	if(tbState == 0){
		$('#countP').css("display","block");
		$('#tbClickDialog div[id=buttonBar]').append(tbOpenBtn);
		$('#tbClickDialog div[id=buttonBar]').append(tbOrderBtn);
	}else if(tbState == 1){
		$('#countP').css("display","block");
		$(tbOpenBtn).val("更改人數");
		$('#tbClickDialog div[id=buttonBar]').append(tbLeaveBtn);
		$('#tbClickDialog div[id=buttonBar]').append(tbOpenBtn);
		$('#tbClickDialog div[id=buttonBar]').append(tbOrderBtn);
	}else if(tbState == 2){
		$('#tbClickDialog div[id=buttonBar]').append(tbCheckoutBtn);
	}else if(tbState == 3){
		$('#tbClickDialog div[id=buttonBar]').append(tbOpenBtn);
		$('#tbClickDialog div[id=buttonBar]').append(tbOrderBtn);
		$('#tbClickDialog div[id=buttonBar]').append(tbCheckoutBtn);
	}
	$('#tbClickDialog div[id=buttonBar]').append(tbCancelBtn);
	$('#tbClickDialog input[type=button]').button().click(function( event ) {
		var act = $(this).attr("id");
		if(act == "cancel"){
			$(divTB).css("z-index","0");
    		$('#tbClickDialog').dialog('close');
		}else if(act == "checkout"){
			$(divTB).css("z-index","0");
			window.location=contextPath+"/checkout/checkDetail.action?tabId="+tbId;
		}else if(act == "order"){
			var cNum = $('#peopleCount').val();
			fName = encodeURI(encodeURI(fName));
			tbName = encodeURI(encodeURI(tbName));
			var url = contextPath+"/order/order.jsp?fId="+fId+"&fName="+fName+"&cNum="+cNum+"&tbId="+tbId+"&tbName="+tbName;
			window.location = url;
		}else if(act == "takeSet"){
			if($('#peopleCount').val()>0 && $('#peopleCount').val().length>0 && $('#peopleCount').val()<1000){
				var updateTable ={
						"act" : "tbOpen",
						"tbId" : tbId,
						"tbState" : 1,
						"custNum" : $('#peopleCount').val(),
						"floor" : "-1"
				};
				$.ajax({
				    url: tableUrl,
				    type: 'POST',
				    data: JSON.stringify(updateTable),
				    contentType: 'application/json; charset=utf-8',
				    success: function() {
						$(divTB).attr("custNum",updateTable.custNum);
						$(divTB).attr("class",setTbState(1));
						$(divTB).attr("tbState","1");
						$(divTB).find('span[id=tbSizeSpan]').text(updateTable.custNum+"("+tbSize+")");
						$(divTB).css("z-index","0");
						$('#tbClickDialog').dialog('close');
				    }
				});
			}else if($('#peopleCount').val()>999){
				showState("超過上限 (小於3位)");
			}else{
				showState("未輸入來客數");
			}
		}else if(act="leave"){
			var updateTable ={
					"act" : "tbOpen",
					"tbId" : tbId,
					"tbState" : 0,
					"custNum" : "0",
					"floor" : "-1"
			};
			$.ajax({
			    url: tableUrl,
			    type: 'POST',
			    data: JSON.stringify(updateTable),
			    contentType: 'application/json; charset=utf-8',
			    success: function() {
					$(divTB).attr("custNum",0);
					$(divTB).attr("class",setTbState(0));
					$(divTB).attr("tbState","0");
					$(divTB).find('span[id=tbSizeSpan]').text(tbSize);
					$(divTB).css("z-index","0");
					$('#tbClickDialog').dialog('close');
			    }
			});
		}else{
			showState("無效的按鈕");
		}
    });
	$('#tbClickDialog').dialog({
		width:420,
		resizable:false,
		modal:true,
		position: { 
			my: "left top", 
			at: "right top", 
			of: $(divTB) 
		},
		show: {
	        effect: "blind",
	        duration: 800
	      },
	    hide: {
	        effect: "blind",
	        duration: 500
	    }
	});
}

function inputNum(thisNum){
	var num = $(thisNum).text();
	if(num != "C"){
		$('#peopleCount').append(num);
	}else{
		$('#peopleCount').val('0');
	}
}

$(function(){
	getFloor();
	$('#selectFloor').on('click','input',function(){
		$('#selectFloor').find('input').each(function(){
			$(this).attr("class","MainBtnColor");
		});
		$(this).attr("class","ActiveBtnColor");
		doLoadTable($(this));
	});
	$('body').on('click','#numBtn',function(){
		var textValue = $('#peopleCount').val();
		if($(this).text() == "C"){
			$('#peopleCount').val("0");
		}else{
			if(textValue == '0')
				textValue="";
			$('#peopleCount').val(textValue+$(this).text());
		}
	});
	hideLoading();
});