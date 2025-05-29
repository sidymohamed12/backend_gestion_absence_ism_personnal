package smrs.backend_gestion_absence_ism.mobile.controllers;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;
import smrs.backend_gestion_absence_ism.mobile.dto.request.JustificationRequestDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.JustificationMobileDto;

@RequestMapping("/api/mobile/etudiant")
public interface MobileEtudiantController {

    @Operation(summary = "Accueil étudiant", description = "Informations d'accueil de l'étudiant avec ses absences récentes")
    ResponseEntity<Map<String, Object>> getInfoEtudiantConnectAccueil(@PathVariable String etudiantId);

    @GetMapping("/{etudiantId}/absences")
    @Operation(summary = "Absences de l'étudiant", description = "Liste des absences avec possibilité de filtrage")
    ResponseEntity<Map<String, Object>> getAbsencesByEtudiant(@PathVariable String etudiantId,
            @RequestParam(required = false) TypeAbsence type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin);

    @PostMapping("/justification")
    ResponseEntity<JustificationMobileDto> creerJustification(
            @RequestPart("justification") JustificationRequestDto justificationDto);
}
