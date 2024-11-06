package com.hacademy.cocoatalk.service;

import java.util.Set;

public interface WebSocketUserService {
	void add(String sessionId, String nickname);
	boolean checkNickname(String nickname);
	boolean checkSessionId(String sessionId);
	String remove(String sessionId);
	Set<String> getList();
}
