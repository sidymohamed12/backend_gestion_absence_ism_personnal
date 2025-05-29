package smrs.backend_gestion_absence_ism.web.controllers.impl;

import org.springframework.http.ResponseEntity;
import smrs.backend_gestion_absence_ism.web.controllers.AdminJustificationController;
import smrs.backend_gestion_absence_ism.web.dto.JustificationWebDto;

import java.util.List;

public class AdminJustificationControllerImpl implements AdminJustificationController {
    @Override
    public ResponseEntity<List<JustificationWebDto>> getAllJustifications() {
        return null;
    }

    @Override
    public ResponseEntity<List<JustificationWebDto>> getJustificationsEnAttente() {
        return null;
    }

    @Override
    public ResponseEntity<JustificationWebDto> validerJustification(Long id, Long adminId, boolean estValidee) {
        return null;
    }
}
