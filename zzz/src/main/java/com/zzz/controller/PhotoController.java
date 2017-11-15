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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.ResponseBody;
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
	public String input(Model model, HttpSession session) {
		if (session.getAttribute("user") == null) {// 判断是否登录
			return "index";
		}
		String path = (String) session.getAttribute("path");
		String name = (String) session.getAttribute("name");
		model.addAttribute("name", name);
		model.addAttribute("path", path);
		model.addAttribute("user", session.getAttribute("user"));
		return "drive";
	}

	@PostMapping("/updatedata")
	@ResponseBody
	public Map<String, Object> updatedata(HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = (String) session.getAttribute("user_id");// 获取id
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
			url = "文件管理" + url1;// 当前路径
		} else {
			url = "文件管理";// 当前路径
		}
		session.setAttribute("route", route);
		File root = new File(route);
		File[] roots = root.listFiles();
		if (roots != null) {
			for (File f : roots) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 时间格式
				Calendar date = Calendar.getInstance();
				date.setTimeInMillis(f.lastModified());// 将文件的时间进行处理
				String a[] = f.getName().split("--__--__--__--");// 将文件名进行处理
				String route1 = f.getAbsolutePath();

				List<Photo> list = photoRepository.register(f.getName());
				String Path = null;
				String size = size1(f.length());
				if (a.length == 2) {
					Photo photo = (Photo) list.get(0);
					Path = photo.getPath();
					us.add(new Route(a[1], df.format(date.getTime()), size, Path, f.getName(), route1));// 文件名																					// 添加到数组
				} else {
					us.add(new Route(f.getName(), df.format(date.getTime()), size, Path, f.getName(), route1));
				}
			}
		}
		map.put("url", url);
		map.put("pu", us);
		map.put("route", route);
		map.put("code", "200");
		return map;
	}

	@PostMapping("/upload") // 上传
	public String upload(HttpServletRequest request, @RequestParam("file") List<MultipartFile> files,
			HttpSession session) {
		String id = (String) session.getAttribute("user_id");
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
				String newname = now + basename + "--__--__--__--" + name;// 文件名用-隔开
				Path filename = path.resolve(newname);
				String size = file.getSize() / 1024f + "KB";// 大小计算
				System.out.println("文件大小：" + size);
				try {
					Files.copy(file.getInputStream(), filename);
					Photo photo = new Photo();
					User user = new User();
					user.setUser_id(id);
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
	public void fileDownload(String route, String Fullname, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		String fileName = Fullname;
		try {
			fileName = new String(fileName.getBytes("iso-8859-1"), "utf-8");
			String realname = fileName.substring(fileName.indexOf("--__--__--__--") + 14);
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

	@PostMapping("/route") // 进入文件路径
	@ResponseBody
	public Map<String, Object> route(String Fullname, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String route = (String) session.getAttribute("route");
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
		map.put("code", "200");
		return map;
	}

	@PostMapping("/fh") // 返回上一级
	@ResponseBody
	public Map<String, Object> fh(HttpSession session, HttpServletRequest request) {
		String route = (String) session.getAttribute("route");
		Map<String, Object> map = new HashMap<String, Object>();
		String parentPath = null;
		String parentPath1 = null;
		String id = (String) session.getAttribute("user_id");
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
		map.put("code", "200");
		return map;
	}

	@PostMapping("/xj") // 新建文件夹
	@ResponseBody
	public Map<String, Object> xj(String wjname, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		String luj = (String) session.getAttribute("route");
		String route1 = luj + "\\" + wjname;
		File file = new File(route1);
		if (!file.exists() && !file.isDirectory()) {// 如果文件不存在则创建
			file.mkdir();
			map.put("code", "200");// 成功创建文件夹
		} else {
			map.put("code", "412");
		}
		return map;
	}

	@PostMapping("/fxxz") // 分享下载
	public void download(String Fullname, String route, String wjname, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		route = route.substring(3, route.length());
		route = request.getSession().getServletContext().getRealPath("") + route;
		fileDownload(route, Fullname, request, response, session);
	}

	// 计算文件大小
	public String size1(double siz) {
		DecimalFormat df = new DecimalFormat("######0.00");
		String size;
		String[] p = { "Byte", "KB", "MB", "GB", "TB", "EB" };
		int i = 0;
		while (siz > 1024) {
			siz /= 1024;
			i++;
		}
		size = df.format(siz) + p[i];
		return size;
	}

}
