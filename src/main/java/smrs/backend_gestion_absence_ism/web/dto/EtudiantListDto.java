package smrs.backend_gestion_absence_ism.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtudiantListDto {
    private String id;
    private String nom;
    private String prenom;
    private String matricule;
    private String email;
}