package com.hacademy.cocoatalk.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PublicFileResponseVO {
	private String sender;
	private String fileName;
	private String realName;
	private long fileSize;
	private String fileType;
	private LocalDateTime time;
	private LocalDateTime expire;
}
