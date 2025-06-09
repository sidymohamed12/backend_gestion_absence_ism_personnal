package smrs.backend_gestion_absence_ism.services;

import java.util.List;

import smrs.backend_gestion_absence_ism.data.entities.Cours;

public interface CoursService {
    List<Cours> getCoursByClasse(String classeId);
}
