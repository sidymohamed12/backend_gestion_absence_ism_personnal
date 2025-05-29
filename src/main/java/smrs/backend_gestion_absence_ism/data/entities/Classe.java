package smrs.backend_gestion_absence_ism.data.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import smrs.backend_gestion_absence_ism.data.enums.Filiere;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "classes")
@NoArgsConstructor
@AllArgsConstructor
public class Classe extends AbstractEntity {
    private String nom;
    private String niveau;
    private Filiere filiere;

    @DBRef
    private List<Etudiant> etudiants = new ArrayList<>();
}
