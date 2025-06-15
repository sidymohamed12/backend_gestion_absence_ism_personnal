package smrs.backend_gestion_absence_ism.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import smrs.backend_gestion_absence_ism.data.repositories.EtudiantRepository;
import smrs.backend_gestion_absence_ism.mobile.dto.response.EtudiantMobileDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.UtilisateurMobileDto;
import smrs.backend_gestion_absence_ism.mobile.mapper.AuthentificationMapper;
import smrs.backend_gestion_absence_ism.mobile.mapper.EtudiantMobileMapper;
import smrs.backend_gestion_absence_ism.services.EtudiantService;
import smrs.backend_gestion_absence_ism.utils.exceptions.EntityNotFoundException;
import smrs.backend_gestion_absence_ism.web.dto.EtudiantListDto;
import smrs.backend_gestion_absence_ism.web.mapper.EtudiantWebMapper;

@Service
@RequiredArgsConstructor
public class EtudiantServiceImpl implements EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final EtudiantMobileMapper etudiantMobileMapper;
    private final AuthentificationMapper authentificationMapper;

    /**
     * Récupère un étudiant par son matricule.
     *
     * @param matricule Le matricule de l'étudiant à récupérer.
     * @return Un EtudiantMobileDto contenant les informations de l'étudiant.
     * @throws EntityNotFoundException si l'étudiant n'est pas trouvé.
     */
    @Override
    public EtudiantMobileDto getEtudiantByMatricule(String matricule) {
        var etudiant = etudiantRepository.findByMatricule(matricule)
                .orElseThrow(() -> new EntityNotFoundException("Étudiant non trouvé"));

        return etudiantMobileMapper.toEtudiantMobileDto(etudiant);

    }

    /**
     * Récupére la liste des étudiants
     */
    @Override
    public List<UtilisateurMobileDto> listerEtudiantsAnneeActive() {
        return etudiantRepository.findAll().stream()
                .map(authentificationMapper::etudiantToUtilisateurMobileDto)
                .toList();
    }

    /**
     * Récupère un étudiant par son ID.
     *
     * @param id L'ID de la classe.
     * @return Une liste d'EtudiantMobileDto contenant les informations de
     *         l'étudiant.
     */
    @Override
    public List<EtudiantListDto> getAllEtudiantsByClasseId(String classeId) {
        if (classeId != null && !classeId.isEmpty()) {
            return etudiantRepository.findByClasseId(classeId).stream()
                    .map(EtudiantWebMapper.INSTANCE::toDto)
                    .toList();
        }
        return List.of();
    }
}
