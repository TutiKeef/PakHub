package com.alphatech.loginpage2;

import com.alphatech.loginpage2.service.RecuperacaoSenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

public class RecuperacaoSenhaController {

    @Autowired
    private RecuperacaoSenhaService recuperacaoSenhaService;

    @GetMapping("/esqueceu-senha")
    public String esqueceuSenhaForm() {
        return "esqueceu-senha";
    }

    @PostMapping("/recuperar-senha")
    public String recuperarSenha(@RequestParam String email, Model model) {
        try {
            recuperacaoSenhaService.gerarTokenRecuperacao(email);
            model.addAttribute("message", "Link enviado para o Email do usuário!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "mensagem";
    }

    @GetMapping("/resetar-senha")
    public String resetarSenhaForm(@RequestParam String token, Model model) {
        try{
            recuperacaoSenhaService.validarToken(token);
            model.addAttribute("token", token);
            return "recuperar-senha";
        } catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "mensagem";
        }
    }
    @PostMapping("resetar-senha")
    public String resetarSenha(@RequestParam String token,
                               @RequestParam String novaSenha,
                               Model model) {
        try {
            PerfilModel perfilModel = recuperacaoSenhaService.validarToken(token);
            recuperacaoSenhaService.atualizarSenha(perfilModel, novaSenha);
            model.addAttribute("message", "Senha Atualizada com sucesso!");
        } catch(Exception e){
            model.addAttribute("error", e.getMessage());

        }

    return "mensagem";

    }
}