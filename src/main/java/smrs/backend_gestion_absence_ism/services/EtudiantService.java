package smrs.backend_gestion_absence_ism.services;

import smrs.backend_gestion_absence_ism.mobile.dto.response.EtudiantMobileDto;

public interface EtudiantService {
    EtudiantMobileDto getEtudiantByMatricule(String matricule);
}
