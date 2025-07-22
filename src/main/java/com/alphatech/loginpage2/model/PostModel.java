package com.alphatech.loginpage2.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private LocalDateTime dateCriacao;

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    private PerfilModel perfil;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateCriacao() {
        return dateCriacao;
    }

    public void setDateCriacao(LocalDateTime dateCriacao) {
        this.dateCriacao = dateCriacao;
    }

    public PerfilModel getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilModel perfil) {
        this.perfil = perfil;
    }
}
