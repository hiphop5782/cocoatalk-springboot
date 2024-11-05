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
import java.util.UUID;

import org.springframework.core.io.ByteArrayResource;
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

import com.hacademy.cocoatalk.vo.AttachmentUploadRequestVO;
import com.hacademy.cocoatalk.vo.AttachmentUploadResponseVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/attachment")
public class AttachmentRestController {
	
	public static final String PATH = "cocoatalk/upload";
	
	@PostMapping("/")
	public AttachmentUploadResponseVO upload(@RequestBody AttachmentUploadRequestVO request) {
		File dir = new File(System.getProperty("user.home"), PATH);
		if(!dir.exists()) dir.mkdirs();
		
		File target = new File(dir, UUID.randomUUID().toString() + "-" + request.getFileName());
		try (OutputStream out = new FileOutputStream(target)) {
			String base64Str = request.getFileContent().replaceFirst("^data:image/\\w+;base64,", "");
			byte[] profileBinary = Base64.getDecoder().decode(base64Str);
			out.write(profileBinary);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return AttachmentUploadResponseVO.builder()
					.fileName(target.getName())
					.fileSize(target.length())
				.build();
	}
	
	@GetMapping("/{filename}")
	public ResponseEntity<ByteArrayResource> download(@PathVariable String filename) throws IOException {
		File dir = new File(System.getProperty("user.home"), PATH);
		if(!dir.exists()) return ResponseEntity.notFound().build();
		
		File target = new File(dir, filename);
		if(!target.exists()) return ResponseEntity.notFound().build();
		
		Path path = Paths.get(target.getAbsolutePath());
		byte[] binary = Files.readAllBytes(path);
		ByteArrayResource resource = new ByteArrayResource(binary);
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.contentLength(binary.length)
				.header(HttpHeaders.CONTENT_DISPOSITION, 
					ContentDisposition.attachment().filename(filename.substring(37), StandardCharsets.UTF_8).build().toString()
				)
				.body(resource);
	}
}









