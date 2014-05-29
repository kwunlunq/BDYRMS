$(function() {
    $( "#tabs" ).tabs().addClass( "ui-tabs-vertical ui-helper-clearfix" );
    $( "#tabs li" ).removeClass( "ui-corner-top" ).addClass( "ui-corner-left" );
    $("input[name='realprice']").hide();
    $('#discription').hide();
    $("label[name='realpriceText'],label[name='discriptionText']").hide();
  });

var divide=false;
var paymentPrice;
function getDisCount(valuekey){
	if(divide==false){
	$.getJSON(contextPath+"/secure/getDisCount",{"disId":valuekey},function(data){
		if(parseFloat(data)==1.0){
			$("#disShow").empty();
			$('#showprice').html($('#price').val());
		}else{
		$("#disShow").empty().html(data+"折");		
		var price = parseFloat($('#price').val());
		var finprice =price*parseFloat(data);
		$('#showprice').html(finprice);
		}
	});
	}else{
		alert("modify");
		$.getJSON(contextPath+"/secure/getDisCount",{"disId":valuekey},function(data){
			if(parseFloat(data)==1.0){
				$("#disShow").empty();
				$('#showprice').html(paymentPrice);
			}else{
			$("#disShow").empty().html(data+"折");
			alert("paymentPrice" + paymentPrice);
			var finprice =paymentPrice*parseFloat(data);
			$('#showprice').html(finprice);
			}
		});
	}
};

var b=true;
function showDiscription(){
	if(b==true){
	$("input[name='realprice']").show();
	$('#discription').show();
	$("label[name='realpriceText'],label[name='discriptionText']").show();
	$("#dis,#disText").hide();
		b=false;
	}else{
		$("input[name='realprice']").hide();
	    $('#discription').hide();
	    $("label[name='realpriceText'],label[name='discriptionText']").hide();
	    $("#dis,#disText").show();
	    b=true;
	}	
}

function calculatePrice(){
	//alert($("input[name='realprice']").val());
	//alert(realPrice)
	
	var realprice = $("input[name='realprice']").val();
	if(realprice<0 || realprice=="" || isNaN(realprice) ){
		alert("1");
		$('#showprice').html($("#price").val());
		return;
	}else{	
	//alert("correct");
	var initPrice = $("#price").val();
	//alert("correct="+$("input[name='realprice']").val());
	
	//var modifyPrice = $("input[name='realprice']").val();
	var showPrice = parseFloat(initPrice)-parseFloat(realprice); 
	alert(initPrice);
	alert(showPrice);
	$('#showprice').html(showPrice);
	$("input[name='realprice']").val(realprice);
	 divide=true;
	 paymentPrice =showPrice;
	}
}

