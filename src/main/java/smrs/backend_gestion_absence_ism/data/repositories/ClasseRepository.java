package smrs.backend_gestion_absence_ism.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import smrs.backend_gestion_absence_ism.data.entities.Classe;

public interface ClasseRepository extends MongoRepository<Classe, String> {

}
