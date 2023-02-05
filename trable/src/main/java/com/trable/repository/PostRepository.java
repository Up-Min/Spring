package com.trable.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.trable.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
