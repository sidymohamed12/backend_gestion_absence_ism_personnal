package smrs.backend_gestion_absence_ism.data.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "etudiants")
@Data
@NoArgsConstructor
public class Etudiant extends AbstractEntity {
    @Indexed(unique = true)
    private String matricule;

    @DBRef
    private Utilisateur utilisateur;

    @DBRef
    private Classe classe;

    @DBRef(lazy = true)
    private List<Absence> absences = new ArrayList<>();

    @DBRef(lazy = true)
    private List<Inscription> inscriptions = new ArrayList<>();

}

// Le QR code d’un étudiant ne doit être valide que s’il est inscrit à l’année
// scolaire active