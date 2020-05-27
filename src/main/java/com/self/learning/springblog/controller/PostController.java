package com.self.learning.springblog.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.self.learning.springblog.dto.PostDto;
import com.self.learning.springblog.service.PostService;
import net.bytebuddy.asm.Advice;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    ObjectMapper mapper = new ObjectMapper();
    //home post route for adding new post
    @RequestMapping( method = RequestMethod.POST)
    public JsonNode createPost(@RequestBody PostDto postDto){
            postService.createPost(postDto);
            JSONObject result=new JSONObject();
            result.appendField("success",true);
            result.appendField("message","Post Successfully submitted");
            return mapper.convertValue(result, JsonNode.class);
        }
    //getting all post
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public ResponseEntity<List<PostDto>> showAllPosts() {
        return new ResponseEntity<>(postService.showAllPosts(), HttpStatus.OK);
    }
    //getting a post based on the post id
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<PostDto> getSinglePost(@PathVariable @RequestBody Long id) {
        return new ResponseEntity<>(postService.readSinglePost(id), HttpStatus.OK);
    }
}
