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
import org.springframework.web.bind.annotation.RequestParam;

import smrs.backend_gestion_absence_ism.web.dto.ClasseWebDto;
import smrs.backend_gestion_absence_ism.web.dto.EtudiantListDto;

@RequestMapping("/api/web/classes")
@Tag(name = "Web - Admin Classe", description = "API pour la gestion des classes par l'administrateur")
public interface AdminClasseController {

        @Operation(summary = "Récupérer toutes les classes", description = "Permet de récupérer la liste des classes avec possibilité de filtrage par filière et niveau")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Liste des classes récupérée avec succès", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClasseWebDto.class)))),
                        @ApiResponse(responseCode = "500", description = "Erreur serveur")
        })
        @GetMapping("")
        ResponseEntity<List<ClasseWebDto>> getAllClasses(
                        @Parameter(description = "Filière pour le filtrage (optionnel)") @RequestParam(value = "searchFiliere", required = false, defaultValue = "") String searchFiliere,
                        @Parameter(description = "Niveau pour le filtrage (optionnel)") @RequestParam(value = "searchNiveau", required = false, defaultValue = "") String searchNiveau);

        @Operation(summary = "Récupérer les classes de l'année scolaire active", description = "Permet de récupérer la liste des classes ayant des étudiants inscrits pour l'année scolaire active")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Liste des classes de l'année active récupérée avec succès", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClasseWebDto.class)))),
                        @ApiResponse(responseCode = "404", description = "Aucune année scolaire active trouvée"),
                        @ApiResponse(responseCode = "500", description = "Erreur serveur")
        })
        @GetMapping("/active")
        ResponseEntity<List<ClasseWebDto>> getClassesForActiveYear();

        @Operation(summary = "Récupérer tous les etudiants d'une classe", description = "Retourne la liste de tous les etudiants enregistrés dans une classe via son ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Liste des étudiants récupérée avec succès", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EtudiantListDto.class)))),
                        @ApiResponse(responseCode = "404", description = "Aucun etduant trouvé"),
                        @ApiResponse(responseCode = "500", description = "Erreur serveur")
        })
        @GetMapping("{classeId}/etudiants")
        ResponseEntity<List<EtudiantListDto>> getAllEtudiantsByClasseId(@PathVariable String classeId);

        @Operation(summary = "Récupérer les infos d'une classe", description = "Retourne les infos d'une classe via son ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Infos Classe récupérée avec succès", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClasseWebDto.class)))),
                        @ApiResponse(responseCode = "404", description = "Aucune classe trouvé"),
                        @ApiResponse(responseCode = "500", description = "Erreur serveur")
        })
        @GetMapping("{classeId}")
        ResponseEntity<ClasseWebDto> getClasseById(@PathVariable String classeId);

}
