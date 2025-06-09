package smrs.backend_gestion_absence_ism.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import smrs.backend_gestion_absence_ism.web.dto.JustificationTraitementRequest;
import smrs.backend_gestion_absence_ism.web.dto.JustificationWebDto;

import java.util.List;

@Tag(name = "Web - Admin Justifications", description = "API pour la gestion des justifications d'absence côté administration")
@RequestMapping("/api/web/justification")
public interface AdminJustificationController {

    @Operation(summary = "Lister toutes les justifications", description = "Retourne la liste de toutes les justifications d'absence enregistrées dans le système.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des justifications récupérée avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JustificationWebDto.class))),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @GetMapping
    ResponseEntity<List<JustificationWebDto>> getAllJustifications();

    // --------------------------------------------------------------------------

    @Operation(summary = "Lister les justifications en attente", description = "Retourne la liste des justifications d'absence en attente de validation par un administrateur.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des justifications en attente récupérée avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JustificationWebDto.class))),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @GetMapping("/en-attente")
    ResponseEntity<List<JustificationWebDto>> getJustificationsEnAttente();

    // --------------------------------------------------------------------------------------

    @Operation(summary = "Valider ou rejeter une justification", description = "Permet à un administrateur de valider ou rejeter une justification d'absence soumise par un étudiant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Justification traitée avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JustificationWebDto.class))),
            @ApiResponse(responseCode = "404", description = "Justification non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @PatchMapping("/{id}")
    ResponseEntity<JustificationWebDto> validerJustification(
            @Parameter(description = "ID de la justification à traiter") @PathVariable String id,
            @Parameter(description = "Un DTO request pour mettre à jour la justification") @Valid @RequestBody JustificationTraitementRequest request);
}
