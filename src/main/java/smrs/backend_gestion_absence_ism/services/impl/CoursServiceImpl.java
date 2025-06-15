package smrs.backend_gestion_absence_ism.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import smrs.backend_gestion_absence_ism.data.entities.Cours;
import smrs.backend_gestion_absence_ism.data.repositories.ClasseRepository;
import smrs.backend_gestion_absence_ism.data.repositories.CoursRepository;
import smrs.backend_gestion_absence_ism.services.CoursService;
import smrs.backend_gestion_absence_ism.utils.exceptions.EntityNotFoundException;

@Service
@AllArgsConstructor
public class CoursServiceImpl implements CoursService {

    private final CoursRepository coursRepository;
    private final ClasseRepository classeRepository;

    /**
     * Récupère tous les cours associés à une classe spécifique.
     *
     * @param classeId L'ID de la classe pour laquelle récupérer les cours.
     * @return Une liste de cours associés à la classe.
     */
    @Override
    public List<Cours> getCoursByClasse(String classeId) {

        if (!classeRepository.existsById(classeId)) {
            throw new EntityNotFoundException("Classe avec l'ID " + classeId + " n'existe pas.");
        }

        return coursRepository.findByClasseId(classeId);

    }

}
