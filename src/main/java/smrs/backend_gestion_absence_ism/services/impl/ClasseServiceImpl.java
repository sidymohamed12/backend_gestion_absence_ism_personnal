package smrs.backend_gestion_absence_ism.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import smrs.backend_gestion_absence_ism.data.repositories.ClasseRepository;
import smrs.backend_gestion_absence_ism.data.repositories.EtudiantRepository;
import smrs.backend_gestion_absence_ism.data.repositories.AnneeScolaireRepository;
import smrs.backend_gestion_absence_ism.data.repositories.InscriptionRepository;
import smrs.backend_gestion_absence_ism.services.ClasseService;
import smrs.backend_gestion_absence_ism.web.dto.ClasseWebDto;
import smrs.backend_gestion_absence_ism.web.mapper.ClasseWebMapper;
import smrs.backend_gestion_absence_ism.data.entities.Classe;
import smrs.backend_gestion_absence_ism.data.entities.AnneeScolaire;
import smrs.backend_gestion_absence_ism.data.entities.Inscription;
import smrs.backend_gestion_absence_ism.data.enums.Filiere;

@Service
@AllArgsConstructor
public class ClasseServiceImpl implements ClasseService {
    private final ClasseRepository classeRepository;
    private final AnneeScolaireRepository anneeScolaireRepository;
    private final InscriptionRepository inscriptionRepository;
    private final EtudiantRepository etudiantRepository;

    @Override
    public List<Classe> getAllClasses(String searchFiliere, String searchNiveau) {
        try {
            // Si les deux paramètres sont fournis
            if (searchFiliere != null && !searchFiliere.isEmpty() && searchNiveau != null && !searchNiveau.isEmpty()) {
                try {
                    Filiere filiere = Filiere.valueOf(searchFiliere.toUpperCase());
                    return classeRepository.findByFiliereAndNiveau(filiere, searchNiveau);
                } catch (IllegalArgumentException e) {
                    // Si la filière n'est pas valide, on filtre juste par niveau
                    return classeRepository.findByNiveau(searchNiveau);
                }
            }
            // Si seulement la filière est fournie
            else if (searchFiliere != null && !searchFiliere.isEmpty()) {
                try {
                    Filiere filiere = Filiere.valueOf(searchFiliere.toUpperCase());
                    return classeRepository.findByFiliere(filiere);
                } catch (IllegalArgumentException e) {
                    // Si la filière n'est pas valide, on retourne toutes les classes
                    return classeRepository.findAll();
                }
            }
            // Si seulement le niveau est fourni
            else if (searchNiveau != null && !searchNiveau.isEmpty()) {
                return classeRepository.findByNiveau(searchNiveau);
            }
            // Si aucun paramètre n'est fourni
            else {
                return classeRepository.findAll();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des classes : ", e);
        }
    }

    @Override
    public List<ClasseWebDto> getAllClasses() {
        try {
            var classes = classeRepository.findAll();
            var response = ClasseWebMapper.INSTANCE.toDtoList(classes);
            response.forEach(c -> c.setEffectif(etudiantRepository.countEtudiantByClasseId(c.getId())));
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de toutes les classes : ", e);
        }
    }

    @Override
    public List<Classe> getClassesByFiliere(Filiere filiere) {
        try {
            return classeRepository.findByFiliere(filiere);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des classes par filière : ", e);
        }
    }

    @Override
    public List<Classe> getClassesByNiveau(String niveau) {
        try {
            return classeRepository.findByNiveau(niveau);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des classes par niveau : ", e);
        }
    }

    @Override
    public List<Classe> getClassesByFiliereAndNiveau(Filiere filiere, String niveau) {
        try {
            return classeRepository.findByFiliereAndNiveau(filiere, niveau);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des classes par filière et niveau : ", e);
        }
    }

    @Override
    public List<Classe> getClassesForActiveAcademicYear() {
        try {
            // Récupérer l'année scolaire active
            AnneeScolaire activeYear = anneeScolaireRepository.findByActive(true);
            if (activeYear == null) {
                throw new RuntimeException("Aucune année scolaire active n'est configurée");
            }

            // Récupérer les inscriptions valides pour l'année active
            List<Inscription> inscriptions = inscriptionRepository.findByAnneeScolaireAndValide(activeYear, true);

            // Extraire les classes des étudiants inscrits pour l'année active
            Set<Classe> classes = new HashSet<>();
            for (Inscription inscription : inscriptions) {
                if (inscription.getEtudiant() != null && inscription.getEtudiant().getClasse() != null) {
                    classes.add(inscription.getEtudiant().getClasse());
                }
            }

            return new ArrayList<>(classes);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des classes pour l'année scolaire active : ", e);
        }
    }
}
