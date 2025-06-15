package smrs.backend_gestion_absence_ism.data.mocks;

import smrs.backend_gestion_absence_ism.data.entities.AnneeScolaire;
import smrs.backend_gestion_absence_ism.data.entities.Classe;
import smrs.backend_gestion_absence_ism.data.entities.Cours;
import smrs.backend_gestion_absence_ism.data.entities.Matiere;
import smrs.backend_gestion_absence_ism.data.repositories.AnneeScolaireRepository;
import smrs.backend_gestion_absence_ism.data.repositories.ClasseRepository;
import smrs.backend_gestion_absence_ism.data.repositories.CoursRepository;
import smrs.backend_gestion_absence_ism.data.repositories.MatiereRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@Order(6)
@RequiredArgsConstructor
public class CoursMock implements CommandLineRunner {

    private final CoursRepository coursRepository;
    private final MatiereRepository matiereRepository;
    private final ClasseRepository classeRepository;
    private final AnneeScolaireRepository anneeScolaireRepository;

    private final String[] nomsCours = {
            "Introduction à Java",
            "Structures de données",
            "Bases de données",
            "Systèmes d'exploitation",
            "Réseaux informatiques",
            "Développement Web",
            "Architecture logicielle"
    };

    private final String[] enseignants = {
            "Mme Diallo", "M. Ndiaye", "Mme Ba", "M. Fall", "Mme Sarr", "M. Diop", "Mme Cissé"
    };

    private final String[] salles = {
            "101", "202", "303", "404", "505", "606", "707"
    };

    @Override
    public void run(String... args) {
        if (coursRepository.count() == 0) {
            List<Matiere> matieres = matiereRepository.findAll();
            List<Classe> classes = classeRepository.findAll();
            List<AnneeScolaire> annees = anneeScolaireRepository.findAll();

            if (matieres.isEmpty() || classes.isEmpty() || annees.isEmpty()) {
                System.out.println("Données insuffisantes pour créer des cours");
                return;
            }

            Random random = new Random();

            for (int i = 0; i < 7; i++) {
                Cours cours = new Cours();

                cours.setId(String.valueOf(i + 1));
                cours.setNom(nomsCours[i]);
                cours.setEnseignant(enseignants[random.nextInt(enseignants.length)]);
                cours.setSalle(salles[random.nextInt(salles.length)]);
                int startHour = 8 + random.nextInt(9); // 8 à 16
                LocalTime heureDebut = LocalTime.of(startHour, 0);
                LocalTime heureFin = heureDebut.plusHours(2);

                cours.setHeureDebut(heureDebut);
                cours.setHeureFin(heureFin);
                cours.setDate(LocalDate.now());
                cours.setJour(cours.getDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRENCH));

                cours.setMatiere(matieres.get(random.nextInt(matieres.size())));
                cours.setClasse(classes.get(random.nextInt(classes.size())));
                cours.setPointageFerme(false);
                cours.setCurrentYear(annees.stream().filter(AnneeScolaire::isActive).findFirst().orElse(annees.get(0)));
                cours.onPrePersist();
                coursRepository.save(cours);
            }
        }
    }
}
