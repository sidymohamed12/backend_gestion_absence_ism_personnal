// package smrs.backend_gestion_absence_ism.mobile.mapper;

// import org.mapstruct.Mapper;
// import org.mapstruct.Mapping;
// import org.mapstruct.factory.Mappers;

// import smrs.backend_gestion_absence_ism.data.entities.Admin;
// import smrs.backend_gestion_absence_ism.data.entities.Cours;
// import smrs.backend_gestion_absence_ism.data.entities.Etudiant;
// import smrs.backend_gestion_absence_ism.data.entities.Justification;
// import smrs.backend_gestion_absence_ism.data.entities.Utilisateur;
// import smrs.backend_gestion_absence_ism.data.entities.Vigile;
// import smrs.backend_gestion_absence_ism.mobile.dto.response.AbsenceMobileDto;
// import smrs.backend_gestion_absence_ism.mobile.dto.response.CoursMobileDto;
// import
// smrs.backend_gestion_absence_ism.mobile.dto.response.UtilisateurMobileDto;

// @Mapper(componentModel = "spring", uses = { AbsenceMobileDto.class })
// public interface AuthentificationMapper {

// AuthentificationMapper INSTANCE =
// Mappers.getMapper(AuthentificationMapper.class);

// @Mapping(source = "utilisateur.id", target = "id")
// @Mapping(source = "utilisateur.nom", target = "nom")
// @Mapping(source = "utilisateur.prenom", target = "prenom")
// @Mapping(source = "utilisateur.email", target = "email")
// @Mapping(source = "utilisateur.role", target = "role")
// @Mapping(source = "matricule", target = "matricule")
// @Mapping(source = "classe.nom", target = "classe")
// @Mapping(target = "departement", ignore = true)
// @Mapping(target = "badge", ignore = true)
// UtilisateurMobileDto etudiantToUtilisateurMobileDto(Etudiant etudiant);

// @Mapping(source = "utilisateur.id", target = "id")
// @Mapping(source = "utilisateur.nom", target = "nom")
// @Mapping(source = "utilisateur.prenom", target = "prenom")
// @Mapping(source = "utilisateur.email", target = "email")
// @Mapping(source = "utilisateur.role", target = "role")
// @Mapping(source = "departement", target = "departement")
// @Mapping(target = "matricule", ignore = true)
// @Mapping(target = "badge", ignore = true)
// @Mapping(target = "absences", ignore = true)
// @Mapping(target = "classe", ignore = true)
// UtilisateurMobileDto adminToUtilisateurMobileDto(Admin admin);

// @Mapping(source = "utilisateur.id", target = "id")
// @Mapping(source = "utilisateur.nom", target = "nom")
// @Mapping(source = "utilisateur.prenom", target = "prenom")
// @Mapping(source = "utilisateur.email", target = "email")
// @Mapping(source = "utilisateur.role", target = "role")
// @Mapping(source = "badge", target = "badge")
// @Mapping(target = "matricule", ignore = true)
// @Mapping(target = "departement", ignore = true)
// @Mapping(target = "absences", ignore = true)
// @Mapping(target = "classe", ignore = true)
// UtilisateurMobileDto vigileToUtilisateurMobileDto(Vigile vigile);

// @Mapping(source = "id", target = "id")
// @Mapping(source = "nom", target = "nom")
// @Mapping(source = "prenom", target = "prenom")
// @Mapping(source = "email", target = "email")
// @Mapping(source = "role", target = "role")
// @Mapping(target = "matricule", ignore = true)
// @Mapping(target = "departement", ignore = true)
// @Mapping(target = "badge", ignore = true)
// @Mapping(target = "absences", ignore = true)
// @Mapping(target = "classe", ignore = true)
// UtilisateurMobileDto utilisateurToUtilisateurMobileDto(Utilisateur
// utilisateur);

// default boolean map(Justification justification) {
// return justification != null;
// }

// @Mapping(source = "classe.nom", target = "classeNom")
// @Mapping(source = "matiere.nom", target = "matiereNom")
// CoursMobileDto coursToCoursMobileDto(Cours cours);
// }
