package smrs.backend_gestion_absence_ism.mobile.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import smrs.backend_gestion_absence_ism.data.entities.Absence;
import smrs.backend_gestion_absence_ism.mobile.dto.response.AbsenceMobileDto;

@Mapper(componentModel = "spring", uses = { CoursMobileMapper.class })
public interface AbsenceMobileMapper {

    AbsenceMobileMapper INSTANCE = Mappers.getMapper(AbsenceMobileMapper.class);

    @Mapping(expression = "java(absence.getJustification() != null)", target = "justification")
    @Mapping(target = "justificationId", source = "absence.justification.id")
    @Mapping(target = "descriptionJustification", source = "absence.justification.description")
    @Mapping(target = "piecesJointes", source = "absence.justification.piecesJointes")
    @Mapping(target = "statutJustification", source = "absence.justification.statut")
    @Mapping(target = "dateValidationJustification", source = "absence.justification.dateValidation")
    @Mapping(target = "nomCours", source = "absence.cours.nom")
    @Mapping(target = "professeur", source = "absence.cours.enseignant")
    @Mapping(target = "salle", source = "absence.cours.salle")
    @Mapping(target = "heureDebut", source = "absence.cours.heureDebut")
    AbsenceMobileDto toMobileDto(Absence absence);

}
