package smrs.backend_gestion_absence_ism.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import smrs.backend_gestion_absence_ism.data.entities.Etudiant;
import smrs.backend_gestion_absence_ism.web.dto.EtudiantListDto;

@Mapper(componentModel = "spring")
public interface EtudiantWebMapper {
    EtudiantWebMapper INSTANCE = Mappers.getMapper(EtudiantWebMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "utilisateur.nom", target = "nom")
    @Mapping(source = "utilisateur.prenom", target = "prenom")
    @Mapping(source = "utilisateur.email", target = "email")
    @Mapping(source = "utilisateur.id", target = "idUser")
    EtudiantListDto toDto(Etudiant etudiant);
}