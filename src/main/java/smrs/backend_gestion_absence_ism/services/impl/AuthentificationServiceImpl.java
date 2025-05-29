package smrs.backend_gestion_absence_ism.services.impl;

import org.springframework.stereotype.Service;
import smrs.backend_gestion_absence_ism.data.entities.Utilisateur;
import smrs.backend_gestion_absence_ism.services.AuthentificationService;

import java.util.Optional;

@Service
public class AuthentificationServiceImpl implements AuthentificationService {
    @Override
    public Optional<Utilisateur> authentifier(String email, String motDePasse) {
        return Optional.empty();
    }
}
