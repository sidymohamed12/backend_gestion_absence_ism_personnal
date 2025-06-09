package smrs.backend_gestion_absence_ism.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;

import java.time.LocalDateTime;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "absences")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Absence extends AbstractEntity {
    private LocalTime duree;
    private TypeAbsence type;
    private LocalDateTime heurePointage;
    private Integer minutesRetard;

    @DBRef
    private Etudiant etudiant;

    @DBRef
    private Cours cours;

    @DBRef
    private Justification justification;

    @DBRef
    private Vigile vigile;
}
