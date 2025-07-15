package com.alphatech.loginpage2;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PerfilController {

    private final PerfilRepository perfilRepository;

    public PerfilController(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    // Redireciona "/" para "/login"
    @GetMapping("/")
    public String redirectRoot() {
        return "redirect:/login";
    }

    // Página de login
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // Processa login
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {
        PerfilModel perfil = perfilRepository.findByUsername(username);
        if (perfil != null && perfil.getPassword().equals(password)) {
            session.setAttribute("usuarioLogado", perfil.getUsername());
            return "redirect:/perfil";
        }
        model.addAttribute("error", "Usuário ou senha inválidos");
        return "login";
    }

    // Página de cadastro
    @GetMapping("/cadastro")
    public String cadastroForm() {
        return "cadastro";
    }

    // Processa cadastro
    @PostMapping("/cadastro")
    public String cadastro(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email,
                           @RequestParam(required = false) String bio, // Tornado opcional
                           @RequestParam(required = false) Long cellphone, // Tornado opcional
                           Model model) {

        if (perfilRepository.findByUsername(username) != null) {
            model.addAttribute("error", "Usuário já existe");
            return "cadastro";
        }

        PerfilModel novoPerfil = new PerfilModel();
        novoPerfil.setUsername(username);
        novoPerfil.setPassword(password);
        novoPerfil.setEmail(email);
        novoPerfil.setBio(bio != null ? bio : ""); // Tratamento para null
        novoPerfil.setCellphone(cellphone != null ? cellphone : 0L); // Valor padrão
        perfilRepository.save(novoPerfil);

        return "redirect:/login";
    }

    // Página de perfil
    @GetMapping("/perfil")
    public String perfilPage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("usuarioLogado");
        if (username == null) {
            return "redirect:/login";
        }

        PerfilModel perfil = perfilRepository.findByUsername(username);
        model.addAttribute("perfil", perfil);
        return "perfil";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    //Esqueceu a senha
    @GetMapping("/esqueceu-senha")
    public String esqueceuSenhaForm() {
        return "esqueceu-senha"; // Deve corresponder ao nome do arquivo HTML
    }
    @PostMapping("/recuperar-senha")
    public String recuperarSenha(@RequestParam String email, Model model) {
        // Lógica para enviar email de recuperação
        model.addAttribute("message", "Link de recuperação enviado para " + email);
        return "mensagem";
    }

}
