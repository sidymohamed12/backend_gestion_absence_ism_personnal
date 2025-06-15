package smrs.backend_gestion_absence_ism.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import smrs.backend_gestion_absence_ism.data.entities.Vigile;
import smrs.backend_gestion_absence_ism.data.repositories.VigileRepository;
import smrs.backend_gestion_absence_ism.mobile.dto.response.UtilisateurMobileDto;
import smrs.backend_gestion_absence_ism.mobile.mapper.AuthentificationMapper;
import smrs.backend_gestion_absence_ism.services.VigileService;
import smrs.backend_gestion_absence_ism.utils.exceptions.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class VigileServiceImpl implements VigileService {

    private final VigileRepository vigileRepository;
    private final AuthentificationMapper authentificationMapper;

    /**
     * Récupère les informations d'un vigile par son ID utilisateur
     * 
     * @param vigileId ID de l'utilisateur associé au vigile
     * @return Données du vigile
     * @throws EntityNotFoundException Si le vigile n'est pas trouvé
     */
    @Override
    public UtilisateurMobileDto getVigileById(String vigileId) {
        Vigile vigile = vigileRepository.findByUtilisateurId(vigileId)
                .orElseThrow(() -> new EntityNotFoundException("Vigile non trouvé pour l'utilisateur " + vigileId));
        return authentificationMapper.vigileToUtilisateurMobileDto(vigile);
    }

    /**
     * Récupère la liste de tous les vigiles
     * 
     * @return Liste des vigiles
     */
    @Override
    public List<UtilisateurMobileDto> getAllVigiles() {
        List<Vigile> vigiles = vigileRepository.findAll();
        return vigiles.stream()
                .map(authentificationMapper::vigileToUtilisateurMobileDto)
                .toList();
    }
}
