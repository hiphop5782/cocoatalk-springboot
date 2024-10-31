package com.hacademy.cocoatalk.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PublicMessageResponseVO {
	private String sender;
	private String content;
	private LocalDateTime time;

	public static PublicMessageResponseVO createFrom(PublicMessageRequestVO request) {
		return PublicMessageResponseVO.builder()
				.content(request.getContent())
				.time(LocalDateTime.now())
			.build();
	}
}
