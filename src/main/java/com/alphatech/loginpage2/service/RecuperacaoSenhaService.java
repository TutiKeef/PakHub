package com.alphatech.loginpage2.service;

import com.alphatech.loginpage2.PerfilModel;
import com.alphatech.loginpage2.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RecuperacaoSenhaService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PerfilRepository perfilRepository;

    // Simulação de um "banco de tokens" em memória. Ideal: usar uma tabela no banco.
    private ConcurrentHashMap<String, TokenData> tokens = new ConcurrentHashMap<>();

    public void gerarTokenRecuperacao(String email) {
        Optional<PerfilModel> perfilOpt = perfilRepository.findByEmail(email);
        if (perfilOpt.isEmpty()) {
            throw new RuntimeException("E-mail não cadastrado.");
        }

        String token = UUID.randomUUID().toString();
        LocalDateTime expiracao = LocalDateTime.now().plusMinutes(30); // expira em 30 minutos

        tokens.put(token, new TokenData(perfilOpt.get(), expiracao));

        String link = "http://localhost:8080/resetar-senha?token=" + token;
        String corpoHtml = "<h2>Recuperação de Senha</h2>" +
                "<p>Olá! Recebemos uma solicitação para redefinir sua senha.</p>" +
                "<p><a href=\"" + link + "\" " +
                "style=\"padding: 10px 20px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px;\">" +
                "Redefinir Senha</a></p>" +
                "<p>Se você não solicitou isso, ignore este e-mail.</p>";

        emailService.enviarEmailComHtml(email, "Recupere sua senha", corpoHtml);
    }

    public PerfilModel validarToken(String token) {
        TokenData tokenData = tokens.get(token);
        if (tokenData == null || tokenData.expiracao.isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token inválido ou expirado.");
        }
        return tokenData.perfil;
    }

    public void atualizarSenha(PerfilModel perfil, String novaSenha) {
        perfil.setPassword(novaSenha); // CUIDADO: isso salva a senha em texto puro
        perfil.setTokenRecuperacao(null);
        perfil.setTokenExpiracao(null);
        perfilRepository.save(perfil);
    }

    // classe interna pra guardar token temporariamente
    private static class TokenData {
        PerfilModel perfil;
        LocalDateTime expiracao;

        public TokenData(PerfilModel perfil, LocalDateTime expiracao) {
            this.perfil = perfil;
            this.expiracao = expiracao;
        }
    }
}

