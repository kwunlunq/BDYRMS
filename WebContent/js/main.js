console.log(contextPath+"/index.jsp");

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

$(function(){

});