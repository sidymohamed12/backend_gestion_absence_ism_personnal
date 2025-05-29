package smrs.backend_gestion_absence_ism.mobile.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import smrs.backend_gestion_absence_ism.data.entities.Cours;
import smrs.backend_gestion_absence_ism.mobile.dto.response.CoursMobileDto;

@Mapper(componentModel = "spring", uses = { AbsenceMobileMapper.class })
public interface CoursMobileMapper {

    CoursMobileMapper INSTANCE = Mappers.getMapper(CoursMobileMapper.class);

    @Mapping(source = "matiere.nom", target = "matiereNom")
    @Mapping(source = "classe.nom", target = "classeNom")
    CoursMobileDto toMobileDto(Cours cours);

}
