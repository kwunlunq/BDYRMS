
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