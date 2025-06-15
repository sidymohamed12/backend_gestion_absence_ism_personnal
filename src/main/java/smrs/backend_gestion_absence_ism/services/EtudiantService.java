package smrs.backend_gestion_absence_ism.services;

import java.util.List;

import smrs.backend_gestion_absence_ism.mobile.dto.response.EtudiantMobileDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.UtilisateurMobileDto;
import smrs.backend_gestion_absence_ism.web.dto.EtudiantListDto;

public interface EtudiantService {
    EtudiantMobileDto getEtudiantByMatricule(String matricule);

    List<UtilisateurMobileDto> listerEtudiantsAnneeActive();

    List<EtudiantListDto> getAllEtudiantsByClasseId(String classeId);
}
