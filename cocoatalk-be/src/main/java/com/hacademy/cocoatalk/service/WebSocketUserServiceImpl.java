package com.hacademy.cocoatalk.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Service;

@Service
public class WebSocketUserServiceImpl implements WebSocketUserService{
	
	private Set<String> nicknames = new CopyOnWriteArraySet<>();
	private Map<String, String> sessions = Collections.synchronizedMap(new HashMap<>());
	
	@Override
	public void add(String sessionId, String nickname) {
		nicknames.add(nickname);
		sessions.put(sessionId, nickname);
	}
	@Override
	public boolean checkNickname(String nickname) {
		return nicknames.contains(nickname);
	}
	@Override
	public boolean checkSessionId(String sessionId) {
		return sessions.containsKey(sessionId);
	}
	@Override
	public String remove(String sessionId) {
		String nickname = sessions.remove(sessionId);
		nicknames.remove(nickname);
		return nickname;
	}
	
}
