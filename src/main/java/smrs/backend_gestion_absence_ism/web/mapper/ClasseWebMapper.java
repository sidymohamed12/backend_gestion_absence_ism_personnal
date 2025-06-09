package smrs.backend_gestion_absence_ism.web.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import smrs.backend_gestion_absence_ism.data.entities.Classe;
import smrs.backend_gestion_absence_ism.web.dto.ClasseWebDto;

@Mapper(componentModel = "spring")
public interface ClasseWebMapper {
    ClasseWebMapper INSTANCE = Mappers.getMapper(ClasseWebMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "nom", target = "nom")
    @Mapping(source = "niveau", target = "niveau")
    @Mapping(source = "filiere", target = "filiere")
    ClasseWebDto toDto(Classe classe);

    List<ClasseWebDto> toDtoList(List<Classe> classes);
}
