package smrs.backend_gestion_absence_ism.services;

import smrs.backend_gestion_absence_ism.data.entities.Absence;
import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;
import smrs.backend_gestion_absence_ism.mobile.dto.response.AbsenceMobileDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.EtudiantAccueilMobileDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AbsenceService {
    List<Absence> getAllAbsences();

    List<Absence> getAbsencesByEtudiant(String etudiantId);

    List<Absence> getAbsencesByEtudiantMatricule(String matricule);

    List<Absence> getAbsencesByDate(LocalDateTime date);

    List<Absence> getAbsencesByCours(String coursId);

    EtudiantAccueilMobileDto getAccueilEtudiant(String etudiantId);

    List<AbsenceMobileDto> getAbsencesEtudiant(String etudiantId,
            TypeAbsence type,
            LocalDate dateDebut,
            LocalDate dateFin);

}
