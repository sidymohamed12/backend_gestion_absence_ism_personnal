package smrs.backend_gestion_absence_ism.web.dto;

import lombok.Data;
import smrs.backend_gestion_absence_ism.data.enums.StatutJustification;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class JustificationWebDto {
    private String id;
    private LocalDateTime createdAt;
    private String description;
    private List<String> piecesJointes;
    private StatutJustification statut;
    private String absenceId;
    private String etudiantNom;
    private String etudiantPrenom;
    private String etudiantMatricule;
    // private LocalDateTime dateAbsence;
}
