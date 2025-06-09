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

import smrs.backend_gestion_absence_ism.web.dto.CoursClasseWebDto;

@Tag(name = "Web - Admin Cours", description = "API pour la gestion des cours par l'administrateur")
@RequestMapping("/api/web/cours")
public interface AdminCoursController {

    @Operation(summary = "Récupérer les cours d'une classe", description = "Permet de récupérer tous les cours associés à une classe spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des cours récupérée avec succès", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CoursClasseWebDto.class)))),
            @ApiResponse(responseCode = "404", description = "Classe non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur serveur")
    })
    @GetMapping("/classes/{classeId}/cours")
    ResponseEntity<List<CoursClasseWebDto>> getCoursByClasse(
            @Parameter(description = "Identifiant de la classe") @PathVariable String classeId);
}
