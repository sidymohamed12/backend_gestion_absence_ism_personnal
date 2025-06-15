package smrs.backend_gestion_absence_ism.services;

import smrs.backend_gestion_absence_ism.data.entities.Absence;
import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;
import smrs.backend_gestion_absence_ism.mobile.dto.response.AbsenceMobileDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.EtudiantAccueilMobileDto;
import smrs.backend_gestion_absence_ism.web.dto.AbsenceDetailWithJustificationDto;
import smrs.backend_gestion_absence_ism.web.dto.AbsenceWebDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AbsenceService {
    List<Absence> getAllAbsences();

    List<Absence> getAbsencesByEtudiant(String etudiantId);

    List<Absence> getAbsencesByEtudiantMatricule(String matricule);

    List<Absence> getAbsencesByDate(LocalDateTime date);

    List<Absence> getAbsencesByCours(String coursId);

    EtudiantAccueilMobileDto getAccueilEtudiant(String etudiantId);

    List<AbsenceMobileDto> getAbsencesEtudiant(String etudiantId,
            TypeAbsence type,
            LocalDate date,
            String coursNom);

    AbsenceDetailWithJustificationDto getAbsenceDetailWithJustification(String absenceId);

    Page<AbsenceWebDto> getAllAbsences(Pageable pageable, TypeAbsence typeAbsence, LocalDate date);
}
