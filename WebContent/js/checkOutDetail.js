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

		$.getJSON(contextPath+"/secure/getDisCount",{"disId":valuekey},function(data){
			if(parseFloat(data)==1.0){
				$("#disShow").empty();
				$('#showprice').html(paymentPrice);
			}else{
			$("#disShow").empty().html(data+"折");
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
	$("#checkout").hide();
		b=false;
	}else{
		$("input[name='realprice']").hide();
	    $('#discription').hide();
	    $("label[name='realpriceText'],label[name='discriptionText']").hide();
	    $("#dis,#disText").show();
	    $("#checkout").show();
	    b=true;
	}	
}

function calculatePrice(){
	
	
	var realprice = $("input[name='realprice']").val();
	if(realprice<0 || realprice=="" || isNaN(realprice) ){

		$('#showprice').html($("#price").val());
		return;
	}else{	
	var initPrice = $("#price").val();
	var showPrice = parseFloat(initPrice)-parseFloat(realprice); 
	$('#showprice').html(showPrice);
	$("input[name='realprice']").val(realprice);
	 divide=true;
	 paymentPrice =showPrice;
	}
}

