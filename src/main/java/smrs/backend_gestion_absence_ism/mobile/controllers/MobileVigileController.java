package smrs.backend_gestion_absence_ism.mobile.controllers;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import smrs.backend_gestion_absence_ism.mobile.dto.request.PointageRequestDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.AbsenceMobileDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.EtudiantMobileDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.UtilisateurMobileDto;

@Tag(name = "Mobile - Vigile", description = "API pour la gestion des fonctionnalités du vigile via l'application mobile")
@RequestMapping("/api/mobile/vigile")
public interface MobileVigileController {

    @GetMapping("/{vigileId}")
    @Operation(summary = "Informations du vigile", description = "Récupère les informations du vigile connecté")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Informations du vigile récupérées avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UtilisateurMobileDto.class))),
            @ApiResponse(responseCode = "400", description = "Paramètres invalides"),
            @ApiResponse(responseCode = "404", description = "Vigile introuvable")
    })
    ResponseEntity<Map<String, Object>> getInfoVigileConnect(
            @Parameter(description = "Identifiant du vigile") @PathVariable String vigileId);

    @PostMapping("/pointage")
    @Operation(summary = "Effectuer le pointage d'un étudiant", description = "Enregistre la présence ou le retard d'un étudiant par scan de QR code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pointage enregistré avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AbsenceMobileDto.class))),
            @ApiResponse(responseCode = "400", description = "Paramètres invalides ou pointage déjà effectué"),
            @ApiResponse(responseCode = "404", description = "Étudiant ou vigile introuvable")
    })
    CompletableFuture<ResponseEntity<Map<String, Object>>> pointerEtudiant(
            @Parameter(description = "Données de pointage") @Valid @RequestBody PointageRequestDto requestDto);

    @GetMapping("/{vigileId}/historique")
    @Operation(summary = "Historique de pointage", description = "Récupère l'historique des pointages effectués par un vigile avec possibilité de filtrage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historique récupéré avec succès"),
            @ApiResponse(responseCode = "404", description = "Vigile introuvable")
    })
    ResponseEntity<Map<String, Object>> getHistoriquePointage(
            @Parameter(description = "Identifiant du vigile") @PathVariable String vigileId,
            @Parameter(description = "Date de pointage (optionnel)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Parameter(description = "Matricule de l'étudiant (optionnel)") @RequestParam(required = false) String etudiantMatricule);

    @GetMapping("/etudiant/**")
    @Operation(summary = "Recherche d'étudiant", description = "Récupère les informations d'un étudiant via son matricule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Étudiant trouvé avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EtudiantMobileDto.class))),
            @ApiResponse(responseCode = "404", description = "Étudiant introuvable")
    })
    ResponseEntity<Map<String, Object>> getEtudiantByRecherche(
            @Parameter(description = "Requête HTTP contenant le matricule de l'étudiant") HttpServletRequest request);
}
