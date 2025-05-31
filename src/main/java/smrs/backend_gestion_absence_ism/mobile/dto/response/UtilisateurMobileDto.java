package smrs.backend_gestion_absence_ism.mobile.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import smrs.backend_gestion_absence_ism.data.enums.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Informations utilisateur")
public class UtilisateurMobileDto {
    @Schema(description = "ID de l'utilisateur", example = "3")
    private String id;

    @Schema(description = "Nom", example = "SAIZONOU")
    private String nom;

    @Schema(description = "Prénom", example = "Sidy")
    private String prenom;

    @Schema(description = "Email", example = "sidy@gmail.com")
    private String email;

    @Schema(description = "Rôle", example = "ETUDIANT")
    private Role role;

    // Champs spécifiques selon le rôle
    @Schema(description = "Matricule (pour etudiant)", example = "ISMDK-2425")
    private String matricule;

    @Schema(description = "Classe (pour etudiant)", example = "L1 Informatique")
    private String classe;

    @Schema(description = "Liste des 5 derniers Absences (pour etudiant)")
    private List<AbsenceMobileDto> absences;

    @Schema(description = "Département (pour admin)", example = "Informatique")
    private String departement;

    @Schema(description = "Badge (pour vigile)", example = "VIG001")
    private String badge;

}
