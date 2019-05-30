package com.yanmouxie.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping("/greeting")
	public String test() {
		return "Hello World";
	}
	
	@RequestMapping("/user")
    public String user(){
		String user ="{\"name123\" : { \"wechat\" : \"yanmouxie\" , \"website\" : \"Http://yanmouxie.coM\"} }";
		System.out.println("/user >>> " + user);
        return user;
    }
}
