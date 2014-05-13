var xmlHttpInit = new XMLHttpRequest();


function supdate(detailid,sid,fkid){
	alert(sid+"---"+fkid);
	
	var count=0;
	$('#TRdetail'+detailid+'>td').each(function(){
		if(count==0){
			$(this).html("<input type='text' size='7' value='111'>")
		}else if(count==1){
			$(this).html("<input type='text' size='7' value='222'>")
		}
		count++;
	})
}