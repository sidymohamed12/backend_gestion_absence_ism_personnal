package smrs.backend_gestion_absence_ism.data.mocks;

import smrs.backend_gestion_absence_ism.data.entities.Absence;
import smrs.backend_gestion_absence_ism.data.entities.Cours;
import smrs.backend_gestion_absence_ism.data.entities.Etudiant;
import smrs.backend_gestion_absence_ism.data.entities.Justification;
import smrs.backend_gestion_absence_ism.data.entities.Vigile;
import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;
import smrs.backend_gestion_absence_ism.data.repositories.AbsenceRepository;
import smrs.backend_gestion_absence_ism.data.repositories.CoursRepository;
import smrs.backend_gestion_absence_ism.data.repositories.EtudiantRepository;
import smrs.backend_gestion_absence_ism.data.repositories.JustificationRepository;
import smrs.backend_gestion_absence_ism.data.repositories.VigileRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * Génère des données de test pour les absences
 */

@Component
@Order(7)
@RequiredArgsConstructor
public class AbsenceMock implements CommandLineRunner {

    private final AbsenceRepository absenceRepository;
    private final EtudiantRepository etudiantRepository;
    private final CoursRepository coursRepository;
    private final JustificationRepository justificationRepository;
    private final VigileRepository vigileRepository;

    @Override
    public void run(String... args) {
        if (absenceRepository.count() == 0) {
            List<Etudiant> etudiants = etudiantRepository.findAll();
            List<Cours> cours = coursRepository.findAll();
            List<Vigile> vigiles = vigileRepository.findAll();
            List<Justification> justifications = justificationRepository.findAll();

            if (etudiants.isEmpty() || cours.isEmpty() || vigiles.isEmpty()) {
                System.out.println("Pas assez de données pour créer des absences");
                return;
            }

            Random random = new Random();

            for (int i = 0; i < 10; i++) {
                Absence absence = new Absence();
                absence.setId(String.valueOf(i + 1));
                absence.setDuree(LocalTime.of(1, 0)); // 1h d'absence

                // Type aléatoire: ABSENCE_COMPLETE ou RETARD
                absence.setType(random.nextBoolean() ? TypeAbsence.ABSENCE_COMPLETE : TypeAbsence.RETARD);

                absence.setHeurePointage(LocalDateTime.now().minusDays(random.nextInt(10)));

                // Minutes retard : 0 si ABSENCE_COMPLETE sinon entre 1 et 30
                absence.setMinutesRetard(absence.getType() == TypeAbsence.RETARD ? random.nextInt(30) + 1 : 0);

                absence.setEtudiant(etudiants.get(random.nextInt(etudiants.size())));
                absence.setCours(cours.get(random.nextInt(cours.size())));

                // Parfois liée à une justification
                if (!justifications.isEmpty() && random.nextBoolean()) {
                    absence.setJustification(justifications.get(random.nextInt(justifications.size())));
                }

                if (absence.getType() == TypeAbsence.RETARD) {
                    absence.setVigile(vigiles.get(random.nextInt(vigiles.size())));
                } else {
                    absence.setVigile(null);
                }

                absence.onPrePersist();

                absenceRepository.save(absence);

            }
        }
    }
}
