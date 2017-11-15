/**
 * 
 */
window.onload = function a1() {
	var s = $("input[name='check']");
	s.each(function(i) {
		$(this).click(function() {
			if(this.checked == true) {
				for(var i = 0; i < s.length; i++) {
					s[i].parentNode.style.opacity = 1;
				}
				return null;
			}
		});
	});
	$('#checkbox1').bind('click', function() {

		if($(this).is(':checked')) {
			$('input[name="check"]').each(function() {
				$(this).prop("checked", true);
			});
		} else {
			$('input[name="check"]').each(function() {
				$(this).prop("checked", false);
			});
		}
	})

}

function submitform1() {
	document.form1.submit();
}


function path(a) {
	b1 = a.data("name");
	b = a.data("filename"),
		c = a.data("path");
	if(b == null) {
		return;
	}
	var str1 = b.lastIndexOf(".");
	var s = b.substr(str1 + 1, b.length);
	if(str1 == -1) {
		s = null;
	}
	var l = c;
	if(s == null || s == "") {
		folder(b);
	} else if(s == "png" || s == "jpg" || s == "tiff" || s == "gif" || s == "pcx" || s == "tga" || s == "exif" || s == "fpx" || s == "svg" || s == "psd" || s == "cdr" || s == "pcd" || s == "raw") {
		var p = document.getElementById("display_picture");
		p.style.transition = "0.5s all";
		p.style.opacity = 1;
		p.style.display = "block";
		var p1 = document.getElementById("display_picture_h2");
		p1.innerHTML = b1;
		var p2 = document.getElementById("display_picture_img");
		p2.src = l;
	}
}

function prom() {
	var tradd = "<tr class='tradd'><td></td><td><div class='file-name'><div class='file-img'><i class='iconfont icon-wenjianjia'></i></div><input type='text' name='file_name' value='' id='file_name' />	</div></td><td></td><td></td><td></td></tr>";
	$("#list-container").prepend(tradd);
	$("#file_name").focus();
	$("#file_name").blur(function() {
		if($("#file_name")[0].value == "" || $("#file_name")[0].value == null) {
			$(".tradd").remove();
		} else {
			$.ajax({
				type: "POST",
				url: "/xj",
				data: {
					"wjname": $("#file_name")[0].value
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
		}
	});
}

function share(b) {
	$.ajax({
		type: "POST",
		url: "/share1",
		data: {
			"Fullname": b
		},
		dataType: "json",
		success: function(data) {
			if(data.code == "200") {
				console.log("succeed");
				alert("分享成功!");
			}
			if(data.code == "412") {}
		},
		error: function(date) {
			console.log("asdasd");
		}
	});
}

function folder(b) {
	$.ajax({
		type: "POST",
		url: "/route",
		data: {
			"Fullname": b
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
}

function updatedata() {
	$.ajax({
		type: "POST",
		url: "/updatedata",
		data: {},
		dataType: "json",
		success: function(data) {
			//if(data.code == "200") {
			$("#list-container").html("");
			$(".container_bottom_top").empty();
			$(".container_bottom_top").append($("<span class='span1'></span>").html(data.url));
			var a1="<a href='javascript:void(0);' onclick='Return()'; id='fh' >返回上一级</a>"
			$(".container_bottom_top").append(a1);
			
			
			
			
			
			
			for(var i in data.pu) {
				console.log("succeed");
				var puname = data.pu[i].name;
				var box = "<div class='checkbox'><input type='checkbox' id='" + i + "' name='check'><label for='" + i + "'></label></div>";
				var Filetype = filetype(puname, data.pu[i].path);
				var name = "<div class='file-name'><div class='file-img'>" + Filetype + "</div><a href='javascript:;' data-name='" + data.pu[i].name + "' data-filename='" + data.pu[i].fullname + "' data-path='" + data.pu[i].path + "' class='file-name'>" + data.pu[i].name + "</a></div>";
				var tdtime = "<td>" + data.pu[i].time + "</td>";
				var tddata = "<td>" + data.pu[i].size + "</td>";
				var fileedit = "<button class='file-edit' tabindex='0'><i class='iconfont icon-dian'></i></button>" +
					"<div class='file-edit-1'>" +
					"<form method='post' enctype='multipart/form-data' name='form2' id='form2' action='/download'><button type='submit' class='download' data-route='" + data.pu[i].route + "' data-filename='" + data.pu[i].fullname + "'>下载</button>" +
					"<input type='hidden' name='Fullname' value='" + data.pu[i].fullname + "' /> <input type='hidden' name='route' value='" + data.pu[i].route + "' /></form>" +
					"<button class='share' data-filename='" + data.pu[i].fullname + "'>分享</button>" +
					"</div>";
				var div = "<div class='file-edit_1'>" + fileedit + "</div>";

				$(".list-header").off('click', '.file-edit').on('click', ".file-edit", function(e) {
					var u = $(this).next(".file-edit-1");
					$(".file-edit-1").css("display", "none");
					if(u.css("display") == "none") {
						u.css("display", "block");
					} else {
						u.css("display", "none");
					}
					$(document).on('click', function() {
						if(u.css("display") == "block") {
							$(".file-edit-1").css("display", "none");
						}
					});
					e.stopPropagation();
				});

				$(".list-header").off('click', '.file-name').on('click', ".file-name", function(e) {
					path($(this));
					e.stopPropagation();
				});
				$(".list-header").off('click', '.share').on('click', ".share", function(e) {
					share($(this).data("filename"));
					e.stopPropagation();
				});
				var tdxz = "<td>" + div + "</td>";
				var td1 = "<td>" + box + "</td>";
				var td = "<td>" + name + "</td>";
				var tr = "<tr>" + td1 + td + tdtime + tddata + tdxz + "</tr>";
				$("#list-container").append(tr);

				$("table tr").mouseover(function() {
					var a = $(this).children();
					var b = a.children();
					b[0].style.opacity = "1";
				});
				$("table tr").mouseout(function() {
					var p = $("input[name='check']");
					p.each(function(i) {
						if(this.checked == true) {
							for(var i = 0; i < p.length; i++) {
								p[i].parentNode.style.opacity = 1;
							}
							return null;
						} else {
							$(this).parent().css("opacity", 0);
						}

					});
				});

			}
			if(data.code == "412") {}
		},
		error: function(date) {
			console.log("asdasd");
		}
	});
}

//判断文件类型
function filetype(a, b) {
	var str1 = a.lastIndexOf(".");
	var s = a.substr(str1 + 1, a.length);
	if(str1 == -1) {
		s = null;
	}
	if(s == null) {
		return "<i class='iconfont icon-wenjianjia1'></i>";
	} else if(s == "png" || s == "jpg" || s == "tiff" || s == "gif" || s == "pcx" || s == "tga" || s == "exif" || s == "fpx" || s == "svg" || s == "psd" || s == "cdr" || s == "pcd" || s == "raw") {
		return "<img src='" + b + "' />";
	} else if(s == "rar" || s == "zip" || s == "cab" || s == "iso" || s == "jar" || s == "ace" || s == "7z" || s == "tar" || s == "gz" || s == "arj" || s == "lzh" || s == "uue") {
		return "<i class='iconfont icon-yasuobao'></i>";
	} else {
		return "<i class='iconfont icon-wenjian'></i>";
	}
}

//重命名
function rename(b) {
	var tradd = "<input type='text' name='file_name' value='' id='file_name' />";
	$("#list-container").prepend(tradd);
	$("#file_name").focus();
	$("#file_name").blur(function() {
		if($("#file_name")[0].value == "" || $("#file_name")[0].value == null) {
			$(".tradd").remove();
		} else {
			$.ajax({
				type: "POST",
				url: "/rename",
				data: {
					"Fullname": b
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
		}
	});

}

function delect(b) {
	$.ajax({
		type: "POST",
		url: "/delect",
		data: {
			"Fullname": b
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
}
//返回上一级
function Return() {
	$.ajax({
		type: "POST",
		url: "/fh",
		data: {
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
}





function circle() {
	var a = document.getElementById("display_picture");
	a.style.opacity = "0";
	setTimeout(function() {
		a.style.display = "none";
	}, 1000);
}