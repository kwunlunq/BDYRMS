var tablesDataForSaveInJson = {
		"act" : null,
		"floor" : "-1",
		"tables" : [],
		"delTBlist" : [] 
};
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
	var tbStateName;
	if(tbState == 0)
		tbStateName = "未使用";
	else
		tbStateName = "使用中";
	
	var myOffset = $('#picTB').position();
	var mot = parseInt(myOffset.top);
	var mol = parseInt(myOffset.left);
	tbLocation = tbLocation.split(",");
	var tt = parseInt(tbLocation[0]);
	var tl = parseInt(tbLocation[1]);
	var topCount = mot+tt;
	var leftCount = mol+tl;

	//Create Table
	var newTbDiv = document.createElement("div");
	newTbDiv.innerHTML = tbName;
	newTbDiv.innerHTML += "<br>"+tbSize+" 人桌";
	newTbDiv.innerHTML += "<br>"+tbStateName;
	newTbDiv.setAttribute("style",'position:absolute;top:'+topCount+'px;left:'+leftCount+'px');
	newTbDiv.setAttribute("class","divTB");
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
	var dialogTB = $("<div title='桌號："+$(divTB).attr("tbName")+" 容納人數："+$(divTB).attr("tbSize")+"'></div>");
	$(dialogTB).append("<input id='openTable' class='MainBtnColor' type='button' value='開桌'>");
	$(dialogTB).append("<input id='closeTable' class='MainBtnColor' type='button' value='徹桌'>");
	$(dialogTB).dialog();
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