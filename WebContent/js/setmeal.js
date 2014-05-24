

function showMe (it, box) {
	  var vis = (box.checked) ? "block" : "none";
	  document.getElementById(it).style.display = vis;
	}

function checkPrice(fkId){
	var num = document.getElementById("text"+fkId).value;
	alert(num);
}