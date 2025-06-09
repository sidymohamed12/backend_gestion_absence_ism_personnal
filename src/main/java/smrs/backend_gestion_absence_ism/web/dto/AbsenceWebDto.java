package smrs.backend_gestion_absence_ism.web.dto;

import lombok.Data;
import smrs.backend_gestion_absence_ism.data.enums.StatutJustification;
import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;

import java.time.LocalDateTime;

@Data
public class AbsenceWebDto {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime heurePointage;
    private TypeAbsence type;
    private String etudiantNom;
    private String etudiantPrenom;
    private String etudiantMatricule;
    private String coursNom;
    private int minutesRetard;
    private StatutJustification justification;
    private String salleNom;
    private String coursJour;

}
