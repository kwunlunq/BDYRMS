$(function(){
	$( 'body' ).find("div[cssId=accordion]").accordion();
	
});

function showMe (fkId,it, box) {
	  var vis = (box.checked) ? "block" : "none";
	  document.getElementById(it).style.display = vis;
	  if($('#span'+fkId).css("display")=='none'){
		  $('#text'+fkId).val('') ;
		  getTotalPrice();
	  }
	  
	}

function getTotalPrice(){
	var price = 0;
	var totalPrice=0;
	$('input[fakeId="textPrice"]').each(function (index,text){
		if($(this).val().length >0 && isNaN($(this).val())){
			alert("請輸入數字");
			$(this).val('');
		}
		else if($(this).val().length > 0 && $(this).val() != null && $(this).val() != "")
			price = $(this).val();
		else
			price = 0;
		totalPrice = parseInt(totalPrice) + parseInt(price);
	});
	$('#totalPrice').text(totalPrice);
}

function onblurCheckPrice(fkId){
	var num = document.getElementById("text"+fkId).value;
	if(!isNaN(num)){	
		if(num==null||num==''){
			$('span').remove();
			$('#text'+fkId).after("<span id='sp"+fkId+"'>請輸入金額</span>");
		}else{
		
		totalPrice = parseInt(totalPrice)+parseInt(num);
		$('#totalPrice').text(totalPrice) ;
		$('span').remove();
		}
	}
	else{
		alert(num+"非正確字元");
		
	}
	
}



var b;
function setConfirm(){
	var setPrice = document.getElementById("setPrice").value;
	var settatolPrice = document.getElementById("totalPrice").innerHTML;
	
	if(!isNaN(setPrice)&&setPrice>settatolPrice){	
		b=window.confirm("套餐價錢大於限定價錢總合 確定新增");
	}
	
}

function checkPrice(){
	
	var setPrice = document.getElementById("setPrice").value;
	
	if(isNaN(setPrice)||setPrice==''){
		alert("請輸入數字");
		return false;
	}else{
		
		var settatolPrice = document.getElementById("totalPrice").innerHTML;
		if(!isNaN(setPrice) && parseInt(setPrice) > parseInt(settatolPrice)){
			alert("套餐價錢不能大於限定價錢總合 ");
			return false;
		}
		return true;
	}
	
}
//------------------------------setupdate----------------------------------------------
function updateSet(setId){
	$('body').find("input[name=checkname]").each(function(){
		$(this).prop("checked", false);
	});
	
	$('#divPrice').find('div').each(function(){
		
		$(this).css('display','none');		
	});
	$('#divPrice').find('input').each(function(){
		var a = $(this).val('');
	});
	
	
	var detailids= [];
	var detailfkids=[];
	$('#setId'+setId).find("span[id=detailname]").each(function(){//抓detail的id 
		detailids.push($(this).attr("detailid"));
	});
	$('#setId'+setId).find("span[id=detailname]").each(function(){//抓fk的id 
		detailfkids.push($(this).attr("detailfkid"));
	});
	console.log("detailid = "+detailids);
	console.log("detailfkid = "+detailfkids);
	var setName = $('#setname'+setId).text();
	var setPrice = $('#setprice'+setId).text();
	$('#setName').val(setName);
	$('#setPrice').val(setPrice);
	
	for(var i=0;i<detailfkids.length;i++){
		
		var settext = $('#settext'+detailids [i]).text();
		$('#check'+detailfkids[i]).attr("checked","checked");
		$('#span'+detailfkids[i]).css("display","block");
		$('#text'+detailfkids[i]).val(settext);
	}
	$('#changebtn').empty();
	$('#changebtn').append( "<input class='MainBtnColor' name='buttonName' type='submit' value='確認更改' onclick='return checkPrice()'>" +
							"<input class='MainBtnColor' type='button' value='取消' onclick='onclear()'>"+
							"<input type='hidden' name='setId' value='"+setId+"'>");
	getTotalPrice();
	
}
function onclear(){
	$('body').find("input[name=checkname]").each(function(){
		$(this).prop("checked", false);
	});
	
	$('#divPrice').find('div').each(function(){
		
		$(this).css('display','none');		
	});
	$('#divPrice').find('input').each(function(){
		var a = $(this).val('');
	});
	$('#totalPrice').text('0');
	$('#setName').val('');
	$('#setPrice').val('');
	$('#changebtn').empty();
	$('#changebtn').append("<input class='MainBtnColor' type='submit' name='buttonName' value='確定' onclick='return checkPrice()'>");
}

function deleteSet(setId){
	var b = window.confirm("確定刪除");
	if(b){
	window.location.href =  contextPath+"/setmeal/deleteset?setId="+setId;
	}
}








