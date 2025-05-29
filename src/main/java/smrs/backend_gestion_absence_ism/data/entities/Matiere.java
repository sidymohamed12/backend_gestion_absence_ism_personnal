package smrs.backend_gestion_absence_ism.data.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "matieres")
@NoArgsConstructor
@AllArgsConstructor
public class Matiere extends AbstractEntity {

    private String nom;
    private String code;
    private String description;
}
