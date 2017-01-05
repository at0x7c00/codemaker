function auto_assistant(){
  bind(params);
  var outputTag=document.getElementById(params.outputTagId);
  var e=getTopLeft(outputTag.parentNode);
  outputTag.style.top=(e[0]+33)+"px";
  outputTag.style.left=(e[1]+2)+"px";
}
function getTopLeft(e){
	var a = new Array();
	var t = e.offsetTop;
	var l = e.offsetLeft;
	var w = e.offsetWidth;
	var h = e.offsetHeight;
	while (e = e.offsetParent) {
		t += e.offsetTop;
		l += e.offsetLeft;
	}
	a[0] = t;
	a[1] = l;
	a[2] = w;
	a[3] = h;
	return a;
}  