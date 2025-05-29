package smrs.backend_gestion_absence_ism.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import smrs.backend_gestion_absence_ism.data.entities.Justification;
import smrs.backend_gestion_absence_ism.web.dto.JustificationWebDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JustificationWebMapper {
    JustificationWebMapper INSTANCE = Mappers.getMapper(JustificationWebMapper.class);

    @Mapping(source = "absence.id", target = "absenceId")
    @Mapping(source = "absence.etudiant.matricule", target = "etudiantMatricule")
    @Mapping(source = "absence.createdAt", target = "dateAbsence")
    JustificationWebDto toDto(Justification justification);

    List<JustificationWebDto> toDtoList(List<Justification> justifications);
}
