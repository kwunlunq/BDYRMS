
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
		if($(this).val().length > 0 && $(this).val() != null && $(this).val() != "")
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

//function focusCheckPrice(fkId){
//	var focusnum = document.getElementById("text"+fkId).value;
//	onblurCheckPrice(focusnum,fkId);
//}

var b;
function setConfirm(){
	var setPrice = document.getElementById("setPrice").value;
	
	if(!isNaN(setPrice)&&setPrice>totalPrice){	
		b=window.confirm("套餐價錢大於限定價錢總合 確定新增");
		if(b){
			window.location.href=contextPath+"";
		}
	}		
}







