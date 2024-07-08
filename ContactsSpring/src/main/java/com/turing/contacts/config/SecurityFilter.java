package com.turing.contacts.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.turing.contacts.models.User;
import com.turing.contacts.repositories.UserRepository;
import com.turing.contacts.services.TokenProviderService;
import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenProviderService tokenService;

    @Autowired
    UserRepository userRepository;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       
        var token = this.recoverToken(request);
        System.out.println("[Filter Manager - Validator]: Token - " + token);
        if (token != null) {

            try {
                //Decode JWT    
                DecodedJWT decodedJWT = tokenService.validateToken(token);

                //Extract ID (Custom subject when create token) and convert to ID LONG
                Long id = Long.parseLong(decodedJWT.getSubject());

                //Fetch user from DB
                User user = userRepository.findById(id).get();

                //Validate User
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                //Set auth context
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
            } catch (Exception e) {
                response.setStatus(490);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"invalid token\"}");
                return;

            }
        }
        
        try {

            filterChain.doFilter(request, response);

        } catch (Exception e) {

            response.setStatus(491);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"invalid permissions\"}");
            return;

        }

    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}