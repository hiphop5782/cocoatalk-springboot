package com.hacademy.cocoatalk.restcontroller;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacademy.cocoatalk.service.WebSocketUserService;

@CrossOrigin
@RestController
@RequestMapping("/nickname")
public class NicknameRestController {
	
	@Autowired
	private WebSocketUserService userService;
	
	@GetMapping("/{nickname}")
	public boolean check(@PathVariable String nickname) {
		return userService.checkNickname(nickname);
	}
	
}
