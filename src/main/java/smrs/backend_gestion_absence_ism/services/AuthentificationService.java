package smrs.backend_gestion_absence_ism.services;

import smrs.backend_gestion_absence_ism.data.entities.Utilisateur;

import java.util.Optional;

public interface AuthentificationService {
    Optional<Utilisateur> authentifier(String email, String motDePasse);
}
