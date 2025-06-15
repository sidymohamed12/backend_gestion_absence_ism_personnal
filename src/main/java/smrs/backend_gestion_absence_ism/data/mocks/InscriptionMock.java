package smrs.backend_gestion_absence_ism.data.mocks;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import smrs.backend_gestion_absence_ism.data.entities.AnneeScolaire;
import smrs.backend_gestion_absence_ism.data.entities.Etudiant;
import smrs.backend_gestion_absence_ism.data.entities.Inscription;
import smrs.backend_gestion_absence_ism.data.repositories.AnneeScolaireRepository;
import smrs.backend_gestion_absence_ism.data.repositories.EtudiantRepository;
import smrs.backend_gestion_absence_ism.data.repositories.InscriptionRepository;

@Component
@Order(8)
@RequiredArgsConstructor
public class InscriptionMock implements CommandLineRunner {

    private final InscriptionRepository inscriptionRepository;
    private final EtudiantRepository etudiantRepository;
    private final AnneeScolaireRepository anneeScolaireRepository;

    @Override
    public void run(String... args) {
        if (inscriptionRepository.count() == 0) {
            AnneeScolaire anneeScolaire = anneeScolaireRepository.findByActive(Boolean.TRUE);

            if (anneeScolaire == null) {
                System.out.println("L'année scolaire active est introuvable.");
                return;
            }

            List<Etudiant> etudiants = etudiantRepository.findAll();

            int index = 1;
            for (Etudiant etudiant : etudiants) {
                Inscription inscription = new Inscription();
                inscription.setId(String.valueOf(index++));
                inscription.setEtudiant(etudiant);
                inscription.setAnneeScolaire(anneeScolaire);
                inscription.setDateInscription(LocalDate.now());
                inscription.setValide(true);
                inscription.onPrePersist();

                inscriptionRepository.save(inscription);
            }

        }
    }
}
