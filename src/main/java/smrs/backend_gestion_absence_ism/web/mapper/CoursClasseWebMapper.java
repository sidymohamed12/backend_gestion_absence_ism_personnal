package smrs.backend_gestion_absence_ism.web.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import smrs.backend_gestion_absence_ism.data.entities.Cours;
import smrs.backend_gestion_absence_ism.web.dto.CoursClasseWebDto;

@Mapper(componentModel = "spring")
public interface CoursClasseWebMapper {
    CoursClasseWebMapper INSTANCE = Mappers.getMapper(CoursClasseWebMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nom", target = "nom")
    @Mapping(source = "enseignant", target = "enseignant")
    @Mapping(source = "salle", target = "salle")
    @Mapping(source = "matiere.nom", target = "nomMatiere")
    CoursClasseWebDto toDto(Cours cours);

    List<CoursClasseWebDto> toDtoList(List<Cours> coursList);
}
