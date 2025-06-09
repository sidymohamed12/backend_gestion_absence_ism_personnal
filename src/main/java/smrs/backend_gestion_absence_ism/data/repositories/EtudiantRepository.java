package smrs.backend_gestion_absence_ism.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import smrs.backend_gestion_absence_ism.data.entities.Etudiant;

import java.util.List;
import java.util.Optional;

public interface EtudiantRepository extends MongoRepository<Etudiant, String> {
    Optional<Etudiant> findByMatricule(String matricule);

    Optional<Etudiant> findByUtilisateurId(String utilisateurId);

    List<Etudiant> findByClasseId(String classeId);

    Integer countEtudiantByClasseId(String classeId);
}