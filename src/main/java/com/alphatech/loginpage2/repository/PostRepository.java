package com.alphatech.loginpage2.repository;

import com.alphatech.loginpage2.model.PerfilModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.alphatech.loginpage2.model.PostModel;


import java.util.List;

public interface PostRepository extends JpaRepository<PostModel, Long> {
    List<PostModel> findByPerfil(PerfilModel perfil);
}