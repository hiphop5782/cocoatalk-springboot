package com.hacademy.cocoatalk.restcontroller;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/nickname")
public class NicknameRestController {
	
	private Set<String> users = new CopyOnWriteArraySet<>();
	
	@GetMapping("/{nickname}")
	public boolean check(@PathVariable String nickname) {
		return users.contains(nickname); 
	}
	
	@PostMapping("/{nickname}")
	public boolean insert(@PathVariable String nickname) {
		return users.add(nickname);
	}
	
}
