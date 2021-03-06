
window.onresize = resizeWindow;
window.onload = resizeWindow;
$(function(){
	clock();
});
var tick;
function stop() {
clearTimeout(tick);
}
function clock() {
var ut=new Date();
var h,m,s;
//var time="        ";
h=ut.getHours();
m=ut.getMinutes();
s=ut.getSeconds();
var year = ut.getFullYear();
var month = ut.getMonth() +1 ;
var d = ut.getDate();
var w = ut.getDay();
var w_str = ["日","一","二","三","四","五","六"];
if(s<=9) s="0"+s;
if(m<=9) m="0"+m;
if(h<=9) h="0"+h;
if(month<=9) month = "0"+month;
if(d<=9) d="0"+d;
$('#hourHeader').html(h+":");
$('#minHeader').html(m);
$('#secHeader').html(":"+s);
$('#todayHeader').html(year + " - " + month + " - " +d + " (" + w_str[w]+")");
//time+=h+":"+m+":"+s;
//document.getElementById('clock').innerHTML=time;
tick=setTimeout("clock()",1000); 
}

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
	var footer = $('#'+tableId+'Footer');
	var tb = $('#'+tableId);
	if(tableId == 'talbe'){
		tb = $('talbe');
	}
	$(header).css("padding","10px");
	$(header).css("font-size","1.3em");
	$(header).addClass("tableHeaderAndFooter ui-corner-top");
	$(header).find('input[type=button]').button();
	$(header).find('input[type=button]').css("font-size","0.7em");
	$(header).find('input[type=button]').css("font-weight","narmal");
	
	$(footer).css("padding","10px");
	$(footer).css("font-size","1.3em");
	$(footer).addClass("tableHeaderAndFooter ui-corner-bottom");
	$(footer).find('input[type=button]').button();
	$(footer).find('input[type=button]').css("font-size","0.7em");
	$(footer).find('input[type=button]').css("font-weight","narmal");
	
	if(tableId == 'talbe'){
		$('table tr:even').css({background:'#e8f4ff'});
		$('table tr:odd').css({background:'white'});
	}else{
		$('#'+tableId+' tr:even').css({background:'#e8f4ff'});
		$('#'+tableId+' tr:odd').css({background:'white'});
	}
	$(tb).css("border-spacing","0");
	$(tb).css("width","100%");
	$(tb).css("border","2px solid rgb(53,106,160)");
	$(tb).css("text-align","center");
	$(tb).find('th').addClass("tableThead");
	$(tb).find('th:first').css("border-left","0");
	$(tb).find('th:last').css("border-right","0");
	if(tableId == 'talbe'){
		$('talbe td').css("border","1px solid rgb(167,207,223)");
		$('talbe tr').addClass("max_td_style");
	}else{
		$('#'+tableId+" td").css("border","1px solid rgb(167,207,223)");
		$('#'+tableId+" tr").addClass("max_td_style");
	}

	$(tb).find('input[type=button]').button();
	$(tb).find('input[type=button]').css("font-size","0.95em");
	var count = 0;
	var thCount = 0;
	
	if(tableId == 'talbe'){
		$("talbe thead").find('th').each(function(){
			thCount++;
		});
		$("talbe tbody").find('tr').each(function(){
			count++;
		});
	}else{
		$('#'+tableId+">thead").find('th').each(function(){
			thCount++;
		});
		$('#'+tableId+">tbody").find('tr').each(function(){
			count++;
		});
	}
	
	
	if(count <= 0){
		if(tableId == 'talbe'){
			$("table tbody").append("<tr><td colspan='"+thCount+"' style='text-align:center;font-size:2em;'>尚無資料</td></tr>");
		}else{
			$('#'+tableId+">tbody").append("<tr><td colspan='"+thCount+"' style='text-align:center;font-size:2em;'>尚無資料</td></tr>");
		}
	}
	
	if(tableId == 'talbe'){
		$('talbe tr:odd').hover(function(){
			$(this).css("background","#d7ebf9");
		},function(){
			$(this).css("background","white");
		});
		
		$('talbe tr:even').hover(function(){
			$(this).css("background","#d7ebf9");
		},function(){
			$(this).css("background","#e8f4ff");
		});
	}else{
		$('#'+tableId+' tr:odd').hover(function(){
			$(this).css("background","#d7ebf9");
		},function(){
			$(this).css("background","white");
		});
		
		$('#'+tableId+' tr:even').hover(function(){
			$(this).css("background","#d7ebf9");
		},function(){
			$(this).css("background","#e8f4ff");
		});
	}
}