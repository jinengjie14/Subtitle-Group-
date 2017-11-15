$(document).ready(function(){
	$("#aside_left").click(function(){
  		var screen1 = window.matchMedia('(max-width: 900px)');
	    if (screen1.matches){
	        $("#header")[0].style.left=0;
	    }
  			$("#header_before").fadeToggle();
	});
	$("#header_before").click(function(){
		var screen1 = window.matchMedia('(max-width: 900px)');
	    if (screen1.matches){
	        $("#header")[0].style.left=-256+"px";
	    }
  			$("#header_before").fadeToggle();
	});
	$("#sousuo").click(function(){
		$(".search").animate({height:'55px'},"slow");
  		$(".search").contents()[0].focus();
	});
	$("#search").blur(function(){
		var screen1 = window.matchMedia('(max-width: 540px)');
		if (screen1.matches){
  			 $(".search").animate({height:'0px'},"slow");
  		}
	});
});


function submitform() {
	document.formname1.submit();
}





function post(obj) {
	var id = $(obj).data("id");
	var state = $(obj).prop("checked");
	$.ajax({
		type: "POST",
		url: "/task_update",
		data: {
			"id": id,
			"state": state
		},
		dataType: "json",
		success: function(data) {
			if(data.code == "200") {
				console.log("succeed");
				alert("任务完成!");
			}
			if(data.code == "201") {
				console.log("succeed");
				alert("取消完成!");
			}
			if(data.code == "400") {}
		},
		error: function(date) {
			console.log("asdasd");
		}
	});
}
function pro_edit(obj) {
	var id = $(obj).data("id");
	var proid = obj.id;
	$.ajax({
		type: "POST",
		url: "/pro_edit",
		data: {
			"id": id,
			"proid": proid
		},
		dataType: "json",
		success: function(data) {
			if(data.code == "200") {
				console.log("succeed");
				location.href="/project_details1";
			}
			if(data.code == "400") {
				alert("没有权限！！");
			}
		},
		error: function(date) {
			console.log("asdasd");
		}
	});
}
function pro_delete(pro) {
	var id = $(pro).data("id");
	var proid =pro.id;
	$.ajax({
		type: "POST",
		url: "/pro_delete",
		data: {
			"id": id,
			"proid": proid
		},
		dataType: "json",
		success: function(data) {
			if(data.code == "200") {
				console.log("succeed");
				alert("删除成功!");
			}
			if(data.code == "400") {
				alert("没有权限！！");
			}
		},
		error: function(date) {
			console.log("asdasd");
		}
	});
}


function dataTitle(a) {
	var u = $(a).next(".task_name_active");
	if($(u).children(input=['text'])[0]!=null){
		$(u).children(name="name").focus();
	}
	else if($(u).children(textarea=['text'])[0]!=null){
		$(u).children(textarea=['text']).focus();
	}
	if(u.css("display") == "none") {
		u.css("display", "block");
		$(a).css("display", "none");
	} else {
		u.css("display", "none");
		$(a).css("display", "block");
	}
}
function dataCancel(a){
	$(a).parent('.task_name_active').css("display", "none");
	if($(a).parent().prev('.active')[0]!=null){
		$(a).parent().prev('.active').css("display", "block");
	}else if($(a).parent().prev('.text-content-describe')[0]!=null){
		$(a).parent().prev('.text-content-describe').css("display", "block");
	}
	
}



function task_details(man){
	
	var id = man.id;
	$.ajax({
		type: "POST",
		url: "/task_details",
		data: {
			"id": id
		
		},
		dataType: "json",
		success: function(data) {
			if(data.code == "200") {
				console.log("succeed");
				location.href="/task_details";
			}
			if(data.code == "400") {
				alert("失败！！");
			}
		},
		error: function(date) {
			console.log("asdasd");
		}
	});
}
function comment_save(){
	var content = $("input[ name='comment']").val()
	$.ajax({
		type: "POST",
		url: "/comment_save",
		data: {
			"content": content,
		},
		dataType: "json",
		success: function(data) {
			if(data.code == "200") {
				console.log("succeed");
				alert("发送成功！！");
			}
			if(data.code == "400") {
				alert("发送失败！！");
			}
		},
		error: function(date) {
			console.log("asdasd");
		}
	});
}
function proman_save(man){
	var id = man.id;
	$.ajax({
		type: "POST",
		url: "/proman_save",
		data: {
			"id": id,
		
		},
		dataType: "json",
		success: function(data) {
			if(data.code == "200") {
				console.log("succeed");
				$(man).find("img").src;
				alert("添加成员成功！！");
			}
			if(data.code == "400") {
				alert("失败！！");
			}
		},
		error: function(date) {
			console.log("asdasd");
		}
	});
}
function taskman_save(man){
	var id = man.id;
	$.ajax({
		type: "POST",
		url: "/taskman_save",
		data: {
			"id": id,
		
		},
		dataType: "json",
		success: function(data) {
			if(data.code == "200") {
				console.log("succeed");
				$(man).find("img").src;
				
				
				alert("添加成员成功！！");
			}
			if(data.code == "400") {
				alert("失败！！");
			}
		},
		error: function(date) {
			console.log("asdasd");
		}
	});
}
function taskname_update(task){
	dataCancel(task);
	var id = $(task).data("id");
	var name = $('#task_name').val();
	$.ajax({
		type: "POST",
		url: "/taskname_update",
		data: {
			"id": id,
			"name": name
		},
		dataType: "json",
		success: function(data) {
			if(data.code == "200") {
				console.log("succeed");
				$(".task_name_left a").html($('#task_name').val());
			}
			if(data.code == "400") {
				alert("失败！！");
			}
		},
		error: function(date) {
			console.log("asdasd");
		}
	});
}
function taskcontent_update(task){
	dataCancel(task);
	var id = $(task).data("id");
	var content = $('#task_content').val();
	$.ajax({
		type: "POST",
		url: "/taskcontent_update",
		data: {
			"id": id,
			"content":content
		},
		dataType: "json",
		success: function(data) {
			if(data.code == "200") {
				console.log("succeed");
				$(".text-content span").html($('#task_content').val());
			}
			if(data.code == "400") {
				alert("失败！！");
			}
		},
		error: function(date) {
			console.log("asdasd");
		}
	});
}
function taskdate_update(task){
	var id = $(task).data("id");
	var ndate = $('#task_date').val();
	$.ajax({
		type: "POST",
		url: "/taskdate_update",
		data: {
			"id": id,
			"ndate": ndate
		},
		dataType: "json",
		success: function(data) {
			if(data.code == "200") {
				console.log("succeed");
				alert("保存成功！！");
			}
			if(data.code == "400") {
				alert("失败！！");
			}
		},
		error: function(date) {
			console.log("asdasd");
		}
	});
}




function proname_update(pro){
	dataCancel(pro);
	var id = $(pro).data("id");
	var name = $('#pro_name').val();
	$.ajax({
		type: "POST",
		url: "/proname_update",
		data: {
			"id": id,
			"name": name
		},
		dataType: "json",
		success: function(data) {
			if(data.code == "200") {
				console.log("succeed");
				$(".task_name_left a").html($('#pro_name').val());
			}
			if(data.code == "400") {
				alert("失败！！");
			}
		},
		error: function(date) {
			console.log("asdasd");
		}
	});
}
function procontent_update(pro){
	dataCancel(pro);
	var id = $(pro).data("id");
	var content = $('#task_content').val();
	$.ajax({
		type: "POST",
		url: "/procontent_update",
		data: {
			"id": id,
			"content":content
		},
		dataType: "json",
		success: function(data) {
			if(data.code == "200") {
				console.log("succeed");
				$(".text-content span").html($('#pro_content').val());
			}
			if(data.code == "400") {
				alert("失败！！");
			}
		},
		error: function(date) {
			console.log("asdasd");
		}
	});
}





function newTask(a) {
	$(".new-task1").show();
	var div = $(".new-task1");
	div.animate({
		opacity: "1"
	}, "slow");
	$(".new-task-body").addClass("new-task-body1");
	uploadtask(a);
}

function taskCircle() {
	window.history.go(-1);
}

function dataMember() {
	$(".add__name_member").animate({
		height: "toggle"
	});
}

function leftMove() {
	var a = $(".task_new_content_member").width();
	var a1 = $(".task_new_content_member1").position().left;
	var b = parseInt($(".task_new_content_name_member").width());
	if(a1 < 0) {
		$(".task_new_content_member1").animate({
			left: "+=80px"
		}, "slow");
	}
}

function rightMove() {
	var a = $(".task_new_content_member").width();
	var a1 = $(".task_new_content_member1").position().left;
	var b = parseInt($(".task_new_content_name_member").width());
	if(a < b) {
		if(b + a1 >= a) {
			$(".task_new_content_member1").animate({
				left: "-=80px"
			}, "slow");
		}
	}
}

function newProject() {
	$(".new-project").show();
	var div = $(".new-project");
	div.animate({
		opacity: "1"
	}, "slow");
	$(".new-project .new-task-body").addClass("new-task-body1");
	$.ajax({
		type: "POST",
		url: "/pro_select",
		data: {
			
		},
		dataType: "json",
		success: function(data) {
			if(data.code == "200") {
				console.log("succeed");
				data.pro;
			}
		},
		error: function(date) {
			console.log("asdasd");
		}
	});
}

//点击添加任务
function newProject1() {
	$(".new-project").show();
	var div = $(".new-project");
	div.animate({
		opacity: "1"
	}, "slow");
	$(".new-project .new-task-body").addClass("new-task-body1");

}


function delectProject() {
	var div = $(".new-project");
	$(".new-project .new-task-body").removeClass("new-task-body1");
	div.animate({
		opacity: "0"
	}, "slow");
	$(".new-project").hide();
}

function saveProject() {
	var options = $("#fruit option:selected"); //获取选中的项
	var id = options.val();

	$.ajax({
		type: "POST",
		url: "/pro_update",
		data: {
			"proid": id
		},
		dataType: "json",
		success: function(data) {
			if(data.code == "200") {
				console.log("succeed");
				updatedata();
			}
			if(data.code == "412") {}
		},
		error: function(date) {
			console.log("asdasd");
		}
	});
	$(".new_content_top_left span").html(options.text());
	var div = $(".new-project");
	$(".new-project .new-task-body").removeClass("new-task-body1");
	div.animate({
		opacity: "0"
	}, "slow");
	$(".new-project").hide();

}
function saveProject1() {
	$(".new_content_top_left span").html(options.text());
	var div = $(".new-project");
	$(".new-project .new-task-body").removeClass("new-task-body1");
	div.animate({
		opacity: "0"
	}, "slow");
	$(".new-project").hide();

}