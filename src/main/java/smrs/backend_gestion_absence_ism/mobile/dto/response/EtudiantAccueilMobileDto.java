package smrs.backend_gestion_absence_ism.mobile.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Informations affichées sur l'accueil mobile de l'étudiant")
public class EtudiantAccueilMobileDto {

    @Schema(description = "ID de l'étudiant", example = "663bd21f9f5c4c7d88ab9999")
    private String id;

    @Schema(description = "Nom", example = "SY")
    private String nom;

    @Schema(description = "Prénom", example = "Moussa")
    private String prenom;

    @Schema(description = "Matricule", example = "ISMDK-2425")
    private String matricule;

    @Schema(description = "Classe", example = "L1 Informatique")
    private String classe;

    private List<AbsenceMobileDto> absences;
}
