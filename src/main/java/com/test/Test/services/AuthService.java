package com.test.Test.services;
import com.test.Test.models.User;
import com.test.Test.repos.UserRepo;
import com.test.Test.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    public void signUp(String email, String password) {

        Optional<User> existingUser = userRepo.findByEmail(email);

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }

        User newUser = new User(email, passwordEncoder.encode(password));
        userRepo.save(newUser);
    }

    public String login(String email, String password) {
        if(email.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Email or password is empty");
        }
        Optional<User> existingUser = userRepo.findByEmail(email);
        if(existingUser.isEmpty()) {
            throw new IllegalArgumentException("Email does not exist");
        }

        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email,password)
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            return jwtUtils.generateToken(userDetails);
        }catch(BadCredentialsException e){
            //auth automatically gives BadCredentialsException if the password is wrong.
            //So we can handle it in this way.
            throw new IllegalArgumentException("Invalid email or password");
        }

    }
}
