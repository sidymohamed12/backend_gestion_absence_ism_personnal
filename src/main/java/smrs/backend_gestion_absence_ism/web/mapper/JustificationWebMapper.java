package smrs.backend_gestion_absence_ism.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import smrs.backend_gestion_absence_ism.data.entities.Justification;
import smrs.backend_gestion_absence_ism.web.dto.JustificationDto;
import smrs.backend_gestion_absence_ism.web.dto.JustificationForAbsenceDetail;
import smrs.backend_gestion_absence_ism.web.dto.JustificationWebDto;

@Mapper(componentModel = "spring")
public interface JustificationWebMapper {
    JustificationWebMapper INSTANCE = Mappers.getMapper(JustificationWebMapper.class);

    JustificationDto toJustificationDto(Justification justification);

    @Mapping(target = "absenceId", source = "absence.id")
    @Mapping(target = "etudiantNom", source = "absence.etudiant.utilisateur.prenom")
    @Mapping(target = "etudiantPrenom", source = "absence.etudiant.utilisateur.nom")
    @Mapping(target = "etudiantMatricule", source = "absence.etudiant.utilisateur.prenom")
    JustificationWebDto toJustificationWebDto(Justification justification);

    @Mapping(target = "nomValidateur", source = "adminValidateur.utilisateur.nom")
    @Mapping(target = "prenomValidateur", source = "adminValidateur.utilisateur.prenom")
    JustificationForAbsenceDetail toJustificationForAbsenceDetail(Justification justification);

}
