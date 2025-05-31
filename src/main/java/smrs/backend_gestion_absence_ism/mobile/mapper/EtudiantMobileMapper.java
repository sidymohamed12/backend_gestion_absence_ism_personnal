package smrs.backend_gestion_absence_ism.mobile.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import smrs.backend_gestion_absence_ism.data.entities.Etudiant;
import smrs.backend_gestion_absence_ism.mobile.dto.response.EtudiantMobileDto;

@Mapper(componentModel = "spring")
public interface EtudiantMobileMapper {
    EtudiantMobileMapper INSTANCE = Mappers.getMapper(EtudiantMobileMapper.class);

    @Mapping(source = "utilisateur.prenom", target = "prenom")
    @Mapping(source = "utilisateur.nom", target = "nom")
    @Mapping(source = "classe.nom", target = "classe")
    EtudiantMobileDto toEtudiantMobileDto(Etudiant etudiant);

}
