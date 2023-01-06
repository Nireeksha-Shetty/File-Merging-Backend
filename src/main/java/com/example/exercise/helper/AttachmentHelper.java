package com.example.exercise.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class AttachmentHelper {
	
	public final String UPLOAD_DIR = "C:\\Users\\nshetty\\eclipse-workspace\\ExerciseOnFile\\src\\main\\resources\\static\\image";
	public boolean uploadFile(MultipartFile multipart){
		boolean f= false;
		try {
			InputStream is = multipart.getInputStream();
			byte data[]= new byte[is.available()];
			
			is.read(data);
			
			FileOutputStream fos = new FileOutputStream(UPLOAD_DIR+File.separator+multipart.getOriginalFilename());
			fos.write(data);
			
			fos.flush();
			fos.close();
//			Files.copy(multipart.getInputStream(), UPLOAD_DIR+File.separator+multipart.getOriginalFilename(), StandardCopyOption.REPLACE_EXISTING);
//			Files.copy(multipart.getInputStream(), UPLOAD_DIR+File.separator+multipart.getOriginalFilename(), StandardCopyOption.REPLACE_EXISTING);
			f=true;
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return f;
	}

}
