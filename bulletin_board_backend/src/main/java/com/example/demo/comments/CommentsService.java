package com.example.demo.comments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentsService {
	
	private final CommentsRepository commentsRepository;
	
    @Autowired
    public CommentsService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }
	
	 public Comments addComment(Comments comment) {
        // You can add additional logic here if needed
        return commentsRepository.save(comment);
    }
	 public List<Comments> getAllComments() {
        return commentsRepository.findAll();
    }
}
