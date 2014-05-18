$(function(){
	showLoading();
	window.onresize = resizeWindow;
	window.onload = resizeWindow;
});

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

function showLoading(){
	$('#loading').remove();
	var loadingDiv = document.createElement("div");
	var loadingVal = "<img src='"+contextPath+"/images/loading.gif'>";
	loadingDiv.setAttribute("id", "loading");
	$(loadingDiv).html(loadingVal);
	$('body').append(loadingDiv);
	$('#loading').css("margin-left","-"+($('#loading').width()/2)+"px");
	$('#loading').fadeToggle(600);	
}
function hideLoading(){
	$('#loading').fadeToggle(600,function(){
		$(this).remove();
	});
}