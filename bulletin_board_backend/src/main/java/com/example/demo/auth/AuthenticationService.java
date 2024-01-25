package com.example.demo.auth;


import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.config.JwtService;
import com.example.demo.posts.Posts;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserDTO;
import com.example.demo.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private AuthenticationResponse response;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
    	try {
    		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            ));
            var user = repository.findByEmail(request.getEmail()).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setFirstname(user.getFirstname());
            userDTO.setLastname(user.getLastname());
            userDTO.setEmail(user.getEmail());
            userDTO.setRole(user.getRole());
            return AuthenticationResponse.builder().token(jwtToken).message("Successfully").data(userDTO).build();
    		
    	}catch (UsernameNotFoundException ex) {
            // Handle the case where the user was not found
            return AuthenticationResponse.builder().message("User not found").build();
        } catch (BadCredentialsException ex) {
            // Handle the case where authentication failed due to bad credentials
            return AuthenticationResponse.builder().message("Invalid credentials").build();
        }
        
    }
    @GetMapping("/getuser")
    public List<User>  getAllUser(){
    	return repository.findAll();
    }
  
    
}
