package com.Auth_Service.controller;

import com.Auth_Service.dto.ApiResponseDto;
import com.Auth_Service.dto.LoginDto;
import com.Auth_Service.dto.UserDto;
import com.Auth_Service.repository.UserRepository;
import com.Auth_Service.service.UserService;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    public UserService userService;

    public UserRepository userRepository;


    public AuthenticationManager authenticationManager;

    public AuthController(UserService userService, UserRepository userRepository,AuthenticationManager authenticationManager) {

        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto<String>> singup(@RequestBody UserDto userDto) {
        ApiResponseDto<String> response = new ApiResponseDto<>();
        if (userRepository.existsByEmail(userDto.getEmail())) {
            response.setMessage("Error");
            response.setStatus(401);
            response.setData("Email already exists");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if (userRepository.existsByUsername(userDto.getUsername())) {
            response.setMessage("Error");
            response.setStatus(401);
            response.setData("username already exists");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        UserDto user = userService.addUser(userDto);
        response.setMessage("created");
        response.setStatus(201);
        response.setData("Registration completed");

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<String>> login(@RequestBody LoginDto loginDto) {
        ApiResponseDto<String> response=new ApiResponseDto<>();
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());
        Authentication  authentication=authenticationManager.authenticate(authenticationToken);

        if(authentication.isAuthenticated()){
            response.setMessage("Login sucessful");
            response.setStatus(200);
            response.setData("User as logged");

            return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatus()));
        }
        response.setMessage("failed");
        response.setStatus(401);
        response.setData("Un-Authorized access");

        return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatus()));
    }


    }

