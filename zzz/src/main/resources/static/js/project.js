window.onload=function(){
	countCircle("#circle-1",77);
	countCircle("#circle-2",55);
}
function countCircle(parent,progress){
    var textContainer = $(parent).find('.circle-percentage');
    var i = 0;
    var time = 2000;
    var intervalTime = Math.abs(time / progress);
    var timerID = setInterval(function () {
      i++;
      textContainer.text(i+"%");
      if (i === progress) clearInterval(timerID);
    }, intervalTime);           
}