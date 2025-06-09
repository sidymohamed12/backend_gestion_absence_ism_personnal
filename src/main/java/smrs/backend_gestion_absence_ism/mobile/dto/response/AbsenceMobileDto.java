package smrs.backend_gestion_absence_ism.mobile.dto.response;

import java.time.LocalDateTime;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Détails d'une absence mobile")
public class AbsenceMobileDto {
    @Schema(description = "ID de l'absence", example = "663bd21f9f5c4c7d88ab0001")
    private String id;

    @Schema(description = "Heure exacte de pointage", example = "2025-05-29T08:17:00")
    private LocalDateTime heurePointage;

    @Schema(description = "Minutes de retard", example = "17")
    private int minutesRetard;

    @Schema(description = "Type d'absence : ABSENCE_COMPLETE ou RETARD", example = "RETARD")
    private TypeAbsence type;

    // private CoursMobileDto cours;

    @Schema(description = "Nom du cours", example = "Architecture des systèmes")
    private String nomCours;

    @Schema(description = "Nom de l'enseignant", example = "Pr. DIOP")
    private String professeur;

    @Schema(description = "Salle du cours", example = "302")
    private String salle;

    @Schema(description = "l'heure de debut du cours", example = "14:00")
    private LocalTime heureDebut;

    @Schema(description = "Si la justification est présente", example = "true")
    private boolean justification;
}
