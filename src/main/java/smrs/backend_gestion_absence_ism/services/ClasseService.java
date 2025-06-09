package smrs.backend_gestion_absence_ism.services;

import java.util.List;

import smrs.backend_gestion_absence_ism.data.entities.Classe;
import smrs.backend_gestion_absence_ism.data.enums.Filiere;
import smrs.backend_gestion_absence_ism.web.dto.ClasseWebDto;

public interface ClasseService {
    List<ClasseWebDto> getAllClasses();

    List<Classe> getClassesByNiveau(String niveau);

    List<Classe> getClassesByFiliere(Filiere filiere);

    List<Classe> getAllClasses(String searchFiliere, String searchNiveau);

    List<Classe> getClassesByFiliereAndNiveau(Filiere filiere, String niveau);

    /**
     * Récupère les classes avec des étudiants inscrits pour l'année scolaire active
     * 
     * @return Liste des classes ayant des étudiants inscrits pour l'année scolaire
     *         active
     */
    List<Classe> getClassesForActiveAcademicYear();

}
