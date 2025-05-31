package smrs.backend_gestion_absence_ism.mobile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Requête de connexion")
public class LoginRequestDTO {

    @Schema(description = "Email de l'utilisateur", example = "sidy@gmail.com")
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    @Schema(description = "Mot de passe", example = "password123")
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasse;

}
