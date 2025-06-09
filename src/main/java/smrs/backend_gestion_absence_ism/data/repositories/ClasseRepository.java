package smrs.backend_gestion_absence_ism.data.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import smrs.backend_gestion_absence_ism.data.entities.Classe;
import smrs.backend_gestion_absence_ism.data.enums.Filiere;

public interface ClasseRepository extends MongoRepository<Classe, String> {
    List<Classe> findByFiliere(Filiere filiere);

    List<Classe> findByNiveau(String niveau);

    List<Classe> findByFiliereAndNiveau(Filiere filiere, String niveau);
}
