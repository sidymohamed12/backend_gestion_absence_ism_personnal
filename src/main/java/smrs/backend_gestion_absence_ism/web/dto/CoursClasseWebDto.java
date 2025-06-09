package smrs.backend_gestion_absence_ism.web.dto;

import lombok.Data;

@Data
public class CoursClasseWebDto {
    private String id;
    private String nom;
    private String enseignant;
    private String salle;
    private String nomMatiere;
}
