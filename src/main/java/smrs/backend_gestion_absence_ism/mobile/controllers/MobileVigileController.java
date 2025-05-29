package smrs.backend_gestion_absence_ism.mobile.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
}
