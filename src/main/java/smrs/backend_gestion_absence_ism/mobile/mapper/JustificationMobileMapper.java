package smrs.backend_gestion_absence_ism.mobile.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import smrs.backend_gestion_absence_ism.data.entities.Justification;
import smrs.backend_gestion_absence_ism.mobile.dto.request.JustificationRequestDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.JustificationMobileDto;

@Mapper(componentModel = "spring")
public interface JustificationMobileMapper {

    JustificationMobileMapper INSTANCE = Mappers.getMapper(JustificationMobileMapper.class);

    @Mapping(source = "absence.id", target = "absenceId")
    @Mapping(source = "absence.cours.nom", target = "coursNom")
    @Mapping(source = "absence.cours.jour", target = "jourCours")
    JustificationMobileDto toDto(Justification justification);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "statut", constant = "EN_ATTENTE")
    @Mapping(target = "piecesJointes", source = "piecesJointes")
    @Mapping(target = "absence", ignore = true)
    @Mapping(target = "adminValidateur", ignore = true)
    @Mapping(target = "dateValidation", ignore = true)
    Justification toEntity(JustificationRequestDto dto);
}
