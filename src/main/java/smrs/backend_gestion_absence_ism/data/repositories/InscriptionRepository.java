package smrs.backend_gestion_absence_ism.data.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import smrs.backend_gestion_absence_ism.data.entities.AnneeScolaire;
import smrs.backend_gestion_absence_ism.data.entities.Inscription;

public interface InscriptionRepository extends MongoRepository<Inscription, String> {
    List<Inscription> findByAnneeScolaireAndValide(AnneeScolaire anneeScolaire, boolean valide);
}
