package com.example.demo.posts;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/post")
public class PostController {
	 @Autowired
	 private PostService postService;
	 
	 @PostMapping(value="/createPost/{userId}")
	 public ResponseEntity<?> createPost(@PathVariable Long userId, 
	    	   @RequestParam("description") String model,@RequestParam("image")MultipartFile file) throws JsonMappingException, JsonProcessingException {
	     //file name uploaded
		 System.out.println(model);
		  String fileName = file.getOriginalFilename();
		//  split file name to get extension and name
		  String[] parts = fileName.split("\\.");
		  String originalFileName = parts[0];
		  String extension =parts[1];
		//current time dat  format  
		  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		  Date date = new Date();
		  String CurrentTime=dateFormat.format(date);
		  String newFileName=CurrentTime+"."+extension;
		  //Change string to object
//		  ObjectMapper mapper = new ObjectMapper();
//		  Posts posts = mapper.readValue(model, Posts.class);
		  //insert data to database using service post
		  Posts createdPost = postService.createPost(userId, model, newFileName, file, extension);
	     //upload file   
	        try {
	        	
	            file.transferTo( new File("C:\\cupia_training\\bulletin_board\\front_end\\public\\uploadedfile\\" +newFileName));
	          } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	          } 
	        
	        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
	      //  return ResponseEntity.ok("File uploaded successfully.");
	    }
	  
	 @GetMapping("/getAllPosts")
	 public ResponseEntity<List<Posts>> getAllPosts() {
        List<Posts> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
	 
	@PostMapping("/upload/{userId}") 
	public ResponseEntity<?> handleFileUpload(@PathVariable Long userId, @RequestParam("description") String model, @RequestParam("file") MultipartFile file ) throws IOException
	{
	System.out.println("Hussein");
	  String fileName = file.getOriginalFilename();

  	  String filePath="C:\\upload\\";
			//  split file name to get extension and name
			  String[] parts = fileName.split("\\.");
			  String originalFileName = parts[0];
			  String extension =parts[1];
			//current time dat  format  
			  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
			  Date date = new Date();
			  String CurrentTime=dateFormat.format(date);
			  String newFileName=CurrentTime+"."+extension;
			  Posts createdPost = postService.createPost(userId, model, newFileName, file, extension);
//		    try {
//		
//		      file.transferTo( new File(filePath + newFileName));
//		      System.out.println("");
//		    } catch (Exception e) {
//		      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		    } 
		    return ResponseEntity.ok("File uploaded successfully.");
 }
	 @GetMapping("/downloadFile/{fileName}")
	public ResponseEntity<?> downloadFile(@PathVariable Long fileName){
		byte[] fileData = postService.downloadImage(fileName);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("image/png"))
				.body(fileData);
		
	}
	
	 @GetMapping("/index")
	  public String hello() {
	    return "uploader";
	  }
}
