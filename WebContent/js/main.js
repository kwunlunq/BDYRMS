$(function(){
	var icons = {
		      header: "ui-icon-plusthick",
		      activeHeader: "ui-icon-minusthick"
		    };
	console.log(contextPath+"/index.jsp");
	$( "#mainPageAcdion" ).accordion({
		collapsible: true,
		heightStyle: "content",
		icons : icons
	});
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
	$('#showstate').fadeToggle(700,function(){
		setTimeout(hideState , 1200);
	});	
}
function hideState(){
	$('#showstate').fadeToggle(700,function(){
		$(this).remove();
	});
}