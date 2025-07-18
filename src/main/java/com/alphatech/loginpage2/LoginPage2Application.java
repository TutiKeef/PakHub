package com.alphatech.loginpage2;

import com.alphatech.loginpage2.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginPage2Application implements CommandLineRunner {

    @Autowired
    private EmailService emailService;

    public static void main(String[] args) {
        SpringApplication.run(LoginPage2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> Testando envio de e-mail...");
        String token = java.util.UUID.randomUUID().toString();
        String link = "http://localhost:8080/resetar-senha?token=" + token;

        String corpoHtml = "<h2>Recuperação de Senha</h2>" +
                "<p>Olá! Recebemos uma solicitação para redefinir sua senha.</p>" +
                "<p>Clique no botão abaixo para continuar:</p>" +
                "<a href=\"" + link + "\" style=\"padding: 10px 20px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px;\">Redefinir Senha</a>" +
                "<p>Se você não solicitou isso, apenas ignore este e-mail.</p>";

        emailService.enviarEmailComHtml(
                "bragatutu1@gmail.com",
                "Recupere sua senha",
                corpoHtml
        );
    }
}
