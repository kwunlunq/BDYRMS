
window.onresize = resizeWindow;
window.onload = resizeWindow;

function resizeWindow(){
	var mainContentWidth = $('#mainPageContent').width();
	$('#article').css("width",mainContentWidth - 166.5);
}

function goURL(page){
	window.location = page;
}

function showState(txt){
	$('#showstate').remove();
	var stateDiv = document.createElement("div");
	var stateVal = document.createTextNode(txt);
	stateDiv.setAttribute("id", "showstate");
	stateDiv.appendChild(stateVal);
	$('body').append(stateDiv);
	$('#showstate').css("margin-left","-"+($('#showstate').width()/2)+"px");
	$('#showstate').fadeToggle(600,function(){
		setTimeout(hideState , 1200);
	});	
}
function hideState(){
	$('#showstate').fadeToggle(600,function(){
		$(this).remove();
	});
}

//function showLoading(){
//	$('#loading').remove();
//	$('#loadingControl').remove();
//	var allScreenDiv = document.createElement("div");
//	$(allScreenDiv).css({
//	      "background-color": "black",
//	      "opacity":"0.5",
//	      "width":"100%",
//	      "height":"100%",
//	      "left":"0px",
//	      "top":"0px",
//	      "position":"absolute"
//	    });
//	var loadingDiv = document.createElement("div");
//	var loadingVal = "<img src='"+contextPath+"/images/loading.gif'>";
//	loadingDiv.setAttribute("id", "loading");
//	allScreenDiv.setAttribute("id", "loadingControl");
//	$(loadingDiv).html(loadingVal);
//	$('body').append(allScreenDiv);
//	$('body').append(loadingDiv);
//	$('#loading').css("margin-left","-"+($('#loading').width()/2)+"px");
//	$('#loading').fadeToggle(200);	
//}
function hideLoading(){
	$('#loading,#loadingControl').fadeToggle(800,function(){
		$('#loadingControl').remove();
		$(this).remove();
	});
}

function setTableToMaxStyle(tableId){
	var header = $('#'+tableId+'Header');
	$(header).css("padding","10px");
	$(header).css("font-size","1.3em");
	$(header).addClass("ui-state-default ui-corner-top");
	var footer = $('#'+tableId+'Footer');
	$(footer).css("padding","10px");
	$(footer).css("font-size","1.3em");
	$(footer).addClass("ui-state-default ui-corner-bottom");
	var tb = $('#'+tableId);
	$('#'+tableId+' tr:even').css({background:'#e8f4ff'});
	$('#'+tableId+' tr:odd').css({background:'white'});
	$(tb).css("border-spacing","0");
	$(tb).css("width","100%");
	$(tb).css("height",$('#writeCodeInThisDiv').height()-$(header).height()-$(footer).height()-50);
	$(tb).css("border","1px solid rgb(167,207,223)");
	$(tb).css("text-align","center");
	$(tb).find('th').addClass("ui-state-default");
	$('#'+tableId+" td").css("border-bottom","1px solid rgb(167,207,223)");
	$('#'+tableId+" tr").addClass("max_td_style");
	var count = 0;
	var thCount = 0;
	$('#'+tableId+">thead").find('th').each(function(){
		thCount++;
	});
	$('#'+tableId+">tbody").find('tr').each(function(){
		count++;
	});
	if(count <= 0){
		$('#'+tableId+">tbody").append("<tr style='height:100%'><td colspan='"+thCount+"' style='text-align:center;font-size:2em;'>尚無資料</td></tr>");
	}else{
		$('#'+tableId+">tbody").append("<tr><td style='height:100%' colspan='"+thCount+"'></td></tr>");
	}
	$('#'+tableId+' tr:odd').hover(function(){
		$(this).css("background","#d7ebf9");
		$('#'+tableId+' tr:last').css("background","white");
	},function(){
		$(this).css("background","white");
		$('#'+tableId+' tr:last').css("background","white");
	});
	
	$('#'+tableId+' tr:even').hover(function(){
		$(this).css("background","#d7ebf9");
		$('#'+tableId+' tr:last').css("background","white");
	},function(){
		$(this).css("background","#e8f4ff");
		$('#'+tableId+' tr:last').css("background","white");
	});
}