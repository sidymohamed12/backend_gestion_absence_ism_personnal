package smrs.backend_gestion_absence_ism.services;

import java.util.List;

import smrs.backend_gestion_absence_ism.mobile.dto.response.UtilisateurMobileDto;

public interface VigileService {
    UtilisateurMobileDto getVigileById(String vigileId);
    List<UtilisateurMobileDto> getAllVigiles();
}
