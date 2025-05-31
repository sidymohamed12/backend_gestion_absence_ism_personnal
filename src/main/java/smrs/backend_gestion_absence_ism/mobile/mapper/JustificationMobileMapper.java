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

    @Mapping(target = "adminValidateur", ignore = true)
    Justification toEntity(JustificationRequestDto dto);
}
