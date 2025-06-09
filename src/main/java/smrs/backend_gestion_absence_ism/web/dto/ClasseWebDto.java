package smrs.backend_gestion_absence_ism.web.dto;

import lombok.Data;

@Data
public class ClasseWebDto {
    private String id;
    private String nom;
    private String niveau;
    private String filiere;
    private Integer effectif;
}
