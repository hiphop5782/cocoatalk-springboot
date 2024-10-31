package com.hacademy.cocoatalk.restcontroller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hacademy.cocoatalk.vo.ProfileUploadRequestVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/profile")
public class ProfileRestController {
	
	@PostMapping("/")
	public void upload(@RequestBody ProfileUploadRequestVO request) {
		File dir = new File(System.getProperty("user.home"), "profile");
		if(!dir.exists()) dir.mkdirs();
		
		File target = new File(dir, request.getNickname());
		System.out.println(target.getAbsolutePath());
		try (OutputStream out = new FileOutputStream(target)) {
			String base64Str = request.getProfile().replaceFirst("^data:image/\\w+;base64,", "");
			byte[] profileBinary = Base64.getDecoder().decode(base64Str);
			out.write(profileBinary);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/{nickname}")
	public ResponseEntity<ByteArrayResource> download(@PathVariable String nickname) throws IOException {
		System.out.println("nickname = " + nickname);
		try {
			File dir = new File(System.getProperty("user.home"), "profile");
			if(!dir.exists()) throw new Exception();
			
			File target = new File(dir, nickname);
			if(!target.exists()) throw new Exception();
			
			Path path = Paths.get(target.getAbsolutePath());
			byte[] binary = Files.readAllBytes(path);
			ByteArrayResource resource = new ByteArrayResource(binary);
			
			return ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.contentLength(binary.length)
					.header(HttpHeaders.CONTENT_DISPOSITION, 
						ContentDisposition.attachment().filename(nickname, StandardCharsets.UTF_8).build().toString()
					)
					.body(resource);
		}
		catch(Exception e) {
			ClassPathResource cls = new ClassPathResource("static/images/user.jpg");
			
			Path path = Paths.get(cls.getFile().getAbsolutePath());
			byte[] binary = Files.readAllBytes(path);
			ByteArrayResource resource = new ByteArrayResource(binary);
			
			return ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.contentLength(binary.length)
					.header(HttpHeaders.CONTENT_DISPOSITION, 
						ContentDisposition.attachment().filename(nickname, StandardCharsets.UTF_8).build().toString()
					)
					.body(resource);
		}
	}
	
}
