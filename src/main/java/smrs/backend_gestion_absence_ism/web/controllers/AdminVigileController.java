package smrs.backend_gestion_absence_ism.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import smrs.backend_gestion_absence_ism.mobile.dto.response.HistoriquePointageMobileDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.UtilisateurMobileDto;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Web - Admin Vigile", description = "API pour la gestion des vigiles par l'administrateur")
@RequestMapping("/api/web/vigile")
public interface AdminVigileController {

        @Operation(summary = "Récupérer tous les vigiles", description = "Retourne la liste de tous les vigiles enregistrés dans le système")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Liste des vigiles récupérée avec succès", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UtilisateurMobileDto.class)))),
                        @ApiResponse(responseCode = "404", description = "Aucun vigile trouvé"),
                        @ApiResponse(responseCode = "500", description = "Erreur serveur")
        })
        @GetMapping("")
        ResponseEntity<List<UtilisateurMobileDto>> getAllVigiles();

        @GetMapping("/{vigileId}/historique")
        @Operation(summary = "Historique de pointage", description = "Récupère l'historique des pointages effectués par un vigile avec possibilité de filtrage")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Historique récupéré avec succès"),
                        @ApiResponse(responseCode = "404", description = "Vigile introuvable")
        })
        ResponseEntity<List<HistoriquePointageMobileDto>> getHistoriquePointage(
                        @Parameter(description = "Identifiant du vigile") @PathVariable String vigileId,
                        @Parameter(description = "Date de pointage (optionnel)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                        @Parameter(description = "Matricule de l'étudiant (optionnel)") @RequestParam(required = false) String etudiantMatricule);

}
