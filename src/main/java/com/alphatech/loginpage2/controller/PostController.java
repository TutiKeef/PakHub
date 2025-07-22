package com.alphatech.loginpage2.controller;

import com.alphatech.loginpage2.model.PerfilModel;
import com.alphatech.loginpage2.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private PerfilRepository perfilRepository;

    @PostMapping("/perfil/postar")
    public String postar(@ResquestParam String text, Principal principal){
        PerfilModel perfil = perfilRepository.findByEmail(principal.getName()).get();
        postService.criarPost(perfil, text);
        return "redirect:/perfil";
    }

    @GetMapping("/perfil")
    public String verPerfil(Model model, Principal principal){
        PerfilModel perfil = perfilRepository.findByEmail(principal.getName()).get();
        List<PostModel> posts = postService.listarPostsPorPerfil(perfil);
        model.addAttribute("perfil", perfil);
        model.addAttribute("posts", posts);
        return "perfil";
    }
}
