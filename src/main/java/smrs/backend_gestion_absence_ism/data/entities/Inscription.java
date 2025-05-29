package smrs.backend_gestion_absence_ism.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "inscriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Inscription extends AbstractEntity {
    @DBRef
    private Etudiant etudiant;

    @DBRef
    private AnneeScolaire anneeScolaire;

    private LocalDate dateInscription = LocalDate.now();
    private boolean valide = true;
}
