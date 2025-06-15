package smrs.backend_gestion_absence_ism.mobile.dto.response;

import smrs.backend_gestion_absence_ism.data.enums.StatutJustification;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Détails d'une justification mobile")
public class JustificationMobileDto {
    @Schema(description = "ID de la justification", example = "663bd21f9f5c4c7d88ab8888")
    private String id;

    @Schema(description = "ID de l'absence concernée", example = "663bd21f9f5c4c7d88ab7777")
    private String absenceId;

    @Schema(description = "Description de la justification", example = "Rendez-vous médical")
    private String description;

    @Schema(description = "Liste des pièces jointes", example = "[\"piece1.jpg\", \"piece2.pdf\"]")
    private List<String> piecesJointes;

    @Schema(description = "Statut de la justification", example = "EN_ATTENTE")
    private StatutJustification statut;

    @Schema(description = "Date de validation", example = "2025-05-30T10:00:00")
    private LocalDateTime dateValidation;

    @Schema(description = "Nom du cours concerné", example = "Dev Mobile")
    private String coursNom;

    @Schema(description = "Jour du cours", example = "Vendredi")
    private String jourCours;
}
