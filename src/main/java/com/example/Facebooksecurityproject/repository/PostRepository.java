package com.example.Facebooksecurityproject.repository;

import com.example.Facebooksecurityproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Integer>
{
}
