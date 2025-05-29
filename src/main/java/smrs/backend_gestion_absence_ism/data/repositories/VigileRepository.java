package smrs.backend_gestion_absence_ism.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import smrs.backend_gestion_absence_ism.data.entities.Vigile;

import java.util.Optional;

public interface VigileRepository extends MongoRepository<Vigile, String> {
    Optional<Vigile> findByBadge(String badge);
}