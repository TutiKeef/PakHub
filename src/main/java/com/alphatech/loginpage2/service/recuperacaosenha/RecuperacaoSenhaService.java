package com.alphatech.loginpage2.service.recuperacaosenha;

import com.alphatech.loginpage2.model.PerfilModel;
import com.alphatech.loginpage2.repository.PerfilRepository;
import com.alphatech.loginpage2.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecuperacaoSenhaService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private PerfilRepository perfilRepository;

    public void gerarTokenRecuperacao(String email) {
        Optional<PerfilModel> perfilOpt = perfilRepository.findByEmail(email);
        if (perfilOpt.isEmpty()) {
            throw new RuntimeException("E-mail não cadastrado.");
        }

        PerfilModel perfil = perfilOpt.get();
        String token = UUID.randomUUID().toString();
        LocalDateTime expiracao = LocalDateTime.now().plusMinutes(30); // expira em 30 minutos

        perfil.setTokenRecuperacao(token);
        perfil.setTokenExpiracao(expiracao);
        perfilRepository.save(perfil);

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
        Optional<PerfilModel> perfilOpt = perfilRepository.findByTokenRecuperacao(token);
        if (perfilOpt.isEmpty()) {
            throw new RuntimeException("Token inválido.");
        }
        PerfilModel perfil = perfilOpt.get();
        if (perfil.getTokenExpiracao() == null || perfil.getTokenExpiracao().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado.");
        }
        return perfil;
    }

    public void atualizarSenha(PerfilModel perfil, String novaSenha) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        perfil.setPassword(encoder.encode(novaSenha));
        perfil.setTokenRecuperacao(null);
        perfil.setTokenExpiracao(null);
        perfilRepository.save(perfil);
    }
}

