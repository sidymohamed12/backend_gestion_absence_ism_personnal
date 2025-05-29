package smrs.backend_gestion_absence_ism.data.entities;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "anneesScolaires")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnneeScolaire extends AbstractEntity {
    private String label;

    private LocalDate startDate;
    private LocalDate endDate;

    private boolean active;

    @DBRef(lazy = true)
    private List<Inscription> inscriptions;

}
