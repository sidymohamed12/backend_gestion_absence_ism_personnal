package smrs.backend_gestion_absence_ism.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import smrs.backend_gestion_absence_ism.data.entities.Cours;
import smrs.backend_gestion_absence_ism.data.repositories.ClasseRepository;
import smrs.backend_gestion_absence_ism.data.repositories.CoursRepository;
import smrs.backend_gestion_absence_ism.services.CoursService;

@Service
@AllArgsConstructor
public class CoursServiceImpl implements CoursService {

    private final CoursRepository coursRepository;
    private final ClasseRepository classeRepository;

    @Override
    public List<Cours> getCoursByClasse(String classeId) {
        try {
            // Vérifier si la classe existe
            if (!classeRepository.existsById(classeId)) {
                throw new IllegalArgumentException("Classe avec l'ID " + classeId + " n'existe pas.");
            }
            // Récupérer les cours associés à la classe
            return coursRepository.findByClasseId(classeId);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des cours pour la classe : ", e);
        }
    }

}
