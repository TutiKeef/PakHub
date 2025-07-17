package com.alphatech.loginpage2;

import com.alphatech.loginpage2.service.EmailService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class EmailTest implements CommandLineRunner {

    @Autowired
    private EmailService emailService;

    @Override
    public void run(String... args) {
        emailService.enviarEmail(
                "arthurbragaalvesmartins@gmail.com", // ou outro email de destino
                "Teste de envio",
                "Este é um email de teste enviado via Spring Boot"
        );
    }
}