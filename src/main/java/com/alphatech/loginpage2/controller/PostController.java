package com.alphatech.loginpage2.controller;

import com.alphatech.loginpage2.model.PerfilModel;
import com.alphatech.loginpage2.repository.PerfilRepository;
import com.alphatech.loginpage2.service.PostService;
import com.alphatech.loginpage2.model.PostModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

import java.util.List;
import jakarta.servlet.http.HttpSession;

@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private PerfilRepository perfilRepository;

    @PostMapping("/perfil/postar")
    public String postar(@RequestParam String text, HttpSession session){
        String username = (String) session.getAttribute("usuarioLogado");
        if (username == null) {
            return "redirect:/login";
        }
        PerfilModel perfil = perfilRepository.findByUsername(username);
        postService.criarPost(perfil, text);
        return "redirect:/perfil";
    }

    @GetMapping("/perfil")
    public String verPerfil(Model model, HttpSession session){
        String username = (String) session.getAttribute("usuarioLogado");
        if (username == null) {
            return "redirect:/login";
        }
        PerfilModel perfil = perfilRepository.findByUsername(username);
        List<PostModel> posts = postService.listarPostsPorPerfil(perfil);
        model.addAttribute("perfil", perfil);
        model.addAttribute("posts", posts);
        return "perfil";
    }
}
