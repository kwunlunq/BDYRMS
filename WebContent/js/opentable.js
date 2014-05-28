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
	tablesDataForSaveInJson.act = "load";
	tablesDataForSaveInJson.floor = $(thisBtn).attr("floorId");
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
					addTB(tbId ,tbName , tbSize , tbState , tbLocation);
				}
				showState("讀取完成");
			}
	    }
	});
}

function addTB(tbId ,tbName , tbSize , tbState , tbLocation){
	idCount++;
	count++;
	if(tbState == 0)
		tbStateName = "未使用";
	else
		tbStateName = "使用中";
	tbLocation = tbLocation.split(",");
	var topCount = parseInt(tbLocation[0]);
	var leftCount = parseInt(tbLocation[1]);

	//Create Table
	var newTbDiv = document.createElement("div");
	var newTbNameSpan = $("<span id='tbNameSpan'>"+tbName+"</span>");
	var newTbSizeSpan = $("<span id='tbSizeSpan' style='position:absolute;right:5px;bottom:1px'>"+tbSize+"</span>");
	$(newTbDiv).append(newTbNameSpan);
	$(newTbDiv).append(newTbSizeSpan);
	newTbDiv.setAttribute("style",'position:absolute;top:'+topCount+'px;left:'+leftCount+'px');
	if(tbState == 0 ){ //閒置 Org
		newTbDiv.setAttribute("class","divTBOrg divTB");
	}else if(tbState == 1){ //點餐 InUse
		newTbDiv.setAttribute("class","divTBInUse divTB");
	}else if(tbState == 2){ //用餐 Eat
		newTbDiv.setAttribute("class","divTBEat divTB");
	}else if(tbState == 3){ //預約 Booking
		newTbDiv.setAttribute("class","divTBBooking divTB");
	}else if(tbState == 4){ //關閉 Closed
		newTbDiv.setAttribute("class","divTBClosed divTB");
	}else{                  // 例外 Org (閒置)
		newTbDiv.setAttribute("class","divTBOrg divTB");
	}
	newTbDiv.setAttribute("tbName",tbName);
	newTbDiv.setAttribute("tbSize",tbSize);
	newTbDiv.setAttribute("tbState",tbState);
	newTbDiv.setAttribute("id",tbId);
	
	$('#picTB').append(newTbDiv);
	$(newTbDiv).click(function(){
		divTBclick($(this));
	});	
}

function divTBclick(divTB){
	var tbId = $(divTB).attr("id");
	var tbName = $(divTB).attr("tbName");
	var tbSize = $(divTB).attr("tbSize");
	var tbState = $(divTB).attr("tbState");
	$(divTB).css("z-index","99999");
	$('#tbClickDialog span[id=tbNameLable]').text(tbName);
	$('#tbClickDialog span[id=tbSizeLable]').text(tbSize);
	$('#tbClickDialog span[id=tbStateLable]').text(stateName[tbState] + "(" + stateDesc[tbState] + ")");
	$('#tbClickDialog').dialog({
		width:400,
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
	    },
	    buttons:{
	    	"入座":function(){
	    	},
	    	"點餐":function(){
	    	},
	    	"結帳":function(){
	    		window.location=contextPath+"/checkout/checkDetail.action?tabId="+tbId;
	    	},
	    	"離開":function(){
    		$(divTB).css("z-index","0");
    		$('#tbClickDialog').dialog('close');
    		}
	    }
	});
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
	hideLoading();
});