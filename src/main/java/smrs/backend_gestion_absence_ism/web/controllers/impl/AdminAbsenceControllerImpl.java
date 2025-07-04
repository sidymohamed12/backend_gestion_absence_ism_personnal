package smrs.backend_gestion_absence_ism.web.controllers.impl;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;
import smrs.backend_gestion_absence_ism.mobile.dto.RestResponse;
import smrs.backend_gestion_absence_ism.services.AbsenceService;
import smrs.backend_gestion_absence_ism.web.controllers.AdminAbsenceController;
import smrs.backend_gestion_absence_ism.web.dto.AbsenceDetailWithJustificationDto;
import smrs.backend_gestion_absence_ism.web.dto.StatAbsence;

@RestController
@AllArgsConstructor
public class AdminAbsenceControllerImpl implements AdminAbsenceController {
    private final AbsenceService absenceService;

    public ResponseEntity<AbsenceDetailWithJustificationDto> getAbsenceDetail(String id) {
        if (id == null || id.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(absenceService.getAbsenceDetailWithJustification(id));
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAbsencesForActiveYear(int page, int size, TypeAbsence typeAbsence,
            LocalDate date) {
        var pageable = PageRequest.of(page, size);
        var absences = absenceService.getAllAbsences(pageable, typeAbsence, date);
        var totalPages = absences.getTotalPages();

        return new ResponseEntity<>(RestResponse.responsePaginate(
                HttpStatus.OK, absences.getContent(), "Absence Paginé",
                new int[totalPages], absences.getNumber(), totalPages,
                absences.getTotalElements(), absences.isFirst(), absences.isLast()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<StatAbsence> getStatAbsence() {
        return ResponseEntity.ok(absenceService.getStatistiqueAbsence());
    }

}
