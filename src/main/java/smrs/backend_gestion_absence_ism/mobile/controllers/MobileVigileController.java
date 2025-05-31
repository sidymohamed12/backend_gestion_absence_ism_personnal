package smrs.backend_gestion_absence_ism.mobile.controllers;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import smrs.backend_gestion_absence_ism.mobile.dto.request.PointageRequestDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.AbsenceMobileDto;

@RequestMapping("/api/mobile/vigile")
public interface MobileVigileController {
    @PostMapping("/pointage")
    @Operation(summary = "Effectuer le pointage d'un étudiant par scan QR")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pointage enregistré avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AbsenceMobileDto.class))),
            @ApiResponse(responseCode = "400", description = "Paramètres invalides ou pointage déjà effectué"),
            @ApiResponse(responseCode = "404", description = "Étudiant ou vigile introuvable")
    })
    ResponseEntity<Map<String, Object>> pointerEtudiant(@Valid @RequestBody PointageRequestDto requestDto);

    @GetMapping("/{vigileId}/historique")
    @Operation(summary = "Historique de pointage", description = "Récupère l'historique des pointages effectués par un vigile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lister historique avec succès"),
            @ApiResponse(responseCode = "404", description = "Vigile introuvable")
    })
    ResponseEntity<Map<String, Object>> getHistoriquePointage(@PathVariable String vigileId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin);

    @GetMapping("/etudiant/**")
    @Operation(summary = "Recherche Etudiant", description = "Récupére un etudiant via matricule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "etudiant trouvé avec succès"),
            @ApiResponse(responseCode = "404", description = "etudiant introuvable")
    })
    ResponseEntity<Map<String, Object>> getEtudiantByRecherche(HttpServletRequest request);
}
