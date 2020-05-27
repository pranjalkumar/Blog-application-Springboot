package com.self.learning.springblog.repository;

import com.self.learning.springblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Post repository for implementing pre-defined methods for DB query
@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

}
