$(function(){
	url = contextPath + "/news";
	$.getJSON(url,{"act":"getAllNews"},function(datas){
		console.log(datas);
		for(var i in datas){
			var newsPostdate = datas[i].newsPostdate.substring(0,19);
			var title = $("<h3>"+datas[i].newsTitle+"["+datas[i].newsType+"]<span class='titleDate'>At "+newsPostdate+"</span></h3>");
			var content = $("<div>"+datas[i].newsContent+"<br><span style='float:right'>by "+datas[i].newsPostname+"<span></div>");
			//<h3>使用Jquery UI 注意事項  <span class='titleDate'>(重要  20140512)</span></h3>
			$('#mainPageAcdion').append(title);
			$('#mainPageAcdion').append(content);
		}
		$( "#mainPageAcdion" ).accordion({
			collapsible: true,
			heightStyle: "content",
			icons : icons
		});
	});
	
	$('#addNewsForm').trigger("reset");
	var icons = {
		      header: "ui-icon-plusthick",
		      activeHeader: "ui-icon-minusthick"
		    };
	console.log(contextPath+"/index.jsp");
	window.onresize = resizeWindow;
	window.onload = resizeWindow;
	$('#btnAddNews').click(function(){
		$( "#addNewsDIV" ).toggle( "blind", 1000,function(){
			if($( "#btnAddNews" ).val() == "取消")
			{
				$('#addNewsForm').trigger("reset");
				$('#errorTitle').text("");
				$('#errorContent').text("");
				$('#doAddNews').remove();
				$( "#btnAddNews" ).val("發佈消息");
			}
			else{
				$('#newsBtnBox').append("<input id='doAddNews' class='MainBtnColor' type='button' value='發佈'>");
				$( "#btnAddNews" ).val("取消");
			}
		});
	});
	
	$('body').on('click','#doAddNews',function(){
		var isSend = true;
		if($('input[name="newsTitle"]').val().length <= 0){
			isSend = false;
			$('#errorTitle').text("(必填)");
		}else
			$('#errorTitle').text("");
		if($('textarea[name="newsContent"]').val().length <= 0){
			isSend = false;
			$('#errorContent').text("(必填)");
		}else
			$('#errorTitle').text("");
		if(isSend){
			$('#errorTitle').text("");
			$('#errorContent').text("");
			$('#addNewsForm').trigger("submit");
			$('#addNewsForm').trigger("reset");
		}
	});
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