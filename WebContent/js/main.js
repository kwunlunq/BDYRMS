$(function(){
	showLoading();
	loadNews();
	$('#addNewsForm').trigger("reset");
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
		
		if($('input[name="newsPostname"]').val().length <= 0){
			isSend = false;
			showState("請先登入");
		}else
			$('#errorTitle').text("");
		
		if(isSend){
			$('#errorTitle').text("");
			$('#errorContent').text("");
			addNews();
			$('#addNewsForm').trigger("reset");
		}
	});
	window.onresize = resizeWindow;
	window.onload = resizeWindow;
	var icons = {
		      header: "ui-icon-plusthick",
		      activeHeader: "ui-icon-minusthick"
		    };
	$( "#mainPageAcdion" ).accordion({
		collapsible: true,
		heightStyle: "content",
		icons : icons
	});
	$('#btnAddNews').click(function(){
		$( "#addNewsDIV" ).toggle( "blind", 1000,function(){
			doChangeAddNewsDiv();
		});
	});
	$('body').on('click','#delNewsBtn',function(){
		delNews($(this));
	});
});

function loadNews(){
	$( "#mainPageAcdion" ).empty();
	url_getAllNews = contextPath + "/news";
	$.getJSON(url_getAllNews,{"act":"getAllNews"},function(datas){
		console.log(datas);
		for(var i in datas){
			var newsPostdate = datas[i].newsPostdate.substring(0,19);
			var title = $("<h3>"+datas[i].newsTitle+"["+datas[i].newsType+"]<span class='titleDate'>At "+newsPostdate+"</span></h3>");
			var content = $("<div id='newsContent'><pre>"+datas[i].newsContent+"</pre><br><span style='float:right'>by "+datas[i].newsPostname+"<input class='MainBtnColor' id='delNewsBtn' newsId='"+datas[i].newsId+"' type='button' value='刪除'><span></div>");
			//<h3>使用Jquery UI 注意事項  <span class='titleDate'>(重要  20140512)</span></h3>
			$('#mainPageAcdion').append(title);
			$('#mainPageAcdion').append(content);
		}
		$( "#mainPageAcdion" ).accordion( "refresh" );
	});
}

function addNews(){
	url_addNews = contextPath + "/news";
	getNewsTitle = $('#addNewsForm input[name="newsTitle"]').val();
	getNewsType = $('#addNewsForm select[name="newsType"]').val();
	getNewsContent = $('#addNewsForm textarea[name="newsContent"]').val()
	getNewsPostname = $('#addNewsForm input[name="newsPostname"]').val();
	$.ajax({
		  type: "POST",
		  url: url_addNews,
		  data: {
			  act: "postNews",
			  newsTitle: getNewsTitle,
			  newsType: getNewsType,
			  newsContent: getNewsContent,
			  newsPostname: getNewsPostname
			  }
		})
		  .done(function( msg ) {
		    $( "#addNewsDIV" ).toggle( "blind", 1000,function(){
		    	doChangeAddNewsDiv();
		    	showState("發佈成功");
		    	loadNews();
		    });
		});
}

function delNews(delBtn){
	url_addNews = contextPath + "/news";
	getNewsId = $(delBtn).attr("newsId");
	$.ajax({
		  type: "POST",
		  url: url_addNews,
		  data: {
			  act: "delNews",
			  newsId: getNewsId
			  }
		})
		  .done(function( msg ) {
			  loadNews();
			  showState("刪除成功");
		});
}

function doChangeAddNewsDiv(){
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

function showLoading(){
	$('#loading').remove();
	var loadingDiv = document.createElement("div");
	var loadingVal = "<image src='"+contextPath+"/images/loading.gif'>";
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