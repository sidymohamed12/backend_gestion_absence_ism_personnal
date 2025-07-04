package smrs.backend_gestion_absence_ism.web.controllers;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;
import smrs.backend_gestion_absence_ism.web.dto.AbsenceDetailWithJustificationDto;
import smrs.backend_gestion_absence_ism.web.dto.AbsenceWebDto;
import smrs.backend_gestion_absence_ism.web.dto.StatAbsence;

@Tag(name = "Web - Admin Absences", description = "API pour la gestion des absences par l'administrateur")
@RequestMapping("/api/web/absences")
public interface AdminAbsenceController {

        @Operation(summary = "Détails d'une absence", description = "Récupère les détails d'une absence spécifique et sa justification associée si elle existe")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Détails de l'absence récupérés avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AbsenceDetailWithJustificationDto.class))),
                        @ApiResponse(responseCode = "404", description = "Absence non trouvée"),
                        @ApiResponse(responseCode = "500", description = "Erreur serveur")
        })
        @GetMapping("/{id}")
        ResponseEntity<AbsenceDetailWithJustificationDto> getAbsenceDetail(
                        @Parameter(description = "Identifiant de l'absence") @PathVariable String id);

        @Operation(summary = "Liste des absences de l'année scolaire active", description = "Récupère toutes les absences des étudiants inscrits pendant l'année scolaire active")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Liste des absences récupérée avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AbsenceWebDto.class))),
                        @ApiResponse(responseCode = "404", description = "Année scolaire active non trouvée"),
                        @ApiResponse(responseCode = "500", description = "Erreur serveur")
        })
        @GetMapping("/annee-active")
        ResponseEntity<Map<String, Object>> getAbsencesForActiveYear(
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "4") int size,
                        @Parameter(description = "Type Absence pour le filtrage (optionnel)") @RequestParam(value = "typeAbsence", required = false) TypeAbsence typeAbsence,
                        @Parameter(description = "Date pour le filtrage (optionnel)") @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);

        @GetMapping("/stat")
        ResponseEntity<StatAbsence> getStatAbsence();
}
