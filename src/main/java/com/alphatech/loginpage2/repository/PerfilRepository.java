package com.alphatech.loginpage2.repository;

import com.alphatech.loginpage2.model.PerfilModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<PerfilModel,Long>{
    PerfilModel findByUsername(String username); //Metodo para procurar nome no banco de dados
    Optional<PerfilModel> findByEmail(String email); //Metodo para procurar email no banco de dados
    Optional<PerfilModel> findByCellphone(Long cellphone);//Metodo para procurar celular no banco de dados
    Optional<PerfilModel> findByTokenRecuperacao(String tokenRecuperacao);
}

