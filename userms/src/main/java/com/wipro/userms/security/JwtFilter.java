package com.wipro.userms.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wipro.userms.service.UserService;
import com.wipro.userms.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    @Lazy
    UserService service;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String path=request.getServletPath();
        if (path.equals("/user/login")||
                path.equals("/user/register")||
                path.equals("/user/logout")){
            filterChain.doFilter(request,response);
            return;
        }
        String token= null;
        String email= null;
        Cookie[] cookies=request.getCookies();
        if (cookies!=null){
            for(Cookie cookie:cookies){
                if("jwt".equals(cookie.getName())){
                    token=cookie.getValue();
                    break;
                }
            }
        }
        if (token!=null){
            try {
            	email=jwtUtil.extractUsername(token);
            } catch(Exception e){
                filterChain.doFilter(request, response);
                return;
            }
        }

        if(email!=null && SecurityContextHolder.getContext()
                        .getAuthentication()==null){
            UserDetails userDetails = service.loadUserByUsername(email);
            if(jwtUtil.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken auth=
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                SecurityContextHolder.getContext()
                        .setAuthentication(auth);
            }
        }
        filterChain.doFilter(request,response);
    }
}