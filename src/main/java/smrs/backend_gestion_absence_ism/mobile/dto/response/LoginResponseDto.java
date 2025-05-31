package smrs.backend_gestion_absence_ism.mobile.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Réponse de connexion")
public class LoginResponseDto {
    @Schema(description = "Token JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    private String accessToken;
    private String refreshToken;

    @Schema(description = "Type de token", example = "Bearer")
    @Builder.Default
    private String type = "Bearer";

    @Schema(description = "Informations utilisateur")
    private UtilisateurMobileDto utilisateur;
}
