package smrs.backend_gestion_absence_ism.data.mocks;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import smrs.backend_gestion_absence_ism.data.entities.AnneeScolaire;
import smrs.backend_gestion_absence_ism.data.repositories.AnneeScolaireRepository;

// @Component
// @Order(2)
@RequiredArgsConstructor
public class AnneeScolaireMock implements CommandLineRunner {

    private final AnneeScolaireRepository anneeScolaireRepository;

    @Override
    public void run(String... args) {
        if (anneeScolaireRepository.count() == 0) {
            AnneeScolaire annee = new AnneeScolaire();
            annee.setId("1");
            annee.setLabel("2024-2025");
            annee.setStartDate(LocalDate.of(2024, 10, 1));
            annee.setEndDate(LocalDate.of(2025, 6, 30));
            annee.setActive(true);
            annee.onPrePersist();
            anneeScolaireRepository.save(annee);
        }
    }
}