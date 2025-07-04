package smrs.backend_gestion_absence_ism.web.dto;

import lombok.Data;
import smrs.backend_gestion_absence_ism.data.enums.StatutJustification;
import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;

import java.time.LocalDateTime;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class AbsenceWebDto {
    @Schema(description = "ID de l'absence", example = "663bd21f9f5c4c7d88ab0001")
    private String id;
    private LocalDateTime createdAt;
    @Schema(description = "Heure exacte de pointage", example = "2025-05-29T08:17:00")
    private LocalDateTime heurePointage;
    @Schema(description = "Minutes de retard", example = "17")
    private int minutesRetard;
    @Schema(description = "Type d'absence : ABSENCE_COMPLETE ou RETARD", example = "RETARD")
    private TypeAbsence type;
    private String etudiantNom;
    private String etudiantPrenom;
    private String etudiantMatricule;
    @Schema(description = "Nom du cours", example = "Architecture des systèmes")
    private String nomCours;
    @Schema(description = "Statut de la justification", example = "EN_ATTENTE")
    private StatutJustification statutJustification;
    @Schema(description = "Salle du cours", example = "302")
    private String salle;
    private String coursJour;
    @Schema(description = "l'heure de debut du cours", example = "14:00")
    private LocalTime heureDebut;

}
