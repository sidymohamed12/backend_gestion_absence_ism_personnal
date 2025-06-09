package smrs.backend_gestion_absence_ism.web.dto;

import lombok.Data;
import smrs.backend_gestion_absence_ism.data.enums.StatutJustification;

@Data
public class JustificationTraitementRequest {
    private String justificationId;
    private StatutJustification statut;
    private String adminValidateurId;
}
