package com.alphatech.loginpage2;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "perfis",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_username", columnNames = {"username"}),
                @UniqueConstraint(name = "uk_email", columnNames = {"email"})
        })
public class PerfilModel {
    //Declaração das variáveis de perfil
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome de usuário é obrigatório")
    @Size(min=3,max=20,message="Username deve ter entre 3 a 20 caracteres")
    @Column(nullable = false, length = 20, unique = true)
    private String username;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    @Column(nullable = false)
    private String password;

    //Variáveis para recuperação de senha
    private String tokenRecuperacao;
    private LocalDateTime tokenExpiracao;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @Column(nullable = false, unique = true)
    private String email;

    @Size(max = 500, message = "Bio não pode ultrapassar 500 caracteres")
    @Column(length = 500)
    private String bio;

    @Digits(integer = 11, fraction = 0, message = "Celular deve conter apenas números")
    @Column(length = 11, unique = true)
    private Long cellphone;

    @Lob
    @Column(name = "foto_Perfil")
    private byte[] fotoPerfil;



    //Getters e Setters


    public String getTokenRecuperacao() {
        return tokenRecuperacao;
    }

    public void setTokenRecuperacao(String tokenRecuperacao) {
        this.tokenRecuperacao = tokenRecuperacao;
    }

    public byte[] getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(byte[] fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public Long getCellphone() {
        return cellphone;
    }

    public void setCellphone(Long cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getTokenExpiracao() {
        return tokenExpiracao;
    }

    public void setTokenExpiracao(LocalDateTime tokenExpiracao) {
        this.tokenExpiracao = tokenExpiracao;
    }

}
