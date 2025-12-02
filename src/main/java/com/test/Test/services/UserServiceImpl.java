package com.test.Test.services;

import com.test.Test.models.User;
import com.test.Test.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = (User) userRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User does not exist"));
        return new  org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),
                List.of(new SimpleGrantedAuthority( "ROLE_" +user.getRole())));
    }
}
