package com.zzz.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.zzz.model.Photo;
import com.zzz.model.Route;
import com.zzz.model.User;
import com.zzz.repository.PhotoRepository;

@Controller
public class PhotoController {
	@Autowired
	private PhotoRepository photoRepository;

	@GetMapping("/upload")
	public String input(Model model, HttpServletRequest request, HttpSession session) {
//		if (session.getAttribute("user") == null) {// 判断是否登录
//			return "index";
//		}
		session.setAttribute("id","aa58bfeb-cc9c-4562-a2c7-b13f8a21c5cf");
		String id = (String) session.getAttribute("id");// 获取id
		String route = null;
		String luj = (String) session.getAttribute("route");// 获取路径
		String url1 = (String) session.getAttribute("url");// 获取url
		List<Route> us = new ArrayList<Route>();
		String url = null;
		if (luj != null) {
			route = luj;
		} else {
			route = request.getSession().getServletContext().getRealPath(id);// 如果路径不存在就得到服务器路径
		}
		if (url1 != null) {
			url = "..\\" + id + url1;// 当前路径
		} else {
			url = "..\\" + id;// 当前路径
		}
		File root = new File(route);
		File[] roots = root.listFiles();
		if (roots != null) {
			for (File f : roots) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 时间格式
				Calendar date = Calendar.getInstance();
				date.setTimeInMillis(f.lastModified());// 将文件的时间进行处理
				String a[] = f.getName().split("-");// 将文件名进行处理
				String route1 = f.getAbsolutePath();
				List<Photo> list = photoRepository.register(f.getName());
				String Path = null;
				if (a.length == 2) {
					Photo photo = (Photo) list.get(0);
					Path = photo.getPath();
					us.add(new Route(a[1], df.format(date.getTime()), f.length() / 1024f + "KB", Path, f.getName(),
							route1));// 文件名 修改时间 大小 图片路径 全名 当前路径 添加到数组
				} else {
					us.add(new Route(f.getName(), df.format(date.getTime()), f.length() / 1024f + "KB", Path,
							f.getName(), route1));
				}
			}
		}
		model.addAttribute("url", url);
		model.addAttribute("route1", route);
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("pu", us);
		return "drive";
	}

	@PostMapping("/upload") // 上传
	public String upload(HttpServletRequest request, @RequestParam("file") List<MultipartFile> files,
			HttpSession session) {
		String id = (String) session.getAttribute("id");
		String luj = (String) session.getAttribute("route");
		String url1 = (String) session.getAttribute("url");
		String url = null;
		if (url1 != null) {
			url = "..\\" + id + url1 + "\\";
		} else {
			url = "..\\" + id + "\\";
		}
		String route = null;
		if (luj != null) {
			route = luj;
		} else {
			route = request.getSession().getServletContext().getRealPath(id);
		}
		Path path = Paths.get(route);
		if (!path.toFile().exists()) {
			path.toFile().mkdirs();
		}
		for (MultipartFile file : files) {
			if (null != file && !StringUtils.isEmpty(file.getOriginalFilename())) {
				String extname = FilenameUtils.getExtension(file.getOriginalFilename());
				System.out.println("扩展名：" + extname);
				String name = FilenameUtils.getName(file.getOriginalFilename());
				System.out.println("文件名：" + name);
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time = sdf1.format(date);
				String now = sdf.format(date);
				String basename = UUID.randomUUID().toString().substring(0, 8);
				String newname = now + basename + "-" + name;// 文件名用-隔开
				Path filename = path.resolve(newname);
				String size = file.getSize() / 1024f + "KB";// 大小计算
				System.out.println("文件大小：" + size);
				try {
					Files.copy(file.getInputStream(), filename);
					Photo photo = new Photo();
					User user = new User();
					user.setId(id);
					photo.setUser(user);
					photo.setId(UUID.randomUUID().toString());
					photo.setPath(url + newname);
					photo.setTitle(newname);
					photo.setName(name);
					photo.setTime(time);
					photo.setSize(size);
					photo.setShare("0");
					photoRepository.save(photo);// 写入数据库
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "redirect:/upload";
	}

	@PostMapping("/download") // 下载
	public void fileDownload(String route, String Fullname, HttpServletRequest request, HttpServletResponse response) {
		String fileName = Fullname;
		try {
			fileName = new String(fileName.getBytes("iso-8859-1"), "utf-8");
			String realname = fileName.substring(fileName.indexOf("-") + 1);
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
			FileInputStream in = new FileInputStream(route);
			OutputStream out = response.getOutputStream();
			byte buffer[] = new byte[102400];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			in.close();
			out.close();
		} catch (Exception e) {
		}
	}

	@PostMapping("/xs") // 新建文件夹
	public String xs(String route, String Fullname, HttpSession session) {
		String route1 = route + "\\" + Fullname;
		String url = null;
		String url1 = (String) session.getAttribute("url");
		if (url1 == null) {
			url = "\\" + Fullname;
		} else {
			url = url1 + "\\" + Fullname;
		}
		session.setAttribute("url", url);
		session.setAttribute("route", route1);
		return "redirect:/upload";
	}

	@PostMapping("/fh") // 返回上一级
	public String fh(String route, HttpSession session, HttpServletRequest request) {
		String parentPath = null;
		String parentPath1 = null;
		String id = (String) session.getAttribute("id");
		String url = (String) session.getAttribute("url");
		parentPath1 = request.getSession().getServletContext().getRealPath(id);
		if (parentPath1.equals(route)) {
		} else {
			File file = new File(route);
			parentPath = file.getParent();
			session.setAttribute("route", parentPath);
		}
		if (url != null) {
			File file = new File(url);
			String parentPath2 = null;
			parentPath2 = file.getParent();
			if (parentPath2.equals("\\")) {
				parentPath2 = null;
				session.setAttribute("url", parentPath2);
			} else {
				session.setAttribute("url", parentPath2);
			}
		}
		return "redirect:/upload";
	}

	@PostMapping("/xj") // 新建
	public String xj(String route, String wjname, HttpSession session) {
		String route1 = route + "\\" + wjname;
		File file = new File(route1);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		} else {
		}
		return "redirect:/upload";
	}

	@PostMapping("/fxxz") // 分享下载
	public void download(String Fullname, String route, String wjname, HttpServletRequest request,
			HttpServletResponse response) {
		route = route.substring(3, route.length());
		route = request.getSession().getServletContext().getRealPath("") + route;
		fileDownload(route, Fullname, request, response);
	}

}
