package smrs.backend_gestion_absence_ism.services.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import smrs.backend_gestion_absence_ism.data.repositories.EtudiantRepository;
import smrs.backend_gestion_absence_ism.mobile.dto.response.EtudiantMobileDto;
import smrs.backend_gestion_absence_ism.mobile.mapper.EtudiantMobileMapper;
import smrs.backend_gestion_absence_ism.services.EtudiantService;
import smrs.backend_gestion_absence_ism.utils.exceptions.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class EtudiantServiceImpl implements EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final EtudiantMobileMapper etudiantMobileMapper;

    @Override
    public EtudiantMobileDto getEtudiantByMatricule(String matricule) {
        var etudiant = etudiantRepository.findByMatricule(matricule)
                .orElseThrow(() -> new EntityNotFoundException("Étudiant non trouvé"));

        return etudiantMobileMapper.toEtudiantMobileDto(etudiant);

    }

}
