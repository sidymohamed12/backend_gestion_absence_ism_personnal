package smrs.backend_gestion_absence_ism.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import smrs.backend_gestion_absence_ism.data.entities.Justification;
import smrs.backend_gestion_absence_ism.data.enums.StatutJustification;

import java.util.List;

public interface JustificationRepository extends MongoRepository<Justification, String> {
    List<Justification> findByStatut(StatutJustification statut);
}
