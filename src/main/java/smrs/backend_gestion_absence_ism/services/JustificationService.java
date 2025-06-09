package smrs.backend_gestion_absence_ism.services;

import smrs.backend_gestion_absence_ism.data.entities.Justification;
import smrs.backend_gestion_absence_ism.mobile.dto.request.JustificationRequestDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.JustificationMobileDto;
import smrs.backend_gestion_absence_ism.web.dto.JustificationTraitementRequest;
import smrs.backend_gestion_absence_ism.web.dto.JustificationWebDto;

import java.util.List;

public interface JustificationService {
    List<Justification> getAllJustifications();

    List<Justification> getJustificationsEnAttente();

    JustificationMobileDto getJustificationById(String justificationId);

    JustificationMobileDto ajouterJustification(JustificationRequestDto request);

    JustificationWebDto traiterJustification(JustificationTraitementRequest request);
}
