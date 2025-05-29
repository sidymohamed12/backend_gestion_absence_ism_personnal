package smrs.backend_gestion_absence_ism.web.dto;

import lombok.Data;
import smrs.backend_gestion_absence_ism.data.enums.StatutAbsence;
import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class AbsenceWebDto {
    private String id;
    private LocalDateTime createdAt;
    private LocalTime duree;
    private TypeAbsence type;
    private StatutAbsence statut;
    private String etudiantNom;
    private String etudiantPrenom;
    private String etudiantMatricule;
    private String coursNom;
    private boolean AJustification;

}
