package com.test.Test.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException ex) throws IOException {
         String err = (String) req.getAttribute("exception");
         if("expired_token".equals(err))
         {
             res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
             res.getWriter().write("""
                     {"error" : "The token is expired"}""");
         }
         else if("invalid_token".equals(err)){
             res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
             res.getWriter().write("""
                     {"error" : "Invalid token"}""");
         }
         else {
             res.getWriter().write("""
                    {"error": "Unauthorized"}
                    """);
         }
    }


}
