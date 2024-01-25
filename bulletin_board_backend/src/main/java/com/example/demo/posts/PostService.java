package com.example.demo.posts;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@Service
public class PostService {
	
	@Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Posts createPost(Long userId, String description, String fileName, MultipartFile file, String extension) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceAccessException("User not found with id: " + userId));
		  Date date = new Date();
		  Posts posts = new Posts();
        posts.setUser(user);
        posts.setContent(description);
        posts.setFileName(fileName);
        posts.setFileExtension(extension);
        posts.setPostedDate(date);
        try {

            byte[] imageData = file.getBytes();
        	posts.setImageData(imageData);
		//	posts.setImageData(ImageUtils.compressImage(file.getBytes()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return postRepository.save(posts);
    }
//    public String uploaImage(MultipartFile file) {
//    	Posts imageData= userRepository.save(Posts.builder().name().type().Posts());
//    			
//    	return null;
//    	
//    }
//    public Posts createPost(Long userId, Posts posts) {
//        User user = userRepository.findById(userId)
//            .orElseThrow(() -> new ResourceAccessException("User not found with id: " + userId));
//
//        posts.setUser(user);
//        return postRepository.save(posts);
//    }

    public List<Posts> getAllPosts() {
    	Sort sort = Sort.by(Sort.Order.desc("postedDate")); 
        return postRepository.findAll(sort);
    }
    
	public byte[] downloadImage(Long id) {
		Optional<Posts> dbFileData = postRepository.findById(id);
		byte[] fileData = ImageUtils.decompressImage(dbFileData.get().getImageData());
		return fileData;
	}

}
