package smrs.backend_gestion_absence_ism.services.impl;

import smrs.backend_gestion_absence_ism.data.entities.Absence;
import smrs.backend_gestion_absence_ism.data.entities.Justification;
import smrs.backend_gestion_absence_ism.data.enums.StatutJustification;
import smrs.backend_gestion_absence_ism.data.repositories.AbsenceRepository;
import smrs.backend_gestion_absence_ism.data.repositories.JustificationRepository;
import smrs.backend_gestion_absence_ism.mobile.dto.request.JustificationRequestDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.JustificationMobileDto;
import smrs.backend_gestion_absence_ism.mobile.mapper.JustificationMobileMapper;
import smrs.backend_gestion_absence_ism.services.JustificationService;
import smrs.backend_gestion_absence_ism.utils.exceptions.EntityNotFoundException;
import smrs.backend_gestion_absence_ism.utils.exceptions.FileStorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JustificationServiceImpl implements JustificationService {

    private final JustificationRepository justificationRepository;
    private final AbsenceRepository absenceRepository;
    private final JustificationMobileMapper justificationMobileMapper;
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public List<Justification> getAllJustifications() {
        return justificationRepository.findAll();
    }

    @Override
    public List<Justification> getJustificationsEnAttente() {
        return justificationRepository.findByStatut(StatutJustification.EN_ATTENTE);
    }

    @Override
    public JustificationMobileDto ajouterJustification(JustificationRequestDto request, String matricule) {

        Absence absence = absenceRepository.findById(request.getAbsenceId())
                .orElseThrow(() -> new EntityNotFoundException("Absence non trouvée"));

        if (absence.getJustification() != null) {
            throw new IllegalStateException("Cette absence est déjà justifiée");
        }

        String documentPath = storeFile(request.getPieceJointe());

        Justification justification = new Justification();
        justification.setDescription(request.getDescription());
        justification.setDocumentPath(documentPath);
        justification.setAbsence(absence);
        justification.setStatut(StatutJustification.EN_ATTENTE);

        Justification saved = justificationRepository.save(justification);

        absenceRepository.save(absence);

        return justificationMobileMapper.toDto(saved);
    }

    public String storeFile(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path targetLocation = uploadPath.resolve(fileName);
            file.transferTo(targetLocation);

            return "/uploads/" + fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Échec du stockage du fichier", ex);
        }
    }

}
