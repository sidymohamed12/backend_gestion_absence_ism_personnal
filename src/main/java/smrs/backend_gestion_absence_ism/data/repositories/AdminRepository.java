package smrs.backend_gestion_absence_ism.data.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import smrs.backend_gestion_absence_ism.data.entities.Admin;

public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Admin> findByUtilisateurId(String utilisateurId);

}