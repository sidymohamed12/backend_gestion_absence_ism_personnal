package smrs.backend_gestion_absence_ism.services;

import smrs.backend_gestion_absence_ism.data.entities.Utilisateur;
import smrs.backend_gestion_absence_ism.mobile.dto.request.LoginRequestDTO;
import smrs.backend_gestion_absence_ism.mobile.dto.response.LoginResponseDto;

import java.util.Optional;

public interface AuthentificationService {
    Optional<Utilisateur> authentifier(String email, String motDePasse);

    LoginResponseDto login(LoginRequestDTO loginRequest);

    void logout();

}
