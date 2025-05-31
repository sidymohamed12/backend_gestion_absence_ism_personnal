package smrs.backend_gestion_absence_ism.mobile.dto.request;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Requête pour ajouter une justification à une absence")

public class JustificationRequestDto {
    @Schema(description = "Identifiant de l'absence concernée", example = "663bd21f9f5c4c7d88ab1234")
    private String absenceId;

    @Schema(description = "Description de la justification", example = "Maladie avec certificat médical")
    private String description;

    @JsonIgnore
    @Schema(description = "")
    private MultipartFile pieceJointe;
}
