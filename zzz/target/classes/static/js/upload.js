/**
 * 
 */
window.onload=function a1(){
}


function submitform(){
  	document.form1.submit();
  }


function path(a){
	var b=a.innerHTML;
	var str1=b.split(".");
    var s = str1[1];
    var l=a.parentNode;
    if(s==null||s==""){
    	l.childNodes[3].submit();
    }
    else if(s=="png"||s=="jpg"||s=="tiff"||s=="gif"||s=="pcx"||s=="tga"||s=="exif"||s=="fpx"||s=="svg"||s=="psd"||s=="cdr"||s=="pcd"||s=="raw"){
    	var p=document.getElementById("display_picture");
    	p.style.transition="0.5s all";
    	p.style.opacity=1;
    	p.style.display="block";
    	var p1=document.getElementById("display_picture_h2");
    	p1.innerHTML=b;
    	var p2=document.getElementById("display_picture_img");
    	p2.src=l.childNodes[3].childNodes[5].value;
    }
    
    
    
    
    

}
function prom() {  
    var name = prompt("请输入您想新建的文件夹", ""); //将输入的内容赋给变量 name ，  

    //这里需要注意的是，prompt有两个参数，前面是提示的话，后面是当对话框出来后，在对话框里的默认值  
    if (name)//如果返回的有内容  
    {
    	form4.wjname.value=name;
    	form4.submit();
    }  

}  
function circle(){
	var a=document.getElementById("display_picture");
	a.style.opacity="0";
	setTimeout(function(){
		a.style.display="none";}
		,1000);
	
}
