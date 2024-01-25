package com.example.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
	
//	@Query(name = "SELECT * FROM TBL_USER WHERE EMAIL = ?1")
    Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);
	boolean findByEmailAndPassword(String email, String password);

	Optional<User> findById(Long userId);
	
	List<User>findAll();
}
