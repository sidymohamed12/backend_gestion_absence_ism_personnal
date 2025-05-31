package smrs.backend_gestion_absence_ism.mobile.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import smrs.backend_gestion_absence_ism.mobile.dto.request.LoginRequestDTO;

import java.util.Map;

@RequestMapping("/api/mobile/auth")
public interface MobileAuthController {
    @PostMapping("/login")
    @Operation(summary = "Connexion utilisateur", description = "Connecte un utilisateur et retourne un token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Connexion réussie"),
            @ApiResponse(responseCode = "401", description = "Identifiants invalides"),
            @ApiResponse(responseCode = "400", description = "Données de requête invalides")
    })
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequestDTO loginRequest);

    @PostMapping("/logout")
    @Operation(summary = "Déconnexion", description = "Déconnecte l'utilisateur (côté client)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Déconnexion réussie"),
            @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    // @PreAuthorize("hasRole('ETUDIANT') or hasRole('VIGILE')")
    public ResponseEntity<Map<String, Object>> logout();
}
