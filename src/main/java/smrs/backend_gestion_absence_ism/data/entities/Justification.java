package smrs.backend_gestion_absence_ism.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import smrs.backend_gestion_absence_ism.data.enums.StatutJustification;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "justifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Justification extends AbstractEntity {
    private String description;
    private List<String> piecesJointes;
    private StatutJustification statut;
    private LocalDateTime dateValidation;

    @DBRef(lazy = true)
    private Absence absence;

    @DBRef(lazy = true)
    private Admin adminValidateur;
}
