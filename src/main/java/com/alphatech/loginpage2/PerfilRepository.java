package com.alphatech.loginpage2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<PerfilModel,Long>{
    PerfilModel findByUsername(String username);
}

