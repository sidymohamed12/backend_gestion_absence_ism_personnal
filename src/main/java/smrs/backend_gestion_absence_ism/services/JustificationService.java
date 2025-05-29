package smrs.backend_gestion_absence_ism.services;

import smrs.backend_gestion_absence_ism.data.entities.Justification;

import java.util.List;

public interface JustificationService {
    List<Justification> getAllJustifications();

    List<Justification> getJustificationsEnAttente();

    // Justification creerJustification(JustificationMobileDto justificationDto,
    // String documentPath);

    Justification validerJustification(String justificationId, Long adminId, boolean estValidee);
}
