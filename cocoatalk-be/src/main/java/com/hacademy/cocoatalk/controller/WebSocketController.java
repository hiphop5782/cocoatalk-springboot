package com.hacademy.cocoatalk.controller;

import java.io.File;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.hacademy.cocoatalk.vo.PublicFileRequestVO;
import com.hacademy.cocoatalk.vo.PublicFileResponseVO;
import com.hacademy.cocoatalk.vo.PublicMessageRequestVO;
import com.hacademy.cocoatalk.vo.PublicMessageResponseVO;

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
	
	
	@MessageMapping(/*/talk*/"/public/files")
	public void publicFiles(StompHeaderAccessor headerAccessor, Message<PublicFileRequestVO> message) {
		String nickname = headerAccessor.getFirstNativeHeader("nickname");
		if(nickname == null) return;
		
		//get body
		PublicFileRequestVO request = message.getPayload();
		
		//calculate
		File dir = new File(System.getProperty("user.home"), "cocoatalk/upload");
		File target = new File(dir, request.getFileName());
		if(!target.exists()) return;
		
		String uploadFileName = request.getFileName().substring(36+1);
		int dot = request.getFileName().lastIndexOf(".");
		String type = dot == -1 ? null : request.getFileName().substring(dot+1);
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expire = now.plusDays(1L);
		
		//send to channel
		simpMessagingTemplate.convertAndSend("/public/files", PublicFileResponseVO.builder()
					.sender(nickname)
					.fileName(uploadFileName)
					.realName(request.getFileName())
					.fileSize(target.length())
					.fileType(type)
					.time(now)
					.expire(expire)
				.build());
	}
	
}
