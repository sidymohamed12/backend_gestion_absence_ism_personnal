package smrs.backend_gestion_absence_ism.mobile.controllers;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;
import smrs.backend_gestion_absence_ism.mobile.dto.request.JustificationRequestDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.AbsenceMobileDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.JustificationMobileDto;

@Tag(name = "Mobile - Étudiant", description = "API pour la gestion des fonctionnalités de l'étudiant via l'application mobile")
@RequestMapping("/api/mobile/etudiant")
public interface MobileEtudiantController {

    @GetMapping("/{etudiantId}")
    @Operation(summary = "Informations d'accueil", description = "Récupère les informations d'accueil de l'étudiant avec ses absences récentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Informations récupérées avec succès"),
            @ApiResponse(responseCode = "400", description = "Paramètres invalides"),
            @ApiResponse(responseCode = "404", description = "Étudiant introuvable")
    })
    ResponseEntity<Map<String, Object>> getInfoEtudiantConnectAccueil(
            @Parameter(description = "Identifiant de l'étudiant") @PathVariable String etudiantId);

    @GetMapping("/{etudiantId}/absences")
    @Operation(summary = "Liste des absences", description = "Récupère la liste des absences d'un étudiant avec possibilité de filtrage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des absences récupérée avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AbsenceMobileDto.class))),
            @ApiResponse(responseCode = "400", description = "Paramètres invalides"),
            @ApiResponse(responseCode = "404", description = "Étudiant introuvable")
    })
    ResponseEntity<Map<String, Object>> getAbsencesByEtudiant(
            @Parameter(description = "Identifiant de l'étudiant") @PathVariable String etudiantId,
            @Parameter(description = "Type d'absence (optionnel)") @RequestParam(required = false) TypeAbsence type,
            @Parameter(description = "Date spécifique (optionnel)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Parameter(description = "Nom du cours (optionnel)") @RequestParam(required = false) String coursNom);

    @PostMapping(path = "/justification")
    @Operation(summary = "Créer une justification", description = "Permet à un étudiant de soumettre une justification pour une absence")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Justification enregistrée avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JustificationMobileDto.class))),
            @ApiResponse(responseCode = "400", description = "Paramètres invalides"),
            @ApiResponse(responseCode = "404", description = "Absence ou étudiant introuvable")
    })
    ResponseEntity<Map<String, Object>> creerJustification(
            @Parameter(description = "Données de la justification") @RequestBody JustificationRequestDto request);

    @GetMapping("/justification/{justificationId}")
    @Operation(summary = "Détails d'une justification", description = "Récupère les détails d'une justification spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Justification récupérée avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JustificationMobileDto.class))),
            @ApiResponse(responseCode = "400", description = "Paramètres invalides"),
            @ApiResponse(responseCode = "404", description = "Justification introuvable")
    })
    ResponseEntity<Map<String, Object>> getJustificationbyId(
            @Parameter(description = "Identifiant de la justification") @PathVariable String justificationId);
}
