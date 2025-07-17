package com.alphatech.loginpage2.service;

import com.alphatech.loginpage2.PerfilModel;
import com.alphatech.loginpage2.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RecuperacaoSenhaService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private EmailService emailService;

    public void gerarTokenRecuperacao(String email) throws Exception {
        Optional<PerfilModel> optionalPerfilModel = perfilRepository.findByEmail(email);

        if (optionalPerfilModel.isEmpty()) {
            throw new Exception("Email não encontrado");
        }

        PerfilModel perfilModel = optionalPerfilModel.get();
        String token = UUID.randomUUID().toString();
        perfilModel.setResetToken(token);
        perfilModel.setTokenExpiracao(LocalDateTime.now().plusHours(1));
        perfilRepository.save(perfilModel);

        // Enviando e-mail real
        String link = "http://localhost:8080/resetar-senha?token=" + token;
        emailService.enviarEmail(
                email,
                "Recuperação de Senha",
                "Clique no link para resetar sua senha: " + link
        );
    }

    public PerfilModel validarToken(String token) throws Exception {
        Optional<PerfilModel> optionalPerfilModel = perfilRepository.findByResetToken(token);

        if (optionalPerfilModel.isEmpty()) {
            throw new Exception("Token inválido");
        }

        PerfilModel perfilModel = optionalPerfilModel.get();
        if (perfilModel.getTokenExpiracao() == null || perfilModel.getTokenExpiracao().isBefore(LocalDateTime.now())) {
            throw new Exception("Token expirado");
        }

        return perfilModel;
    }

    public void atualizarSenha(PerfilModel perfilModel, String novaSenha) {
        perfilModel.setPassword(novaSenha);  // Em produção, use BCrypt
        perfilModel.setResetToken(null);
        perfilModel.setTokenExpiracao(null);
        perfilRepository.save(perfilModel);
    }
}

