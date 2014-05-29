var tablesDataForSaveInJson = {
		"act" : null,
		"floor" : "-1",
		"floorName" : null,
		"tables" : [],
		"delTBlist" : [],
		"delFloorList": [],
		"floorList" : []
};
var tableUrl = contextPath + "/table/tableset";
var count = 0;
var idCount = 0;
var canAddTbBtn = false;
var canSaveTbBtn = false;

$(function() {
	canAddTbBtn = false;	
	$('#addTB').css("color","gray");
	canSaveTbBtn = false;
	$('#saveFloor').css("color","gray");
	
	getFloor();
	
	$('body').on('change','#changeFloor',doLoadTable);
	
	$('body').on('click','#reLoad',doLoadTable);
		
	$('body').on('click','#saveFloor',saveFloor);
	
	$('#addTB').click(addTbDialog);
	
	$('#addFloor').click(addFloorDialog);
	
	$('#editFloor').click(editFloorDialog);
	
	$('body').on('click','#delIcon',function (){delFloor($(this));});
	
	$('body').on('click','#floorListLi',function () {editFloorName($(this));});
	
});

function updateSession(){
	var url = contextPath + "/order/getOrderDataServlet";
	$.getJSON(url,{"data":"updateTable"});
}

function editFloorName(thisLi){
	var tbName = $(thisLi).text();
	var state = $(thisLi).attr("state");
	var canEdit = true;
	$('#editFloorDialog').find('input[type=text]').each(function (){
		canEdit = false;
	});

	if(state == 'show'){
		if(canEdit){
			$(thisLi).attr("state","edit");
			$(thisLi).html("<input id='editFloorName' style='text-align:center;height:25px;width:150px' type='text' value='"+tbName+"'>");
			$('#editFloorName').focus();
		}else{
			showState("有其他修改正在進行,須先完成");
		}
	}else if(state == 'edit'){
		$(thisLi).attr("state","show");
		$(thisLi).text($('#editFloorName').val());
	}
}

function editTableDialog(thisTB){
	var tbId = $(thisTB).attr("id");
	var tbName = $(thisTB).attr("tbName");
	var tbSize = $(thisTB).attr("tbSize");
	$(thisTB).css("z-index","9999");
	$('#editTbNameText').val(tbName);
	$('#editTbSizeText').val(tbSize);
	$('#editTableDialog span').each(function (){
		  $(this).text("");
	});
	$('#editTableDialog').dialog({
		resizable:false,
		modal:true,
		position: { 
			my: "left top", 
			at: "right top",
			of: $(thisTB) 
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
	    	  "取消":function (){
	    		  $(thisTB).css("z-index","0");
	    		  $('#editTableDialog').dialog('close');
	    	  },
	    	  "刪除":function(){
	    		  $(thisTB).css("z-index","0");
	    		  tablesDataForSaveInJson.delTBlist.push(tbId);
	    		  count--;
	    		  $('#tableCount').text(count);
	    		  $('#editTableDialog').dialog('close');
	    		  $('#picTB>div[id='+tbId+']').toggle('explode',800,function(){
	    			  $(this).remove();
	    		  });
	    	  },
	    	  "修改":function() {
	    		  var canAdd = true;
	    		  tbName = $('#editTbNameText').val();
	    		  tbSize = $('#editTbSizeText').val();
	    		  if(!checkData('tbName',tbName)){
	    			  $('#editTbNameError').text("必填(最多10個字)");
	    			  canAdd = false;
	    		  }else{
	    			  $('#editTbNameError').text("");
	    		  }
	    		  if(!checkData('tbSize',tbSize)){
	    			  $('#editTbSizeError').html("必填<br>(須為數字並大於0且最多三位)");
	    			  canAdd = false;
	    		  }else{
	    			  $('#editTbSizeError').text("");
	    		  }
	    		  if(canAdd){
	    			  tablesDataForSaveInJson.delTBlist = [];
	    			  $(thisTB).attr("tbName",tbName);
	    			  $(thisTB).attr("tbSize",tbSize);
	    			  $('#picTB>div[id='+tbId+']>span[id=tbNameSpan]').text(tbName);
	    			  $('#picTB>div[id='+tbId+']>span[id=tbSizeSpan]').text(tbSize);
	    			  showState("修改成功");
	    			  $('#editTableDialog').dialog('close');
	    			  $(thisTB).css("z-index","0");
	    		  }
	    	  }
	      }
	});
}

function delFloor(thisIcon){
	var floorId = $(thisIcon).attr("floorId");
	$('#sortableFloor').find('li[floorId='+floorId+']').remove();
	tablesDataForSaveInJson.delFloorList.push(floorId);
	console.log(tablesDataForSaveInJson.delFloorList);
}

function editFloorDialog(){
	getFloorInSortableFloor();
    $( "#sortableFloor" ).sortable({
        placeholder: "ui-state-highlight"
      });
    $( "#sortableFloor" ).disableSelection();
	$('#editFloorDialog').dialog({
		resizable:false,
		modal:true,
		position: { 
			my: "center center", 
			at: "top top",  
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
	    	  "取消":function (){
	    		  $('#editFloorDialog').dialog('close');
	    	  },
	    	  "重設":function (){
	    		  tablesDataForSaveInJson.delFloorList = [];
	    		  getFloorInSortableFloor();
	    	  },
	    	  "儲存":function() {
	    		  var canSave = true;
	    		  $('#editFloorName').each(function (){
	    			  canSave = false;
	    			  showState('場地名稱未修改完成');
	    		  });
	    		  if(canSave){
	    			  doSaveFloorList();
	    			  showState("修改完成");
	    		  }
	    		  
	    	  }
	      }
	});
}

function doSaveFloorList(){
	tablesDataForSaveInJson.floorList = [];
	tablesDataForSaveInJson.act = "updateFloor";
	$("#sortableFloor>li").each(function (){
		var floorData = {
				"floorId" : $(this).attr("floorId"),
				"floorName" : $(this).text()
		};
		tablesDataForSaveInJson.floorList.push(floorData);
	});
	console.log(tablesDataForSaveInJson.floorList);
	$.ajax({
	    url: tableUrl,
	    type: 'POST',
	    data: JSON.stringify(tablesDataForSaveInJson),
	    contentType: 'application/json; charset=utf-8',
	    success: function(state) {
	    	tablesDataForSaveInJson.delFloorList = [];
	    	getFloorInSortableFloor();
	    	getFloor();
	    	if(state != 0){
	    		showState("刪除失敗!請確認刪除的場地不能有桌子/擺設");
	    	}else{
	    		updateSession();
	    		showState("儲存完成");
	    	}
	    },
	});
}

function getFloorInSortableFloor(){
	tablesDataForSaveInJson.act = "init";
	$.ajax({
	    url: tableUrl,
	    type: 'POST',
	    data: JSON.stringify(tablesDataForSaveInJson),
	    contentType: 'application/json; charset=utf-8',
	    dataType :'json',
	    success: function(floorList) {
	    	$('#sortableFloor').empty();
			for(var i=0 ;i<floorList.length;i++){
				var floorData = floorList[i];
				$('#sortableFloor').append("<li class='ui-state-default' floorId='"+floorData.floorId+"'><div id='floorListLi' state='show'>"+floorData.floorName+"</div><span id='delIcon' floorId='"+floorData.floorId+"' class='ui-state-default ui-corner-all' ><span class='ui-icon ui-icon-closethick'></span></span></li>");
			}
	    }
	});
}

function addFloorDialog(){
	$('#addFloorDialog').dialog({
		resizable:false,
		modal:true,
		position: { 
			my: "center center", 
			at: "top top", 
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
	    	  "取消":function (){
	    		  $('#addFloorDialog').dialog('close');
	    		  $('#addFloorDialog input').each(function (){
	    			  $(this).val("");
	    		  });
	    		  $('#addFloorDialog span').each(function (){
	    			  $(this).text("");
	    		  });
	    	  },
	    	  "新增":function() {
	    		  var canAdd = true;
	    		  var floorName = $('#addFloorNameText').val();

	    		  if(!checkData('tbName',floorName)){
	    			  $('#addFloorNameError').text("必填(最多10個字)");
	    			  canAdd = false;
	    		  }else{
	    			  $('#addFloorNameError').text("");
	    		  }

	    		  if(canAdd){
	    			  doAddFloorAjax(floorName);
		    		  $('#addFloorDialog input').each(function (){
		    			  $(this).val("");
		    		  });
	    			  showState("新增成功! 可繼續新增或取消");
	    		  }
	    	  }
	      }
	});
} 

function doAddFloorAjax(floorName){
	tablesDataForSaveInJson.floorName = floorName;
	tablesDataForSaveInJson.act = "addFloor";
	$.ajax({
	    url: tableUrl,
	    type: 'POST',
	    data: JSON.stringify(tablesDataForSaveInJson),
	    contentType: 'application/json; charset=utf-8',
	    success: function() {
	    	getFloor();
	    	updateSession();
	    	showState('新增完成');
	    }
	});
}

function getFloor(){
	$('#changeFloor').empty();
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
	canAddTbBtn = true;
	$('#addTB').css("color","white");
	canSaveTbBtn = true;
	$('#saveFloor').css("color","white");
	$('#picTB>div').fadeToggle(800,function(){
		$(this).remove();
	});
	$('#tableCount').text("0");
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
		topCount = 0;
		leftCount = 0;	
	}else
	{
		tbLocation = tbLocation.split(",");
		topCount = parseInt(tbLocation[0]);
		leftCount = parseInt(tbLocation[1]);
	}

	//Create Table
	var newTbDiv = document.createElement("div");
	var newTbNameSpan = $("<span id='tbNameSpan'>"+tbName+"</span>");
	var newTbSizeSpan = $("<span id='tbSizeSpan' style='position:absolute;right:5px;bottom:1px'>"+tbSize+"</span>");
	$(newTbDiv).append(newTbNameSpan);
	$(newTbDiv).append(newTbSizeSpan);
	newTbDiv.setAttribute("style",'position:absolute;top:'+topCount+'px;left:'+leftCount+'px');
//	if(tbState == 0 ){ //閒置 Org
//		newTbDiv.setAttribute("class","divTBOrg divTB");
//	}else if(tbState == 1){ //點餐 InUse
//		newTbDiv.setAttribute("class","divTBInUse divTB");
//	}else if(tbState == 2){ //用餐 Eat
//		newTbDiv.setAttribute("class","divTBEat divTB");
//	}else if(tbState == 3){ //預約 Booking
//		newTbDiv.setAttribute("class","divTBBooking divTB");
//	}else if(tbState == 4){ //關閉 Closed
//		newTbDiv.setAttribute("class","divTBClosed divTB");
//	}else{                  // 例外 Org (閒置)
		newTbDiv.setAttribute("class","divTBOrg divTB");
//	}
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
	//$(newTbDiv).click(function (){editTableDialog($(this));});
	$(newTbDiv).dblclick( function () { editTableDialog($(this)); });

	$('#tableCount').text(count);
}

function saveFloor(){
	if(canSaveTbBtn){
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
					updateSession();
					tablesDataForSaveInJson.delTBlist = [];
					tablesDataForSaveInJson.tables = [];
					tablesDataForSaveInJson.act = null;
					tablesDataForSaveInJson.floor = -1;
			   }
		});
	}else{
		showState("請先讀取樓層");
	}
}

function addTbDialog(){
	$('#addTbDialog input').each(function (){
		$(this).val("");
	});
	$('#addTbDialog span').each(function (){
		$(this).text("");
	});
	if(canAddTbBtn){
		$('#addTbDialog').dialog({
			resizable:false,
			modal:true,
			position: { 
				my: "left top", 
				at: "right top",  
				of: $('#addTB') 
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
		    	  "取消":function (){
		    		  $('#addTbDialog').dialog('close');
		    	  },
		    	  "新增":function() {
		    		  var canAdd = true;
		    		  var tbName = $('#addTbNameText').val();
		    		  var tbSize = $('#addTbSizeText').val();
		    		  if(!checkData('tbName',tbName)){
		    			  $('#addTbNameError').text("必填(最多10個字)");
		    			  canAdd = false;
		    		  }else{
		    			  $('#addTbNameError').text("");
		    		  }
		    		  
		    		  if(!checkData('tbSize',tbSize)){
		    			  $('#addTbSizeError').html("必填<br>(須為數字並大於0且最多三位)");
		    			  canAdd = false;
		    		  }else{
		    			  $('#addTbSizeError').text("");
		    		  }
		    		  if(canAdd){
		    			  addTB(-1 ,tbName , tbSize , 0 , 0);
		    			  showState("新增成功! 可繼續新增或取消");
		    		  }
		    	  }
		      }
		});
	}else{
		showState("請先讀取樓層!");
	}
}

function checkData(type,data){
	if(type == 'tbName'){
		if(data != null && data != "" && data.length > 0 && data.length < 11){
			return true;
		}else{
			return false;
		}
	}else if(type == 'tbSize'){
		if(!isNaN(data) && data > 0 && data < 1000){
			return true;
		}else{
			return false;
		}
	}
	return false;
}