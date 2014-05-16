$(function(){
	
	
});
var xmlHttpInit;
function empUpdate(empId){
	alert(empId);
	var count = 0;
	$('#trEmp'+empId+'>td').each(function(){
		
		if(count<10){
			if(count==4){
				empOption(empId);
			}
			var str = $(this).text().trim();
			$(this).html("<input type='text' size='7' value='"+str+"'>");
			count++;
		}
//		if(count < 10){
//			var str = $(this).text().trim();
//			$(this).html("<input type='text' size='7' value='"+str+"'>");
//			count++;
//		}
	});	
}
function empOption(empId){
	var sele=document.createElement("select");
	sele.setAttribute("id","myPri");
	alert();
}
