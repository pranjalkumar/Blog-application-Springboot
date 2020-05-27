package com.self.learning.springblog.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.self.learning.springblog.dto.PostDto;
import com.self.learning.springblog.exception.PostNotFoundException;
import com.self.learning.springblog.model.Post;
import com.self.learning.springblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PostService {
    @Autowired
    private AuthService authService;
    @Autowired
    private PostRepository postRepository;

    //mapping post model with POJO
    public void createPost(PostDto postDto){
        Post post=new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        User username=authService.getCurrentUser().orElseThrow(()->new IllegalArgumentException("No user found "));
        post.setUsername(username.getUsername());
        post.setCreatedOn(Instant.now());

        postRepository.save(post);
    }

    public List<PostDto> showAllPosts(){
        List<Post> posts=postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    //mapping data
    private PostDto mapFromPostToDto(Post post){
        PostDto postDto=new PostDto();
        postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setTitle(post.getTitle());
        postDto.setUsername(post.getUsername());
        return postDto;
    }

    public PostDto readSinglePost(Long id){
        Post post=postRepository.findById(id).orElseThrow(()->new PostNotFoundException("Post not found for id "+id));
        return mapFromPostToDto(post);
    }


}
