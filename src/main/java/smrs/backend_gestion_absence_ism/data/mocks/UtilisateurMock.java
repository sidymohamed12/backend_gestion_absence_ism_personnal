package smrs.backend_gestion_absence_ism.data.mocks;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import smrs.backend_gestion_absence_ism.data.entities.Admin;
import smrs.backend_gestion_absence_ism.data.entities.Classe;
import smrs.backend_gestion_absence_ism.data.entities.Etudiant;
import smrs.backend_gestion_absence_ism.data.entities.Utilisateur;
import smrs.backend_gestion_absence_ism.data.entities.Vigile;
import smrs.backend_gestion_absence_ism.data.enums.Role;
import smrs.backend_gestion_absence_ism.data.repositories.AdminRepository;
import smrs.backend_gestion_absence_ism.data.repositories.ClasseRepository;
import smrs.backend_gestion_absence_ism.data.repositories.EtudiantRepository;
import smrs.backend_gestion_absence_ism.data.repositories.UtilisateurRepository;
import smrs.backend_gestion_absence_ism.data.repositories.VigileRepository;

import java.util.List;

@Component
@Order(4)
@RequiredArgsConstructor
public class UtilisateurMock implements CommandLineRunner {

    private final EtudiantRepository etudiantRepository;
    private final VigileRepository vigileRepository;
    private final AdminRepository adminRepository;
    private final UtilisateurRepository utilisateurRepository;
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final ClasseRepository classeRepository;

    @Override
    public void run(String... args) {
        if (utilisateurRepository.count() == 0) {
            // ----------------------
            // ADMIN USERS
            // ----------------------
            Utilisateur u1 = new Utilisateur();
            u1.setNom("Diop");
            u1.setPrenom("Cheikh");
            u1.setEmail("admin1@ism.edu.sn");
            u1.setMotDePasse(passwordEncoder.encode("admin123"));
            u1.setRole(Role.ADMIN);
            u1.onPrePersist();

            Utilisateur u2 = new Utilisateur();
            u2.setNom("Faye");
            u2.setPrenom("Fatou");
            u2.setEmail("admin2@ism.edu.sn");
            u2.setMotDePasse(passwordEncoder.encode("admin123"));
            u2.setRole(Role.ADMIN);
            u2.onPrePersist();

            utilisateurRepository.saveAll(List.of(u1, u2));

            Admin admin1 = new Admin();
            admin1.setId("1");
            admin1.setDepartement("Informatique");
            admin1.setUtilisateur(u1);
            admin1.onPrePersist();

            Admin admin2 = new Admin();
            admin2.setId("2");
            admin2.setDepartement("Droit");
            admin2.setUtilisateur(u2);
            admin2.onPrePersist();

            adminRepository.saveAll(List.of(admin1, admin2));

            // ----------------------
            // VIGILE USERS
            // ----------------------
            Utilisateur u3 = new Utilisateur();
            u3.setNom("Sow");
            u3.setPrenom("Modou");
            u3.setEmail("vigile1@ism.edu.sn");
            u3.setMotDePasse(passwordEncoder.encode("vigile123"));
            u3.setRole(Role.VIGILE);
            u3.onPrePersist();

            Utilisateur u4 = new Utilisateur();
            u4.setNom("Ndoye");
            u4.setPrenom("Awa");
            u4.setEmail("vigile2@ism.edu.sn");
            u4.setMotDePasse(passwordEncoder.encode("vigile123"));
            u4.setRole(Role.VIGILE);
            u4.onPrePersist();

            Utilisateur u5 = new Utilisateur();
            u5.setNom("Fall");
            u5.setPrenom("Pape");
            u5.setEmail("vigile3@ism.edu.sn");
            u5.setMotDePasse(passwordEncoder.encode("vigile123"));
            u5.setRole(Role.VIGILE);
            u5.onPrePersist();

            utilisateurRepository.saveAll(List.of(u3, u4, u5));

            Vigile v1 = new Vigile();
            v1.setId("3");
            v1.setBadge("VG001");
            v1.setUtilisateur(u3);
            v1.onPrePersist();

            Vigile v2 = new Vigile();
            v2.setId("4");
            v2.setBadge("VG002");
            v2.setUtilisateur(u4);
            v2.onPrePersist();

            Vigile v3 = new Vigile();
            v3.setId("5");
            v3.setBadge("VG003");
            v3.setUtilisateur(u5);
            v3.onPrePersist();

            vigileRepository.saveAll(List.of(v1, v2, v3));

            // ----------------------
            // ETUDIANT USERS
            // ----------------------
            List<Classe> classes = classeRepository.findAll();
            if (classes.isEmpty())
                throw new RuntimeException("Aucune classe trouvée.");

            Utilisateur u6 = new Utilisateur();
            u6.setNom("Sarr");
            u6.setPrenom("Alioune");
            u6.setEmail("alioune@ism.edu.sn");
            u6.setMotDePasse(passwordEncoder.encode("etudiant123"));
            u6.setRole(Role.ETUDIANT);
            u6.onPrePersist();

            Utilisateur u7 = new Utilisateur();
            u7.setNom("Diouf");
            u7.setPrenom("Mame");
            u7.setEmail("mame@ism.edu.sn");
            u7.setMotDePasse(passwordEncoder.encode("etudiant123"));
            u7.setRole(Role.ETUDIANT);
            u7.onPrePersist();

            Utilisateur u8 = new Utilisateur();
            u8.setNom("Sy");
            u8.setPrenom("Lamine");
            u8.setEmail("lamine@ism.edu.sn");
            u8.setMotDePasse(passwordEncoder.encode("etudiant123"));
            u8.setRole(Role.ETUDIANT);
            u8.onPrePersist();

            Utilisateur u9 = new Utilisateur();
            u9.setNom("Ndiaye");
            u9.setPrenom("Ndeye");
            u9.setEmail("ndeye@ism.edu.sn");
            u9.setMotDePasse(passwordEncoder.encode("etudiant123"));
            u9.setRole(Role.ETUDIANT);
            u9.onPrePersist();

            Utilisateur u10 = new Utilisateur();
            u10.setNom("Thiam");
            u10.setPrenom("Ibrahima");
            u10.setEmail("ibrahima@ism.edu.sn");
            u10.setMotDePasse(passwordEncoder.encode("etudiant123"));
            u10.setRole(Role.ETUDIANT);
            u10.onPrePersist();

            utilisateurRepository.saveAll(List.of(u6, u7, u8, u9, u10));

            Etudiant e1 = new Etudiant();
            e1.setId("6");
            e1.setMatricule("ISM2425/DK-0001");
            e1.setClasse(classes.get(0));
            e1.setUtilisateur(u6);
            e1.onPrePersist();

            Etudiant e2 = new Etudiant();
            e2.setId("7");
            e2.setMatricule("ISM2425/DK-0002");
            e2.setClasse(classes.get(1 % classes.size()));
            e2.setUtilisateur(u7);
            e2.onPrePersist();

            Etudiant e3 = new Etudiant();
            e3.setId("8");
            e3.setMatricule("ISM2425/DK-0003");
            e3.setClasse(classes.get(2 % classes.size()));
            e3.setUtilisateur(u8);
            e3.onPrePersist();

            Etudiant e4 = new Etudiant();
            e4.setId("9");
            e4.setMatricule("ISM2425/DK-0004");
            e4.setClasse(classes.get(0));
            e4.setUtilisateur(u9);
            e4.onPrePersist();

            Etudiant e5 = new Etudiant();
            e5.setId("10");
            e5.setMatricule("ISM2425/DK-0005");
            e5.setClasse(classes.get(1 % classes.size()));
            e5.setUtilisateur(u10);
            e5.onPrePersist();

            etudiantRepository.saveAll(List.of(e1, e2, e3, e4, e5));
        }
    }

}