package com.example.exercise.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.example.exercise.entity.Attachment;
import com.example.exercise.entity.ResponseData;
import com.example.exercise.helper.AttachmentHelper;
import com.example.exercise.service.AttachmentService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;

@CrossOrigin
@RestController()
public class AttachmentController {

    private AttachmentService attachmentService;
    
    @Autowired
    private AttachmentHelper attachmenthelper;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/upload")
    public ResponseData uploadFile(@RequestParam("file")MultipartFile file) throws Exception {
        Attachment attachment = null;
        String downloadURl = "";
        attachment = attachmentService.saveAttachment(file);
        downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(attachment.getId())
                .toUriString();

        return new ResponseData(attachment.getFileName(),
                downloadURl,
                file.getContentType(),
                file.getSize());
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception {
        Attachment attachment = null;
        attachment = attachmentService.getAttachment(fileId);
        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getFileName()
                + "\"")
                .body(new ByteArrayResource(attachment.getData()));
    }
    
    @GetMapping("/compare")
    public void compareFiles() throws IOException, InterruptedException {
    	attachmentService.fileCompare();
    	return;
    }
    
    @GetMapping("/frequency")
    public void frequencyOfWords() throws IOException, InterruptedException {
    	attachmentService.frequency();
    	return;
    }
    
    @PostMapping("/upload-file")
    public ResponseEntity<String> uploadFiles(@RequestParam("file")MultipartFile file) throws IOException
    {
    	
    	System.out.println(file.getContentType());
    	System.out.println(file.getResource());
    	
    	try {
    		
    	
    	//validation
    	if(file.isEmpty()) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request must contain file");
    	}
    	
    	if(!file.getContentType().equals("text/plain")) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Only text content type are allowed");
    	}
    	
    	//file upload
    	boolean f = attachmenthelper.uploadFile(file);
    	if(f)
    	{
    		return ResponseEntity.ok("File is successfully added");
    	}
    	
    	
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
   
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong! try again");
    	
    }
    
    @PostMapping("/upload2")
    public String uploadFiles1(@RequestParam("file1")MultipartFile file1, @RequestParam("file2")MultipartFile file2) throws IOException  {
		attachmentService.mergeFile(file1, file2);
		return "Uploaded Successfully";
    }
    
    @GetMapping("download")
	public ResponseEntity<Object> downloadFile() throws IOException
	{
		String filename = "C:\\\\Users\\\\nshetty\\\\eclipse-workspace\\\\ExerciseOnFile\\\\src\\\\main\\\\resources\\\\static\\\\image\\\\file3.txt";
		File file = new File(filename);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition",
				String.format("attachment; filename=\"%s\"", file.getName()));
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers)
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/txt")).body(resource);

		return responseEntity;
	}
    
}