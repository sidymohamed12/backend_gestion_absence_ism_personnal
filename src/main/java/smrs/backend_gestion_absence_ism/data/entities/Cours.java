package smrs.backend_gestion_absence_ism.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "cours")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cours extends AbstractEntity {
    private String nom;
    private String enseignant;
    private String salle;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private String jour;
    private LocalDate date;
    @DBRef(lazy = true)
    private Matiere matiere;
    @DBRef(lazy = true)
    private Classe classe;
    private boolean pointageFerme = false;

    @DBRef
    private AnneeScolaire currentYear;

    @DBRef(lazy = true)
    private List<Absence> absences;
}