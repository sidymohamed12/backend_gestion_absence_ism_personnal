package smrs.backend_gestion_absence_ism.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.index.Indexed;
import smrs.backend_gestion_absence_ism.data.enums.Role;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur extends AbstractEntity {
    private String nom;
    private String prenom;
    @Indexed(unique = true)
    private String email;
    private String motDePasse;
    private Role role;
}
