package com.hacademy.cocoatalk.service;

public interface WebSocketUserService {
	void add(String sessionId, String nickname);
	boolean checkNickname(String nickname);
	boolean checkSessionId(String sessionId);
	String remove(String sessionId);
}
