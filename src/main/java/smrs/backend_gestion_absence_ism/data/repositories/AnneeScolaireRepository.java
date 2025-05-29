package smrs.backend_gestion_absence_ism.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import smrs.backend_gestion_absence_ism.data.entities.AnneeScolaire;

public interface AnneeScolaireRepository extends MongoRepository<AnneeScolaire, String> {
    AnneeScolaire findByActive(Boolean active);
}
