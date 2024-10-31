package com.hacademy.cocoatalk.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.hacademy.cocoatalk.vo.PublicUserInOutResponseVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WebSocketEventHandler {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@EventListener
	public void userEnter(SessionConnectedEvent event) {
		//헤더 정보를 추출
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());//분석해!
		String sessionId = accessor.getSessionId();
		
		Message<?> connectMessage = (Message<?>) accessor.getHeader("simpConnectMessage");
		if(connectMessage == null) return;
		
		StompHeaderAccessor connectAccessor = StompHeaderAccessor.wrap(connectMessage);
		String nickname = connectAccessor.getFirstNativeHeader("nickname");
		if(nickname == null) return;
		
		//추가 작업
		messagingTemplate.convertAndSend("/public/system", PublicUserInOutResponseVO.builder()
					.nickname(nickname)
					.action("enter")
				.build());
	}
	@EventListener
	public void userLeave(SessionDisconnectEvent event) {
		//헤더 정보를 추출
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());//분석해!
		String sessionId = accessor.getSessionId();
		
		Message<?> connectMessage = (Message<?>) accessor.getHeader("simpConnectMessage");
		if(connectMessage == null) return;
		
		StompHeaderAccessor connectAccessor = StompHeaderAccessor.wrap(connectMessage);
		String nickname = connectAccessor.getFirstNativeHeader("nickname");
		if(nickname == null) return;
		
		messagingTemplate.convertAndSend("/public/system", PublicUserInOutResponseVO.builder()
				.nickname(nickname)
				.action("leave")
			.build());
	}
}