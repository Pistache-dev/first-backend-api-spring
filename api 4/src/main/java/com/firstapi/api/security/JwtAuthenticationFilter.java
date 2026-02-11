package com.firstapi.api.security;

import com.firstapi.api.user.UserDetailsImpl;
import com.firstapi.api.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(
            JwtTokenService jwtTokenService,
            UserRepository userRepository
    ) {
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        System.out.println(">>> Authorization header = " + header);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            System.out.println(">>> JWT token = " + token);

            if (jwtTokenService.isTokenValid(token)) {
                System.out.println(">>> Token is VALID");

                String email = jwtTokenService.extractEmail(token);
                System.out.println(">>> Email extracted = " + email);

                userRepository.findByEmail(email).ifPresentOrElse(user -> {
                    UserDetailsImpl userDetails = new UserDetailsImpl(user);

                    Authentication auth = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println(">>> Authentication SET in SecurityContext");
                }, () -> {
                    System.out.println(">>> User NOT FOUND in DB");
                });

            } else {
                System.out.println(">>> Token is INVALID");
            }
        } else {
            System.out.println(">>> No Bearer token found");
        }

        filterChain.doFilter(request, response);
    }
}

