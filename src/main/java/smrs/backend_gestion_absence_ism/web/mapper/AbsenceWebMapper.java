package smrs.backend_gestion_absence_ism.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import smrs.backend_gestion_absence_ism.data.entities.Absence;
import smrs.backend_gestion_absence_ism.web.dto.AbsenceWebDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AbsenceWebMapper {
    AbsenceWebMapper INSTANCE = Mappers.getMapper(AbsenceWebMapper.class);

    @Mapping(source = "cours.nom", target = "nomCours")
    @Mapping(target = "etudiantNom", source = "etudiant.utilisateur.nom")
    @Mapping(target = "etudiantPrenom", source = "etudiant.utilisateur.prenom")
    @Mapping(target = "etudiantMatricule", source = "etudiant.matricule")
    @Mapping(target = "statutJustification", source = "justification.statut")
    @Mapping(target = "salle", source = "cours.salle")
    @Mapping(target = "coursJour", source = "cours.jour")
    @Mapping(target = "heureDebut", source = "cours.heureDebut")
    AbsenceWebDto toDto(Absence absence);

    List<AbsenceWebDto> toDtoList(List<Absence> absences);
}
