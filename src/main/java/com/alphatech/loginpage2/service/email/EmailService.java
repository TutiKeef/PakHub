package com.alphatech.loginpage2.service.email;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailComHtml(String para, String assunto, String corpoHtml) {
        try {
            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true);

            helper.setTo(para);
            helper.setSubject(assunto);
            helper.setText(corpoHtml, true); // <--- true para HTML
            helper.setFrom("seuemail@gmail.com");

            mailSender.send(mensagem);
            System.out.println(">>> E-mail HTML enviado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
            e.printStackTrace();
        }
    }
}