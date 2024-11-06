package com.hacademy.cocoatalk.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WebSocketUserServiceImpl implements WebSocketUserService{
	
	private Set<String> nicknames = new CopyOnWriteArraySet<>();
	private Map<String, String> sessions = Collections.synchronizedMap(new HashMap<>());
	
	@Override
	public void add(String sessionId, String nickname) {
		nicknames.add(nickname);
		sessions.put(sessionId, nickname);
		log.info("사용자 추가 [{}, {}], 현재 {}명, 세션 {}개", sessionId, nickname, nicknames.size(), sessions.size());
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
		if(!sessions.containsValue(nickname)) {
			nicknames.remove(nickname);
		}
		log.info("사용자 제거 [{}, {}], 현재 {}명, 세션 {}개", sessionId, nickname, nicknames.size(), sessions.size());
		return nickname;
	}
	@Override
	public Set<String> getList() {
		return nicknames;
	}
}
