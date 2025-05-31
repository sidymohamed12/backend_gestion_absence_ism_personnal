package smrs.backend_gestion_absence_ism.mobile.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import smrs.backend_gestion_absence_ism.data.entities.Classe;
import smrs.backend_gestion_absence_ism.data.entities.Utilisateur;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Informations affichées après recherche de l'étudiant via matricule (par le vigile)")
public class EtudiantMobileDto {
    private String nom;
    private String prenom;
    private String matricule;
    private String classe;
}
