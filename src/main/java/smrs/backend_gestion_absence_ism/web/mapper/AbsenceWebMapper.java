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

    // @Mapping(source = "etudiant.matricule", target = "etudiantMatricule")
    // @Mapping(source = "cours.nom", target = "coursNom")
    @Mapping(expression = "java(absence.getJustification() != null)", target = "AJustification")
    AbsenceWebDto toDto(Absence absence);

    List<AbsenceWebDto> toDtoList(List<Absence> absences);
}
