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
	var tbStateName;
	if(tbState == 0)
		tbStateName = "未使用";
	else
		tbStateName = "使用中";
	
	if(tbId == -1 || tbId == null)
		tbId = "tb"+idCount;
	var myOffset = $('#picTB').position();
	var mot = parseInt(myOffset.top);
	var mol = parseInt(myOffset.left);
	var topCount = mot;
	var leftCount = mol;	
	if(tbLocation == 0 || tbLocation == null || tbLocation == ""){
		topCount = mot + (count*5);
		leftCount = mol + (count*5);	
	}else
	{
		tbLocation = tbLocation.split(",");
		var tt = parseInt(tbLocation[0]);
		var tl = parseInt(tbLocation[1]);
		topCount = mot+tt;
		leftCount = mol+tl;
	}

	//Create Table
	var newTbDiv = document.createElement("div");
	newTbDiv.innerHTML = tbName;
	newTbDiv.innerHTML += "<br>"+tbSize+" 人桌";
	newTbDiv.innerHTML += "<br>"+tbStateName;
	newTbDiv.innerHTML += '<input type="hidden" id="tbName" value="'+tbName+'">';
	newTbDiv.innerHTML += '<input type="hidden" id="tbSize" value="'+tbSize+'">';
	newTbDiv.innerHTML += '<input type="hidden" id="tbState" value="'+tbState+'">';
	newTbDiv.innerHTML += '<br><img class="tbimg" id="lockIMG" alt="unLock" src="'+contextPath+'/images/unLock.jpg">';
	newTbDiv.innerHTML += '<img class="tbimg" id="delTB" alt="unLock" src="'+contextPath+'/images/del.jpg">';
	newTbDiv.setAttribute("style",'position:absolute;top:'+topCount+'px;left:'+leftCount+'px');
	newTbDiv.setAttribute("class","divTB");
	newTbDiv.setAttribute("id",tbId);
	
	var newd =$(newTbDiv);
	newd.draggable();
	newd.draggable( "option", "containment", "parent" );
	newd.draggable( "option", "cursor", "crosshair" );
	$('#picTB').append(newd);
}

$(function() {
	getFloor();

	$('body').on('change','#changeFloor',doLoadTable);
	
	$('body').on('click','#reset',doLoadTable);
	
	$('#picTB').on('click','#delTB',function(){
		$('#saveFloor').attr("disabled","disabled");
		$(this).attr("id","");
		$(this).parents('#picTB>div').fadeToggle(800,function(){
			$(this).remove();
			$('#saveFloor').removeAttr("disabled");
		});
		var TBcount = $(this).parents('#picTB>div').attr("id");
		tablesDataForSaveInJson.delTBlist.push(TBcount);
		$('#dataTB>div[id='+TBcount+']').slideToggle(500,function(){
			$(this).remove();	
		});
	});
		
	$('body').on('click','#saveFloor',function(){
		$('#picTB>div').each(function(){
			var TBid =  $(this).attr("id");
			var tbName = $('#'+TBid+'>input[id="tbName"]').val();
			var tbSize = $('#'+TBid+'>input[id="tbSize"]').val();
			var state = $('#'+TBid+'>input[id="tbState"]').val();
			//location
			var pic_Offset = $('#picTB').position();
			var pic_top = parseInt(pic_Offset.top);
			var pic_left = parseInt(pic_Offset.left);
			var myOffset = $(this).position();
			var top = parseInt(myOffset.top - pic_top);
			var left = parseInt(myOffset.left - pic_left);

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
	});

	$('#picTB').on('click','#lockIMG',function(){
		$(this).parents("#picTB>div").draggable();
		var state = $(this).attr('alt');
		if(state=="unLock")
		{
			$(this).parents("#picTB>div").draggable("destroy");
			$(this).attr('alt','Lock');
			$(this).attr('src', contextPath + '/images/Lock.jpg');
		}else
		{
			$(this).parents("#picTB>div").draggable("option", "containment", "parent");
			$(this).attr('alt','unLock');
			$(this).attr('src',contextPath + '/images/unLock.jpg');
		}
	});
	
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