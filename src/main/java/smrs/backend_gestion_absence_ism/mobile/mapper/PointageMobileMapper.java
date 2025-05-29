package smrs.backend_gestion_absence_ism.mobile.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import smrs.backend_gestion_absence_ism.data.entities.Absence;
import smrs.backend_gestion_absence_ism.mobile.dto.response.HistoriquePointageMobileDto;

@Mapper(componentModel = "spring")
public interface PointageMobileMapper {

    PointageMobileMapper INSTANCE = Mappers.getMapper(PointageMobileMapper.class);

    @Mapping(source = "etudiant.utilisateur.nom", target = "etudiantNom")
    @Mapping(source = "etudiant.matricule", target = "matricule")
    @Mapping(source = "cours.nom", target = "coursNom")
    @Mapping(source = "heurePointage", target = "heureArrivee", dateFormat = "HH:mm")
    @Mapping(source = "heurePointage", target = "date", dateFormat = "yyyy-MM-dd")
    HistoriquePointageMobileDto toDto(Absence absence);
}
