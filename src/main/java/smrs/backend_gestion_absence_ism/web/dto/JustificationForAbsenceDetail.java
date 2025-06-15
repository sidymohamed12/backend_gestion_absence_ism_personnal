package smrs.backend_gestion_absence_ism.web.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import smrs.backend_gestion_absence_ism.data.enums.StatutJustification;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JustificationForAbsenceDetail {
    private String id;
    private LocalDateTime createdAt;
    private String description;
    private List<String> piecesJointes;
    private StatutJustification statut;
    private LocalDateTime dateValidation;
    private String prenomValidateur;
    private String nomValidateur;
}
