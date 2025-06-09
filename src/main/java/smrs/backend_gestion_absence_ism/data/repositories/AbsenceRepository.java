package smrs.backend_gestion_absence_ism.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import smrs.backend_gestion_absence_ism.data.entities.Absence;
import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;

import java.time.LocalDateTime;
import java.util.List;

public interface AbsenceRepository extends MongoRepository<Absence, String> {
    List<Absence> findByEtudiantId(String etudiantId);
    List<Absence> findByCreatedAt(LocalDateTime createdAt);
    List<Absence> findByCoursId(String coursId);
    List<Absence> findByEtudiantIdAndType(String etudiantId, TypeAbsence type);
    List<Absence> findByEtudiantIdAndCreatedAtBetween(String etudiantId, LocalDateTime startDate,
            LocalDateTime endDate);
    boolean existsByEtudiantIdAndCoursId(String etudiantId, String coursId);
    List<Absence> findByVigileIdOrderByHeurePointageDesc(String vigileId);

}
