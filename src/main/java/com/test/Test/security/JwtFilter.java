package com.test.Test.security;

import com.test.Test.services.UserServiceImpl;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(token ==null || !token.startsWith("Bearer "))
        {
            filterChain.doFilter(request,response);
            return;

        }

        String jwtToken = token.substring(7);
        String username = jwtUtils.extractUsername(jwtToken);
        UserDetails user = userDetailsService.loadUserByUsername(username);

        if(SecurityContextHolder.getContext().getAuthentication() == null){
            if(jwtUtils.vaildateToken(jwtToken,user))
            {
                UsernamePasswordAuthenticationToken uernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(uernamePasswordAuthenticationToken);
            }else{
                throw new BadCredentialsException("Invalid Token");
            }
        }

        filterChain.doFilter(request,response);
        return ;

    }
}
