package smrs.backend_gestion_absence_ism.mobile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Requête pour enregistrer un pointage d'étudiant via QR code")

public class PointageRequestDto {
    @Schema(description = "Matricule de l'étudiant", example = "ISMDK-2425")
    private String matriculeEtudiant;

    @Schema(description = "Identifiant du vigile qui scanne", example = "663bd21f9f5c4c7d88ab9876")
    private String vigileId;
}
