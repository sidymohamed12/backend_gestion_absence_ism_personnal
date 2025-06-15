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
import smrs.backend_gestion_absence_ism.utils.exceptions.EntityNotFoundException;
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

    /**
     * Récupére toutes les classes en fonction des paramétres de filtrage
     * 
     * @param searchFiliere La filière à filtrer (peut être null ou vide)
     * @param searchNiveau  Le niveau à filtrer (peut être null ou vide)
     * @return Une liste de classes correspondant aux critères de filtrage
     */
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

    /**
     * Récupère toutes les classes sans filtrage
     * 
     * @return Une liste de toutes les classes
     */
    @Override
    public List<ClasseWebDto> getAllClasses() {
        try {
            var classes = classeRepository.findAll();
            var response = ClasseWebMapper.INSTANCE.toDtoList(classes);
            response.forEach(c -> c.setEffectif(etudiantRepository.countEtudiantByClasseId(c.getId())));
            return response;
        } catch (Exception e) {
            throw new EntityNotFoundException(
                    "Erreur lors de la récupération de toutes les classes : " + e.getMessage());
        }
    }

    /**
     * Récupére les classes par niveau
     * 
     * @param filiere La filiere de la classe à récupérer
     * @return Une liste de classes correspondant à la filiere spécifié
     */
    @Override
    public List<Classe> getClassesByFiliere(Filiere filiere) {
        try {
            return classeRepository.findByFiliere(filiere);
        } catch (Exception e) {
            throw new EntityNotFoundException("Erreur lors de la récupération des classes par filière : " + e);
        }
    }

    /**
     * Récupére les classes par niveau
     * 
     * @param niveau Le niveau de la classe à récupérer
     * @return Une liste de classes correspondant au niveau spécifié
     */
    @Override
    public List<Classe> getClassesByNiveau(String niveau) {
        try {
            return classeRepository.findByNiveau(niveau);
        } catch (Exception e) {
            throw new EntityNotFoundException("Erreur lors de la récupération des classes par niveau : " + e);
        }
    }

    /**
     * Récupère les classes par filière et niveau
     * 
     * @param filiere La filière de la classe à récupérer
     * @param niveau  Le niveau de la classe à récupérer
     * @return Une liste de classes correspondant à la filière et au niveau
     *         spécifié
     */
    @Override
    public List<Classe> getClassesByFiliereAndNiveau(Filiere filiere, String niveau) {
        try {
            return classeRepository.findByFiliereAndNiveau(filiere, niveau);
        } catch (Exception e) {
            throw new EntityNotFoundException(
                    "Erreur lors de la récupération des classes par filière et niveau : " + e);
        }
    }

    /**
     * Récupère les classes pour l'année scolaire active
     * 
     * @return Une liste de classes pour l'année scolaire active
     */
    @Override
    public List<Classe> getClassesForActiveAcademicYear() {
        try {
            AnneeScolaire activeYear = anneeScolaireRepository.findByActive(true);
            if (activeYear == null) {
                throw new EntityNotFoundException("Aucune année scolaire active n'est configurée");
            }

            List<Inscription> inscriptions = inscriptionRepository.findByAnneeScolaireAndValide(activeYear, true);
            if (inscriptions.isEmpty()) {
                throw new EntityNotFoundException("Aucune inscription valide trouvée pour l'année scolaire active");
            }

            return getClassesFromInscriptionActiveYear(inscriptions);

        } catch (Exception e) {
            throw new EntityNotFoundException(
                    "Erreur lors de la récupération des classes pour l'année scolaire active : " + e);
        }
    }

    /**
     * Récupère les classes à partir des inscriptions pour l'année scolaire active
     * 
     * @param inscriptions La liste des inscriptions pour l'année scolaire active
     * @return Une liste de classes uniques associées aux inscriptions
     */
    private List<Classe> getClassesFromInscriptionActiveYear(List<Inscription> inscriptions) {
        Set<Classe> classes = new HashSet<>();
        for (Inscription inscription : inscriptions) {
            if (inscription.getEtudiant() != null && inscription.getEtudiant().getClasse() != null) {
                classes.add(inscription.getEtudiant().getClasse());
            }
        }
        return new ArrayList<>(classes);
    }

    /**
     * Récupère une classe par son ID
     * 
     * @param classeId L'ID de la classe à récupérer
     * @return La classe correspondante
     */
    @Override
    public ClasseWebDto getClasseById(String classeId) {
        var classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new RuntimeException("Classe non trouvée avec l'ID : " + classeId));
        return ClasseWebMapper.INSTANCE.toDto(classe);
    }
}
