package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer{
	
	@Override
	  public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/api/**") // Define the endpoints you want to allow access to
	            .allowedOrigins("http://localhost:3000") // Allow requests from this origin
	            .allowCredentials(true)
	            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	            .allowedHeaders("*"); // Allow all headers (you can customize this)
	    }

}
