package smrs.backend_gestion_absence_ism.mobile.dto.response;

import java.time.LocalDateTime;

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

    private CoursMobileDto cours;

    @Schema(description = "Si la justification est présente", example = "true")
    private boolean justification;
}
