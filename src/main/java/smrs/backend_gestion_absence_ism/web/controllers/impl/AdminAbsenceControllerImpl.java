package smrs.backend_gestion_absence_ism.web.controllers.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import smrs.backend_gestion_absence_ism.services.AbsenceService;
import smrs.backend_gestion_absence_ism.utils.exceptions.EntityNotFoundException;
import smrs.backend_gestion_absence_ism.web.controllers.AdminAbsenceController;
import smrs.backend_gestion_absence_ism.web.dto.AbsenceDetailWithJustificationDto;
import smrs.backend_gestion_absence_ism.web.dto.AbsenceWebDto;

@RestController
@AllArgsConstructor
public class AdminAbsenceControllerImpl implements AdminAbsenceController {
    private final AbsenceService absenceService;

    public ResponseEntity<AbsenceDetailWithJustificationDto> getAbsenceDetail(@PathVariable String id) {
        return ResponseEntity.ok(absenceService.getAbsenceDetailWithJustification(id));
    }
    
    @Override
    public ResponseEntity<List<AbsenceWebDto>> getAbsencesForActiveYear() {
        try {
            List<AbsenceWebDto> absences = absenceService.getAbsencesForActiveYear();
            return ResponseEntity.ok(absences);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
