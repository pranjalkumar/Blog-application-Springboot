package com.self.learning.springblog.service;

import com.self.learning.springblog.dto.LoginRequest;
import com.self.learning.springblog.dto.RegisterRequest;
import com.self.learning.springblog.model.User;
import com.self.learning.springblog.repository.UserRepository;
import com.self.learning.springblog.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

//    mapping request to User model
    public void signup(RegisterRequest registerRequest){
        User user=new User();
        user.setUserName(registerRequest.getUsername());
        user.setPassword(encodePassowrd(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        userRepository.save(user);
    }

    //encrypting pass
    private String encodePassowrd(String password) {
        return passwordEncoder.encode(password);
    }

    //login and returning a JWT token
    public String login(LoginRequest loginRequest) {
        Authentication authenticate=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return jwtProvider.generateToken(loginRequest.getUsername());
    }

    //get the current user
    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }
}
