package com.example.demo.comments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {
	@Autowired
	private final CommentsService commentsService;
	
	@Autowired
    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService; // Initialize the final field in the constructor
    }
	  
    @GetMapping("/")
    public List<Comments> getAllComments() {
        return commentsService.getAllComments();
    }

    @PostMapping("/addComment")
    public Comments addComment(@RequestBody Comments comment) {
    	System.out.println("rrrrrrrrrrrrrrrrrr");
        return commentsService.addComment(comment);
    }
}
