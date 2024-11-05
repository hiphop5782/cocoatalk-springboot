package com.hacademy.cocoatalk.service;

import java.io.File;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FileCleanServiceImpl implements FileCleanService{
	
	public static final String PATH = "cocoatalk/upload";
	
	@Scheduled(cron = "0 0 0 * * *")
	@Override
	public void removeExpiredFile() {
		File dir = new File(System.getProperty("user.home"), PATH);
		if(!dir.exists()) return;
		
		File[] fileList = dir.listFiles();
		for(File target : fileList) {
			long gap = System.currentTimeMillis() - target.lastModified();
			if(gap >= 24 * 60 * 60 * 1000L) {
				target.delete();
			}
		}
	}
	
}
