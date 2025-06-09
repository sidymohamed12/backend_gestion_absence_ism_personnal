package smrs.backend_gestion_absence_ism.data.mocks;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import smrs.backend_gestion_absence_ism.data.entities.Matiere;
import smrs.backend_gestion_absence_ism.data.repositories.MatiereRepository;

// @Component
// @Order(1)
@RequiredArgsConstructor
public class MatiereMock implements CommandLineRunner {

    private final MatiereRepository matiereRepository;

    @Override
    public void run(String... args) throws Exception {
        if (matiereRepository.count() == 0) {
            createMatiere("1", "Programmation Java", "JAVA101", "Introduction à la programmation en Java");
            createMatiere("2", "Algorithmique", "ALG201", "Résolution de problèmes et structures de données");
            createMatiere("3", "Base de Données", "BD301", "Modélisation et requêtes SQL");
            createMatiere("4", "Réseaux Informatiques", "NET401", "Concepts de communication réseau");
            createMatiere("5", "Systèmes d’Exploitation", "SYS501", "Processus, threads et gestion mémoire");
            createMatiere("6", "Développement Web", "WEB601", "HTML, CSS, JavaScript et frameworks");
            createMatiere("7", "Architecture Logicielle", "ARCH701", "Conception et design d’applications logicielles");
        }
    }

    private void createMatiere(String id, String nom, String code, String description) {
        Matiere matiere = new Matiere();
        matiere.setId(id);
        matiere.setNom(nom);
        matiere.setCode(code);
        matiere.setDescription(description);
        matiere.onPrePersist();
        matiereRepository.save(matiere);
    }
}