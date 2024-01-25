package com.example.demo.auth;


import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.JwtService;
import com.example.demo.exception.ApiRequestException;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final JwtService jwtService;
    private final UserRepository repository;

    private AuthenticationResponse response; 
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) { 

    	boolean isEmailInUse = repository.existsByEmail(request.getEmail());
        if (isEmailInUse) {
//             throw new ApiRequestException("EMAIL ALREADY USED!");
        	// response = service.register(request);
        	 var user = repository.findByEmail(request.getEmail()).orElseThrow();
        	 String existingToken = jwtService.generateToken(user);
        	 response.setToken(existingToken);
        	 response.setMessage("This email is already in use");
        	 response.setData(request);
        	 
        	 return new ResponseEntity<AuthenticationResponse>(response, HttpStatus.BAD_REQUEST);
         }else {
        	 response = service.register(request);
        	 response.setMessage("REGISTRATION ");
        	 response.setData(request);
        	 
        	 return new ResponseEntity<AuthenticationResponse>(response, HttpStatus.CREATED);
         }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
      //  return ResponseEntity.ok(service.authenticate(request));
     response = service.authenticate(request);
   	// response.setData(request);
   	 
   	 return new ResponseEntity<AuthenticationResponse>(response, HttpStatus.CREATED);
    }
}
