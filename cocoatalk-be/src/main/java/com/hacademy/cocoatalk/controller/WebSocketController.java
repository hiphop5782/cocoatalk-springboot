package com.hacademy.cocoatalk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hacademy.cocoatalk.vo.PublicMessageRequestVO;
import com.hacademy.cocoatalk.vo.PublicMessageResponseVO;
import com.hacademy.cocoatalk.vo.PublicProfileRequestVO;

import lombok.extern.slf4j.Slf4j;

/**
 Message Mapping Plan
 1. public chat (from. /talk/public/ooo)
  - /public/messages - 채팅 메세지 공유 채널
  - /public/alerts - 알림 공유 채널
  - /public/users - 사용자 공유 채널
 2. group chat (from. /talk/group/ooo
  - /group/{id}/messages - 채팅 메세지 공유 채널
  - /group/{id}/alerts - 알림 공유 채널
  - /group/{id}/users - 사용자 공유 채널
  - /group/{id}/command - 그룹 관련 명령 채널
 */
@Slf4j
@Controller
public class WebSocketController {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping(/*/talk*/"/public/messages")
	public void publicMessage(StompHeaderAccessor headerAccessor, Message<PublicMessageRequestVO> message) {
		String nickname = headerAccessor.getFirstNativeHeader("nickname");
		if(nickname == null) return;
		
		//get body
		PublicMessageRequestVO request = message.getPayload();
		
		//convert
		PublicMessageResponseVO response = PublicMessageResponseVO.createFrom(request);
		response.setSender(nickname);
		
		//send to channel
		simpMessagingTemplate.convertAndSend("/public/messages", response);
	}
	
}
