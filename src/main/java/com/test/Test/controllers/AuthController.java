package com.test.Test.controllers;

import com.test.Test.dtos.SignUpDTO;
import com.test.Test.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignUpDTO signUpDTO) {
        try {
            authService.signUp(signUpDTO.getEmail(), signUpDTO.getPassword());
            return ResponseEntity.ok(Map.of("message", "User created successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal server error"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?>logIn(@RequestBody SignUpDTO signUpDTO)
    {
        try{
           String TOKEN = authService.login(signUpDTO.getEmail(), signUpDTO.getPassword());
           Map<String,Object> res = new HashMap<>();
            res.put("token",TOKEN);
            res.put("message", "User successfully logged in");
           return ResponseEntity.status(HttpStatus.OK).body(res);
        }
        catch(IllegalArgumentException ex)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal server error"));
        }
    }

}
