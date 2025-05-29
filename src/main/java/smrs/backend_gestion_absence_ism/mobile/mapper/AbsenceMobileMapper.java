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
    AbsenceMobileDto toMobileDto(Absence absence);

}
