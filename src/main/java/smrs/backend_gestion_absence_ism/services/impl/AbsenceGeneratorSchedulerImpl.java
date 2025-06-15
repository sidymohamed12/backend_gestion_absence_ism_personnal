package smrs.backend_gestion_absence_ism.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import smrs.backend_gestion_absence_ism.data.entities.Absence;
import smrs.backend_gestion_absence_ism.data.entities.AnneeScolaire;
import smrs.backend_gestion_absence_ism.data.entities.Cours;
import smrs.backend_gestion_absence_ism.data.entities.Etudiant;
import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;
import smrs.backend_gestion_absence_ism.data.repositories.AbsenceRepository;
import smrs.backend_gestion_absence_ism.data.repositories.AnneeScolaireRepository;
import smrs.backend_gestion_absence_ism.data.repositories.CoursRepository;
import smrs.backend_gestion_absence_ism.data.repositories.EtudiantRepository;
import smrs.backend_gestion_absence_ism.services.AbsenceGeneratorScheduler;

@Service
@RequiredArgsConstructor
@Slf4j
public class AbsenceGeneratorSchedulerImpl implements AbsenceGeneratorScheduler {
    private final CoursRepository coursRepository;
    private final AbsenceRepository absenceRepository;
    private final AnneeScolaireRepository anneeScolaireRepository;
    private final EtudiantRepository etudiantRepository;

    /**
     * Tâche planifiée qui s'exécute toutes les minutes pour vérifier
     * si des cours viennent de se terminer et générer les absences
     * des étudiants non pointés
     */

    @Override
    @Scheduled(cron = "0 * * * * *") // Toutes les minutes
    @Transactional
    public void genererAbsencesPourCoursTermines() {
        LocalDate aujourdhui = LocalDate.now();
        LocalTime maintenant = LocalTime.now();

        AnneeScolaire anneeActive = anneeScolaireRepository.findByActive(true);
        if (anneeActive == null) {
            return;
        }

        List<Cours> coursTermines = coursRepository.findByCurrentYearIdAndHeureFinBetween(
                anneeActive.getId(),
                maintenant.minus(1, ChronoUnit.MINUTES), // 1 minute avant maintenant
                maintenant);

        log.info("{} cours terminés à {} le {}", coursTermines.size(), maintenant, aujourdhui);

        coursTermines.forEach(cours -> {
            if (cours.isPointageFerme()) {
                log.info("Pointage déjà fermé pour le cours {} - {}",
                        cours.getNom(), cours.getHeureFin());
                return;
            }

            List<Etudiant> etudiantsClasse = etudiantRepository.findByClasseId(cours.getClasse().getId());

            List<String> etudiantsPointes = absenceRepository.findByCoursId(cours.getId())
                    .stream()
                    .map(absence -> absence.getEtudiant().getId())
                    .toList();

            List<Etudiant> etudiantsNonPointes = etudiantsClasse.stream()
                    .filter(etudiant -> !etudiantsPointes.contains(etudiant.getId()))
                    .toList();

            etudiantsNonPointes.forEach(etudiant -> {
                Absence absence = new Absence();
                absence.setType(TypeAbsence.ABSENCE_COMPLETE);
                absence.setEtudiant(etudiant);
                absence.setCours(cours);
                absence.setHeurePointage(LocalDateTime.now());
                absence.onPrePersist();
                absenceRepository.save(absence);
            });

            cours.setPointageFerme(true);
            coursRepository.save(cours);
        });
    }

}
