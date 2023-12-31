package com.poly.service.impl;

import java.io.File;
import java.io.FileNotFoundException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.poly.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService {
	@Autowired
	ServletContext app;

	public File save(MultipartFile file, String folder) {
		try {
			File dir = new File(app.getRealPath("/" + folder));
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String originalFileName = file.getOriginalFilename();
			File savedFile = new File(dir, originalFileName);
			file.transferTo(savedFile);
			System.out.println(savedFile.getAbsolutePath());
			return savedFile;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}