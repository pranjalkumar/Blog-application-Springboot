package com.self.learning.springblog.exception;
//custom exception message for post not found
public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String mesaage) {
        super(mesaage);
    }
}
