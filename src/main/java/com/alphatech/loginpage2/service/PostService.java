package com.alphatech.loginpage2.service;

import com.alphatech.loginpage2.model.PerfilModel;
import com.alphatech.loginpage2.model.PostModel;
import com.alphatech.loginpage2.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public PostModel criarPost (PerfilModel perfil, String text){
        PostModel post = new PostModel();
        post.setPerfil(perfil);
        post.setText(text);
        post.setDateCriacao(LocalDateTime.now());
        return postRepository.save(post);
    }
    public List<PostModel> listarPostsPorPerfil(PerfilModel perfil){
        return postRepository.findByPerfil(perfil);
    }
}