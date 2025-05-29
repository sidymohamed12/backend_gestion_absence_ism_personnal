package smrs.backend_gestion_absence_ism.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "vigiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vigile extends AbstractEntity {

    @DBRef
    private Utilisateur utilisateur;
    @Indexed(unique = true)
    private String badge;

}
