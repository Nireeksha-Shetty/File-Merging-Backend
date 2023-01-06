package com.example.exercise.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.exercise.entity.Attachment;
import com.example.exercise.entity.ResponseData;

public interface AttachmentService {

	Attachment saveAttachment(MultipartFile file) throws Exception;

	Attachment getAttachment(String fileId) throws Exception;
	
	public void fileCompare() throws IOException, InterruptedException;

	public void frequency() throws IOException, InterruptedException;

	public void mergeFile(MultipartFile file1, MultipartFile file2) throws IOException;

}
