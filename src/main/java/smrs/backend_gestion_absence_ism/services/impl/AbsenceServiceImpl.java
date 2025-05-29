package smrs.backend_gestion_absence_ism.services.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import smrs.backend_gestion_absence_ism.data.entities.Absence;
import smrs.backend_gestion_absence_ism.data.entities.Etudiant;
import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;
import smrs.backend_gestion_absence_ism.data.repositories.AbsenceRepository;
import smrs.backend_gestion_absence_ism.data.repositories.EtudiantRepository;
import smrs.backend_gestion_absence_ism.mobile.dto.response.AbsenceMobileDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.EtudiantAccueilMobileDto;
import smrs.backend_gestion_absence_ism.mobile.mapper.AbsenceMobileMapper;
import smrs.backend_gestion_absence_ism.services.AbsenceService;
import smrs.backend_gestion_absence_ism.utils.exceptions.EntityNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AbsenceServiceImpl implements AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final EtudiantRepository etudiantRepository;
    private final AbsenceMobileMapper absenceMobileMapper;

    @Override
    public List<Absence> getAllAbsences() {
        return absenceRepository.findAll();
    }

    @Override
    public List<Absence> getAbsencesByEtudiant(String etudiantId) {
        if (etudiantId == null)
            return List.of();
        return absenceRepository.findByEtudiantId(etudiantId);
    }

    @Override
    public List<Absence> getAbsencesByEtudiantMatricule(String matricule) {
        return List.of();
    }

    @Override
    public List<Absence> getAbsencesByDate(LocalDateTime date) {
        return absenceRepository.findByCreatedAt(date);
    }

    @Override
    public List<Absence> getAbsencesByCours(String coursId) {
        return List.of();
    }

    /**
     * on envoie les infos de l'etudiant avec les 5 derniers absences
     */
    @Override
    public EtudiantAccueilMobileDto getAccueilEtudiant(String etudiantId) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new EntityNotFoundException("Étudiant non trouvé"));

        EtudiantAccueilMobileDto dto = new EtudiantAccueilMobileDto();
        dto.setId(etudiant.getId());
        dto.setNom(etudiant.getUtilisateur().getNom());
        dto.setPrenom(etudiant.getUtilisateur().getPrenom());
        dto.setMatricule(etudiant.getMatricule());
        dto.setClasse(etudiant.getClasse().getNom());

        LocalDateTime ilYADepuis30Jours = LocalDateTime.now().minusDays(30);
        List<Absence> absencesRecentes = absenceRepository.findByEtudiantIdAndCreatedAtBetween(
                etudiantId, ilYADepuis30Jours, LocalDateTime.now());

        List<AbsenceMobileDto> absencesDto = absencesRecentes.stream()
                .sorted(Comparator.comparing(Absence::getCreatedAt).reversed())
                .limit(5)
                .map(absenceMobileMapper::toMobileDto)
                .toList();

        dto.setAbsences(absencesDto);

        return dto;
    }

    /**
     * on récupére les absences d'un étudiant avec posibilité de filtrer par date
     * debut et fin
     */
    @Override
    public List<AbsenceMobileDto> getAbsencesEtudiant(String etudiantId, TypeAbsence type, LocalDate dateDebut,
            LocalDate dateFin) {

        List<Absence> absences;

        if (type != null) {
            absences = absenceRepository.findByEtudiantIdAndType(etudiantId, type);
        } else {
            absences = absenceRepository.findByEtudiantId(etudiantId);
        }

        if (dateDebut != null && dateFin != null) {
            LocalDateTime startDateTime = dateDebut.atStartOfDay();
            LocalDateTime endDateTime = dateFin.atTime(23, 59, 59);

            absences = absences.stream()
                    .filter(a -> a.getHeurePointage().isAfter(startDateTime) &&
                            a.getHeurePointage().isBefore(endDateTime))
                    .toList();
        }

        return absences.stream()
                .map(absenceMobileMapper::toMobileDto)
                .sorted((a, b) -> b.getHeurePointage().compareTo(a.getHeurePointage()))
                .toList();
    }

}
