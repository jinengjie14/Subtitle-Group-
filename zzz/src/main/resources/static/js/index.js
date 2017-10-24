/**
 * 
 */
window.onload=function a1(){
	var a=document.getElementById("container");
	a.style.transform="translateX(-924px)";
	
}


//JavaScript Document
function chklogin(form){
	if(form.login.value==""){
		var a=document.getElementById("errors");
		a.innHTML="";
		alert('用户名不能为空!');
		form.login.select();
		return false;
		}
		if(form.password.value==""){
		alert('密码不能为空!');
		form.password.select();
		return false;
		}
		return true;
	}