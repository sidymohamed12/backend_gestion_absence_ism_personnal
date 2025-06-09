package smrs.backend_gestion_absence_ism;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import smrs.backend_gestion_absence_ism.mobile.dto.request.LoginRequestDTO;
import smrs.backend_gestion_absence_ism.mobile.dto.response.LoginResponseDto;
import smrs.backend_gestion_absence_ism.services.impl.AuthentificationServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentification", description = "API pour la gestion de l'authentification et l'autorisation")
public class AuthController {

    private final AuthentificationServiceImpl authService;

    @Operation(summary = "Connexion utilisateur", description = "Authentifie un utilisateur et retourne un token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Connexion réussie", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Identifiants invalides"),
            @ApiResponse(responseCode = "400", description = "Données de requête invalides")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDto response = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Déconnexion", description = "Déconnecte l'utilisateur actuellement authentifié")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Déconnexion réussie"),
            @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        authService.logout();
        return ResponseEntity.ok("Déconnexion réussie.");
    }

}