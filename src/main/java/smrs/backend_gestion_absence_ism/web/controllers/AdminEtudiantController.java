package smrs.backend_gestion_absence_ism.web.controllers;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import smrs.backend_gestion_absence_ism.mobile.dto.response.UtilisateurMobileDto;
import smrs.backend_gestion_absence_ism.web.dto.EtudiantListDto;

@Tag(name = "Web - Admin Étudiant", description = "API pour la gestion des étudiants par l'administrateur")
@RequestMapping("/api/web/etudiants")
public interface AdminEtudiantController {

        @Operation(summary = "Récupérer tous les etudiants", description = "Retourne la liste de tous les etudiants enregistrés dans le système")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Liste des etudiants récupérée avec succès", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UtilisateurMobileDto.class)))),
                        @ApiResponse(responseCode = "404", description = "Aucun etduant trouvé"),
                        @ApiResponse(responseCode = "500", description = "Erreur serveur")
        })
        @GetMapping("")
        ResponseEntity<List<UtilisateurMobileDto>> getAllEtudiants();

        @Operation(summary = "Récupérer les détails d'un etudiant", description = "Retourne les informations de l'etudiant et ses absences par l'id de l'utilisateur ")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Détail etudiant récupérée avec succès", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UtilisateurMobileDto.class)))),
                        @ApiResponse(responseCode = "404", description = "Aucun etduant trouvé"),
                        @ApiResponse(responseCode = "500", description = "Erreur serveur")
        })
        @GetMapping("/{id}")
        ResponseEntity<UtilisateurMobileDto> getDetailEtudiant(
                        @Parameter(description = "Identifiant de l'utilisateur") @PathVariable String id);

        @Operation(summary = "Lister les étudiants de l'année active", description = "Récupère la liste de tous les étudiants inscrits pour l'année scolaire en cours")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Liste des étudiants récupérée avec succès", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EtudiantListDto.class)))),
                        @ApiResponse(responseCode = "404", description = "Aucun étudiant trouvé"),
                        @ApiResponse(responseCode = "500", description = "Erreur serveur")
        })
        @GetMapping("/annee-active")
        ResponseEntity<List<EtudiantListDto>> listerEtudiantsAnneeActive();
}
