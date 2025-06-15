package smrs.backend_gestion_absence_ism.data.mocks;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import smrs.backend_gestion_absence_ism.data.entities.Classe;
import smrs.backend_gestion_absence_ism.data.enums.Filiere;
import smrs.backend_gestion_absence_ism.data.repositories.ClasseRepository;

@Component
@Order(3)
@RequiredArgsConstructor
public class ClasseMock implements CommandLineRunner {

    private final ClasseRepository classeRepository;

    @Override
    public void run(String... args) throws Exception {
        if (classeRepository.count() == 0) {
            createClasse("1", "GLRS2B", "L3", Filiere.GLRS);
            createClasse("2", "BIM3A", "L2", Filiere.BIM);
            createClasse("3", "MAIE1", "M1", Filiere.MAIE);
            createClasse("4", "CDSD2", "M2", Filiere.CDSD);
        }
    }

    private void createClasse(String id, String nom, String niveau, Filiere filiere) {
        Classe classe = new Classe();
        classe.setId(id);
        classe.setNom(nom);
        classe.setNiveau(niveau);
        classe.setFiliere(filiere);
        classe.onPrePersist();
        classeRepository.save(classe);
    }
}
