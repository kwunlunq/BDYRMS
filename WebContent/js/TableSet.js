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
				$('#changeFloor').append("<option value='"+floorData.floorId+"'>"+floorData.floorName+"</option>");
			}
	    }
	});
}

function doLoadTable(){
	$('#picTB>div').fadeToggle(800,function(){
		$(this).remove();
	});
	count = 0;
	idCount = 0;
	tablesDataForSaveInJson.act = "load";
	tablesDataForSaveInJson.floor = $('#changeFloor').find(":selected").val();
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
	if(tbId == -1 || tbId == null)
		tbId = "tb"+idCount;
	if(tbLocation == 0 || tbLocation == null || tbLocation == ""){
		topCount = count*5;
		leftCount = count*5;	
	}else
	{
		tbLocation = tbLocation.split(",");
		var tt = parseInt(tbLocation[0]);
		var tl = parseInt(tbLocation[1]);
		topCount = tt;
		leftCount = tl;
	}

	//Create Table
	var newTbDiv = document.createElement("div");
	$(newTbDiv).append("<div id='tbNameInTable'>"+tbName+"</div>");
	newTbDiv.setAttribute("style",'position:absolute;top:'+topCount+'px;left:'+leftCount+'px');
	newTbDiv.setAttribute("class","divTBOrg divTB");
	newTbDiv.setAttribute("id",tbId);
	newTbDiv.setAttribute("tbName",tbName);
	newTbDiv.setAttribute("tbSize",tbSize);
	newTbDiv.setAttribute("tbState",tbState);
	
	$(newTbDiv).draggable({
		containment : "parent",
		snap: true,
		stack: "#picTB div",
		revert: "valid",
		snapTolerance: 8
	});
	$(newTbDiv).droppable({
		toleranceType: "touch",
		hoverClass: "divTBerror divTB",
	    drop: function( event, ui ) {
	        $( this ).addClass( "divTB" );
	     },
		out: function( event, ui ) {
			$( this ).addClass( "divTB" );
		}
	});
	$('#picTB').append(newTbDiv);
}

function saveFloor(){
	$('#picTB>div').each(function(){
		var TBid =  $(this).attr("id");
		var tbName = $('#'+TBid).attr("tbName");
		var tbSize = $('#'+TBid).attr("tbSize");
		var state = $('#'+TBid).attr("tbState");

		var myOffset = $(this).position();
		var top = parseInt(myOffset.top);
		var left = parseInt(myOffset.left);

		var tableData = {
			"tbId" : TBid,
			"tbName" : tbName,
			"tbSize" : tbSize,
			"pos" : top+","+left,
			"tbFloor" : $('#changeFloor').find(":selected").val(),
			"tbState" : state
		};
		tablesDataForSaveInJson.tables.push(tableData);
	});
	tablesDataForSaveInJson.act = "save";
	tablesDataForSaveInJson.floor = $('#changeFloor').find(":selected").val();
	$.ajax({
	    url: tableUrl,
		    type: 'POST',
		    data: JSON.stringify(tablesDataForSaveInJson),
		    contentType: 'application/json; charset=utf-8',
		    success: function(msg) {
				showState("儲存完成");
				tablesDataForSaveInJson.delTBlist = [];
				tablesDataForSaveInJson.tables = [];
				tablesDataForSaveInJson.act = null;
				tablesDataForSaveInJson.floor = -1;
		   }
	});
}

$(function() {
	getFloor();

	$('body').on('change','#changeFloor',doLoadTable);
	
	$('body').on('click','#reLoad',doLoadTable);
		
	$('body').on('click','#saveFloor',saveFloor);
	
	$('#addTB').click(function() {
		var tbName;
		var tbSize;
		if(tbName=prompt("請輸入桌子名稱或者代號",""))
		{
			if(tbName.length > 0 && tbName != null && tbName != "")
			{
				if(tbSize=prompt("請輸入桌子可容納人數",""))
				{
					if(tbSize > 0 && tbSize < 99999)
					{
						addTB(-1,tbName , tbSize, 0 , 0);
					}else
					{
						alert("必須為一個整數");
					}
				}
			}
		}
	});
});