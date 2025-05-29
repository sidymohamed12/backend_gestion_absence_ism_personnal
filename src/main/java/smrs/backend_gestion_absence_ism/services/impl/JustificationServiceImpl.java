package smrs.backend_gestion_absence_ism.services.impl;

import smrs.backend_gestion_absence_ism.data.entities.Justification;
import smrs.backend_gestion_absence_ism.services.JustificationService;

import java.util.List;

public class JustificationServiceImpl implements JustificationService {
    @Override
    public List<Justification> getAllJustifications() {
        return List.of();
    }

    @Override
    public List<Justification> getJustificationsEnAttente() {
        return List.of();
    }

    // @Override
    // public Justification creerJustification(JustificationMobileDto
    // justificationDto, String documentPath) {
    // return null;
    // }

    @Override
    public Justification validerJustification(String justificationId, Long adminId, boolean estValidee) {
        return null;
    }

}
