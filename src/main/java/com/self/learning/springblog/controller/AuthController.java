package com.self.learning.springblog.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.self.learning.springblog.dto.LoginRequest;
import com.self.learning.springblog.dto.RegisterRequest;
import com.self.learning.springblog.service.AuthService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    ObjectMapper mapper = new ObjectMapper();
    //signup Controller
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public JsonNode signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
//        return new ResponseEntity((HttpStatus.OK));
        JSONObject result=new JSONObject();
        result.appendField("success",true);
        result.appendField("message","User Successfully registered");
        return mapper.convertValue(result,JsonNode.class);
    }

    //login controller
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonNode login(@RequestBody LoginRequest loginRequest){
        String token=authService.login(loginRequest);
        JSONObject result=new JSONObject();
        result.appendField("success","true");
        result.appendField("token",token);
        return mapper.convertValue(result,JsonNode.class);
    }
}
