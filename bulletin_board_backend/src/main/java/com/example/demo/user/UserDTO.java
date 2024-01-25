package com.example.demo.user;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UserDTO {
	
	private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Role role;
    

}
