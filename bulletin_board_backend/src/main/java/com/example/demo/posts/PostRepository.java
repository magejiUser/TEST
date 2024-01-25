package com.example.demo.posts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.user.User;

public interface PostRepository extends JpaRepository<Posts, Long>{
	
	Optional<Posts> findById(Long id);

}
