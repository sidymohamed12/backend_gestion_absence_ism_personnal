package smrs.backend_gestion_absence_ism.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import smrs.backend_gestion_absence_ism.data.entities.Cours;

import java.util.List;

public interface CoursRepository extends MongoRepository<Cours, String> {
    List<Cours> findByJour(String jour);

    // ---------------- Sidy -----------
    List<Cours> findByClasseIdAndJourAndCurrentYearId(String classeId, String jour, String currentYearId);

    List<Cours> findByClasseId(String classeId);

    List<Cours> findByJourAndCurrentYearId(String jour, String currentYearId);
}
