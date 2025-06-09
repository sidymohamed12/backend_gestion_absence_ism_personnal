package smrs.backend_gestion_absence_ism.web.dto;

import lombok.Data;
import smrs.backend_gestion_absence_ism.data.enums.StatutJustification;

@Data
public class JustificationDto {
    private String id;
    private String description;
    private StatutJustification statut;
}