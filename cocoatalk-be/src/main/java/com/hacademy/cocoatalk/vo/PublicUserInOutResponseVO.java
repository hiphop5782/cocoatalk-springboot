package com.hacademy.cocoatalk.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class PublicUserInOutResponseVO {
	private String nickname;
	private String action;
}
