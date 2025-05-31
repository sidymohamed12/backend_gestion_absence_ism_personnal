package smrs.backend_gestion_absence_ism.mobile.controllers;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;
import smrs.backend_gestion_absence_ism.mobile.dto.request.JustificationRequestDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.AbsenceMobileDto;

@RequestMapping("/api/mobile/etudiant")
public interface MobileEtudiantController {

        @GetMapping("/{etudiantId}")
        @Operation(summary = "Accueil étudiant", description = "Informations d'accueil de l'étudiant avec ses absences récentes")
        ResponseEntity<Map<String, Object>> getInfoEtudiantConnectAccueil(@PathVariable String etudiantId);

        @GetMapping("/{etudiantId}/absences")
        @Operation(summary = "Absences de l'étudiant", description = "Liste des absences avec possibilité de filtrage")
        ResponseEntity<Map<String, Object>> getAbsencesByEtudiant(@PathVariable String etudiantId,
                        @RequestParam(required = false) TypeAbsence type,
                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin);

        @PostMapping(path = "/justification", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Justification enregistré avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AbsenceMobileDto.class))),
                        @ApiResponse(responseCode = "400", description = "Paramètres invalides"),
        })
        ResponseEntity<Map<String, Object>> creerJustification(
                        @RequestPart("justification") JustificationRequestDto request,
                        @RequestPart("document") MultipartFile document, String etudiantId);
}
