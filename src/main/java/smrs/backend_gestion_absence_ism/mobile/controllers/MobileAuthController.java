package smrs.backend_gestion_absence_ism.mobile.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import smrs.backend_gestion_absence_ism.mobile.dto.request.LoginRequestDTO;
import smrs.backend_gestion_absence_ism.mobile.dto.response.LoginResponseDto;

import java.util.Map;

@Tag(name = "Mobile - Authentification", description = "API pour la gestion de l'authentification des utilisateurs mobiles")
@RequestMapping("/api/mobile/auth")
public interface MobileAuthController {
    
    @PostMapping("/login")
    @Operation(
        summary = "Connexion utilisateur", 
        description = "Authentifie un utilisateur (étudiant ou vigile) et retourne un token JWT"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Connexion réussie",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))
        ),
        @ApiResponse(responseCode = "401", description = "Identifiants invalides"),
        @ApiResponse(responseCode = "400", description = "Données de requête invalides")
    })
    ResponseEntity<Map<String, Object>> login(
        @Parameter(description = "Informations de connexion", required = true)
        @Valid @RequestBody LoginRequestDTO loginRequest);

    @PostMapping("/logout")
    @Operation(
        summary = "Déconnexion", 
        description = "Déconnecte l'utilisateur actuellement authentifié"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Déconnexion réussie"),
        @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    ResponseEntity<Map<String, Object>> logout();
}
