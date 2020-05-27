package com.self.learning.springblog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAutheticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailsService userDetailsService;

    //JWT extraction and validation (protected routes)
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String jwt=getJwtFromRequest(httpServletRequest);
//        System.out.println("jwte here");
//        System.out.println(jwt);
        if(StringUtils.hasText(jwt)&& jwtProvider.validateToken(jwt)){
//            System.out.println( "testing phase");
            String username=jwtProvider.getUsernameFromJwt(jwt);
//            System.out.println(username);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
//        System.out.println("filter reached");
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    //fetching JWT from the auth header
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken=request.getHeader("Authorization");
//        System.out.println("test");
//        System.out.println(bearerToken);
        if(StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer ")){
//            System.out.println("test");
//            System.out.println(bearerToken.substring(7));
            return bearerToken.substring(7);
        }
        return bearerToken;
    }
}
