package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.user.User;

import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
	@Bean
	 public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
	    http.authorizeHttpRequests(authz -> authz
	            .requestMatchers(
	            new AntPathRequestMatcher("/api/register/**"),
	            new AntPathRequestMatcher("/api/post/upload/**"),
	            new AntPathRequestMatcher("/api/post/createPost/**"),
	            new AntPathRequestMatcher("/api/post/createPost/1/**"),
	            new AntPathRequestMatcher("/api/post/getAllPosts/**"),
	            new AntPathRequestMatcher("/api/post/index/**"),
	            new AntPathRequestMatcher("/api/authenticate/**"),
	            new AntPathRequestMatcher("/api/comments/addComment/**"),
	            new AntPathRequestMatcher("/api/post/downloadFile/1/**"),
	            new AntPathRequestMatcher("/h2-console/**"))
	            .permitAll()
	            .anyRequest().authenticated())
	    .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
	    http.headers().frameOptions().disable();
	    return http.build();
	  }
}