package com.music.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws ServletException, IOException {

        String path = req.getRequestURI();

        
        if (path.startsWith("/admin") || path.startsWith("/songs/ui")) {
            chain.doFilter(req, res);
            return;
        }

        if (path.startsWith("/css") || path.startsWith("/js") || path.startsWith("/images")) {
            chain.doFilter(req, res);
            return;
        }

        String header = req.getHeader("Authorization");

        
        if (header == null && req.getCookies() != null) {
            for (Cookie c : req.getCookies()) {
                if (c.getName().equals("token")) {
                    header = "Bearer " + c.getValue();
                    break;
                }
            }
        }

       
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            String role = jwtUtil.extractRole(token);

            if (role != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                role,
                                null,
                                Collections.singleton(new SimpleGrantedAuthority(role))
                        );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(req, res);
    }
}

