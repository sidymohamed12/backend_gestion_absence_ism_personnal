package smrs.backend_gestion_absence_ism.data.mocks;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import smrs.backend_gestion_absence_ism.data.entities.AnneeScolaire;
import smrs.backend_gestion_absence_ism.data.entities.Classe;
import smrs.backend_gestion_absence_ism.data.entities.Cours;
import smrs.backend_gestion_absence_ism.data.entities.Matiere;
import smrs.backend_gestion_absence_ism.data.repositories.AnneeScolaireRepository;
import smrs.backend_gestion_absence_ism.data.repositories.ClasseRepository;
import smrs.backend_gestion_absence_ism.data.repositories.CoursRepository;
import smrs.backend_gestion_absence_ism.data.repositories.MatiereRepository;

@RequiredArgsConstructor
// @Component
public class AjoutCours implements CommandLineRunner {
    private final CoursRepository coursRepository;
    private final MatiereRepository matiereRepository;
    private final ClasseRepository classeRepository;
    private final AnneeScolaireRepository anneeScolaireRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Matiere> matieres = matiereRepository.findAll();
        List<Classe> classes = classeRepository.findAll();
        List<AnneeScolaire> annees = anneeScolaireRepository.findAll();

        Cours cours = new Cours();
        cours.setId(String.valueOf("12"));
        cours.setNom("Cours Test5");
        cours.setEnseignant("Sidy SAIZONOU");
        cours.setSalle("202");
        cours.setHeureDebut(LocalTime.of(00, 30));
        cours.setHeureFin(LocalTime.of(01, 58));
        cours.setDate(LocalDate.now());
        cours.setJour(cours.getDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRENCH));
        cours.setMatiere(matieres.get(1));
        cours.setClasse(classes.get(1));
        cours.setPointageFerme(false);
        cours.setCurrentYear(annees.stream().filter(AnneeScolaire::isActive).findFirst().orElse(annees.get(0)));
        cours.onPrePersist();
        coursRepository.save(cours);

    }

}
