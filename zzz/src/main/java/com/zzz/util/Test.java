package com.zzz.util;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 String sr=HttpUtil.sendPost("http://10.1.65.33:81/login", "account=testtest&password=123456");
	        System.out.println(sr);
	}

}
