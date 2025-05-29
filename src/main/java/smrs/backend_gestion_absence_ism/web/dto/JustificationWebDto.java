package smrs.backend_gestion_absence_ism.web.dto;

import lombok.Data;
import smrs.backend_gestion_absence_ism.data.enums.StatutJustification;

import java.time.LocalDateTime;

@Data
public class JustificationWebDto {
    private String id;
    private LocalDateTime createdAt;
    private String description;
    private String documentPath;
    private StatutJustification statut;
    private Long absenceId;
    private String etudiantNom;
    private String etudiantPrenom;
    private String etudiantMatricule;
    private LocalDateTime dateAbsence;
}
