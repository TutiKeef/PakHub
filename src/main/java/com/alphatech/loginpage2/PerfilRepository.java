package com.alphatech.loginpage2;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PerfilRepository extends JpaRepository<PerfilModel,Long>{
    PerfilModel findByUsername(String username); //Metodo para procurar nome no banco de dados
    Optional<PerfilModel> findByEmail(String email); //Metodo para procurar email no banco de dados
    Optional<PerfilModel> findByCellphone(Long cellphone);//Metodo para procurar celular no banco de dados
    Optional<PerfilModel> findByResetToken(String resetToken);
}

