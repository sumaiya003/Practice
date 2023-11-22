package com.example.Facebooksecurityproject.controller;


import com.example.Facebooksecurityproject.entity.Post;
import com.example.Facebooksecurityproject.entity.PostStatus;
import com.example.Facebooksecurityproject.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @PostMapping("/create")
    public String createPost(@RequestBody Post post , Principal principal){
        post.setStatus(PostStatus.PENDING);
        post.setUserName(principal.getName());
        postRepository.save(post);
        return principal.getName()+"Your post is saved" ;
    }
    @GetMapping("/approvePost/{postId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String approvePost(@PathVariable int postId){
        Post post=postRepository.findById(postId).get();
        post.setStatus(PostStatus.APPROVED);
        postRepository.save(post);
        return "post approved";
    }

    @GetMapping("/approveAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String approveAll(){
        postRepository.findAll().stream().filter(post -> post.getStatus().equals(PostStatus.PENDING)).forEach(post->{
            post.setStatus(PostStatus.APPROVED);
            postRepository.save(post);
        });
        return "Approved all";
    }

    @GetMapping("/viewAll")
    public List<Post>  viewAll(){
        return postRepository.findAll().stream()
                .filter(post -> post.getStatus().equals(PostStatus.APPROVED))
                .collect(Collectors.toList());
    }
    // reject by id


    // reject all
    @GetMapping("/rejectAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public  String rejectAll(){
        postRepository.findAll().stream().filter(post -> post.getStatus().equals(PostStatus.PENDING)).forEach(post->{
            post.setStatus(PostStatus.REJECTED);
            postRepository.save(post);
        });
        return "rejected all!";
    }
}
