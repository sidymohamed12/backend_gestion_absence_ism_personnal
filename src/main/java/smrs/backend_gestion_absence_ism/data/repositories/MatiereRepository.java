package smrs.backend_gestion_absence_ism.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import smrs.backend_gestion_absence_ism.data.entities.Matiere;

public interface MatiereRepository extends MongoRepository<Matiere, String> {

}
