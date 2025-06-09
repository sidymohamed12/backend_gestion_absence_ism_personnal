package smrs.backend_gestion_absence_ism.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import smrs.backend_gestion_absence_ism.data.entities.Cours;

import java.time.LocalDate;
import java.util.List;

public interface CoursRepository extends MongoRepository<Cours, String> {

    List<Cours> findByClasseIdAndDateAndCurrentYearId(String classeId, LocalDate date, String currentYearId);

    List<Cours> findByClasseId(String classeId);

    List<Cours> findByCurrentYearId(String anneeScolaireId);

    List<Cours> findByDateAndCurrentYearIdAndPointageFermeFalse(LocalDate date, String anneeScolaireId);
}
