package smrs.backend_gestion_absence_ism.services.impl;

import smrs.backend_gestion_absence_ism.data.entities.Absence;
import smrs.backend_gestion_absence_ism.data.entities.Admin;
import smrs.backend_gestion_absence_ism.data.entities.Justification;
import smrs.backend_gestion_absence_ism.data.enums.StatutJustification;
import smrs.backend_gestion_absence_ism.data.repositories.AbsenceRepository;
import smrs.backend_gestion_absence_ism.data.repositories.AdminRepository;
import smrs.backend_gestion_absence_ism.data.repositories.JustificationRepository;
import smrs.backend_gestion_absence_ism.mobile.dto.request.JustificationRequestDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.JustificationMobileDto;
import smrs.backend_gestion_absence_ism.mobile.mapper.JustificationMobileMapper;
import smrs.backend_gestion_absence_ism.services.JustificationService;
import smrs.backend_gestion_absence_ism.utils.exceptions.EntityNotFoundException;
import smrs.backend_gestion_absence_ism.web.dto.JustificationTraitementRequest;
import smrs.backend_gestion_absence_ism.web.dto.JustificationWebDto;
import smrs.backend_gestion_absence_ism.web.mapper.JustificationWebMapper;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JustificationServiceImpl implements JustificationService {

    private final JustificationRepository justificationRepository;
    private final AbsenceRepository absenceRepository;
    private final JustificationMobileMapper justificationMobileMapper;
    private final JustificationWebMapper justificationWebMapper;
    private final AdminRepository adminRepository;

    @Override
    public List<Justification> getAllJustifications() {
        return justificationRepository.findAll();
    }

    @Override
    public List<Justification> getJustificationsEnAttente() {
        return justificationRepository.findByStatut(StatutJustification.EN_ATTENTE);
    }

    @Override
    public JustificationMobileDto ajouterJustification(JustificationRequestDto request) {

        Absence absence = absenceRepository.findById(request.getAbsenceId())
                .orElseThrow(() -> new EntityNotFoundException("Absence non trouvée"));

        if (absence.getJustification() != null) {
            throw new IllegalStateException("Cette absence est déjà justifiée");
        }

        Justification justification = new Justification();
        justification.setDescription(request.getDescription());
        justification.setPiecesJointes(request.getPieceJointes());
        justification.setAbsence(absence);
        justification.setStatut(StatutJustification.EN_ATTENTE);

        Justification saved = justificationRepository.save(justification);

        absenceRepository.save(absence);

        return justificationMobileMapper.toDto(saved);
    }

    @Override
    public JustificationMobileDto getJustificationById(String justificationId) {
        var justification = justificationRepository.findById(justificationId)
                .orElseThrow(() -> new EntityNotFoundException("justification non trouvée"));
        return justificationMobileMapper.toDto(justification);
    }

    @Override
    public JustificationWebDto traiterJustification(JustificationTraitementRequest request) {
        Justification justification = justificationRepository.findById(request.getJustificationId())
                .orElseThrow(() -> new EntityNotFoundException("justification avec cet id n'existe pas"));
        Admin adminValidateur = adminRepository.findById(request.getAdminValidateurId())
                .orElseThrow(() -> new EntityNotFoundException("Admin avec cet id n'existe pas"));

        justification.setDateValidation(LocalDateTime.now());
        justification.setStatut(request.getStatut());
        justification.setAdminValidateur(adminValidateur);
        justification.onPreUpdate();

        justificationRepository.save(justification);

        return justificationWebMapper.toJustificationWebDto(justification);
    }

}