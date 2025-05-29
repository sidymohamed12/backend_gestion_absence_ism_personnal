package smrs.backend_gestion_absence_ism.mobile.dto.response;

import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Historique de pointage d’un vigile pour un étudiant")

public class HistoriquePointageMobileDto {

    @Schema(description = "ID du pointage", example = "663bd21f9f5c4c7d88ab8080")
    private String id;

    @Schema(description = "Nom de l'étudiant", example = "BA")
    private String etudiantNom;

    @Schema(description = "Matricule de l'étudiant", example = "ISMDK-2425")
    private String matricule;

    @Schema(description = "Nom du cours", example = "Base de données")
    private String coursNom;

    @Schema(description = "Date du pointage", example = "2025-05-29")
    private String date;

    @Schema(description = "Heure d'arrivée", example = "08:12")
    private String heureArrivee;

    @Schema(description = "Minutes de retard", example = "12")
    private int minutesRetard;

    @Schema(description = "Type d'absence", example = "RETARD")
    private TypeAbsence typeAbsence;
}
