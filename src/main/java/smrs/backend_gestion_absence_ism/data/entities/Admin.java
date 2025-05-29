package smrs.backend_gestion_absence_ism.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "admins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends AbstractEntity {
    private String departement;

    @DBRef
    private Utilisateur utilisateur;

}
