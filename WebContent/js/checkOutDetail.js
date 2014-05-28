$(function() {
    $( "#tabs" ).tabs().addClass( "ui-tabs-vertical ui-helper-clearfix" );
    $( "#tabs li" ).removeClass( "ui-corner-top" ).addClass( "ui-corner-left" );
    
  });

function getDisCount(valuekey){
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
};