package smrs.backend_gestion_absence_ism.data.mocks;

import smrs.backend_gestion_absence_ism.data.entities.Admin;
import smrs.backend_gestion_absence_ism.data.entities.Justification;
import smrs.backend_gestion_absence_ism.data.enums.StatutJustification;
import smrs.backend_gestion_absence_ism.data.repositories.AdminRepository;
import smrs.backend_gestion_absence_ism.data.repositories.JustificationRepository;

import java.util.Collections;
import java.util.Random;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@Order(5)
@RequiredArgsConstructor
public class JustificationMock implements CommandLineRunner {
    private final JustificationRepository justificationRepository;
    private final AdminRepository adminRepository;

    private static final String[] descriptions = {
            "Maladie avec certificat médical",
            "Problème de transport",
            "Urgence familiale",
            "Rendez-vous administratif",
            "Participation à un événement universitaire",
            "Problème de santé temporaire",
            "Convocation officielle",
            "Décès d'un proche"
    };

    private static final String[] documents = {
            "certificat_medical_01.pdf",
            "attestation_rdv_01.pdf",
            "convocation_tribunal_01.pdf",
            "certificat_medical_02.pdf",
            "attestation_hopital_01.pdf",
            "attestation_deces_01.pdf",
            "justificatif_transport_01.pdf",
            "attestation_evenement_01.pdf"
    };

    @Override
    public void run(String... args) throws Exception {
        if (justificationRepository.count() == 0) {
            Admin admin = adminRepository.findAll().stream().findFirst().orElse(null);
            if (admin == null) {
                System.out.println("Aucun admin trouvé pour valider les justifications");
                return;
            }

            Random random = new Random();
            for (int i = 0; i < 5; i++) {
                Justification justification = new Justification();
                justification.setId(String.valueOf(i + 1));
                justification.setDescription(descriptions[random.nextInt(descriptions.length)]);
                justification.setPiecesJointes(Collections.singletonList(documents[random.nextInt(documents.length)]));
                justification.setStatut(StatutJustification.EN_ATTENTE);
                justification.setDateValidation(null);
                justification.setAdminValidateur(admin);
                justification.onPrePersist();
                justificationRepository.save(justification);
            }
        }
    }

}
