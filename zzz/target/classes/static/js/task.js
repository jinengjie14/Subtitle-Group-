function uploadtask(task){
	var task_id =task.id;
$.ajax({
		type: "POST",
		url: "/updatetask",
		data: {
			"task_id":task_id
		},
		dataType: "json",
		success: function(data) {
			//if(data.code == "200") {
				$(".new_content_top_left span").html(data.details.project.pro_name);
				$(".task_name_left a").html(data.details.name);
				$(".text-content-describe").html(data.details.content);
				for(var i in data.man){
					var a1="<img src="+data.man[i].path+" />";
					$(".task_new_content_name_member").append(a1);
				}
				for(var i in data.user){
					var li="<li  th:id="+data.user[i].user_id+" onclick='taskman_save(this)'><img th:src="+data.user[i].path+" src="+data.user[i].path+" />"
											+ "<div class='add__name_title' th:text="+add__name_title+">"+data.user[i].name+"</div>"
										+"</li>";
					$(".task_new_content_name_member").append(li);					
				}
				for(var i in data.comment){
					var a="<li>"
					+ "<span class='image'><img src="+ data.comment[i][2] +" src='images/preview.jpg'></span>"
						 + "<span><span>"+ data.comment[i][1] +"</span><span  class='time'>"+ data.comment[i][0].date +"</span></span>"
						 + "<span class='message'>"+ data.comment[i][0].comment_content +"</span>"
						 + "</li>";
					$(".text_name_comment_member ul").append(a);
				}
				$(".text-footer span").append(data.details.date);
			//}
			if(data.code == "412") {}
		},
		error: function(date) {
			console.log("asdasd");
				}
			});
}