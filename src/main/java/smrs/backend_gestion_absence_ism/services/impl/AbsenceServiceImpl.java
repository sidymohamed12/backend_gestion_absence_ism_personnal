package smrs.backend_gestion_absence_ism.services.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import smrs.backend_gestion_absence_ism.data.entities.Absence;
import smrs.backend_gestion_absence_ism.data.entities.AnneeScolaire;
import smrs.backend_gestion_absence_ism.data.entities.Etudiant;
import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;
import smrs.backend_gestion_absence_ism.data.repositories.AbsenceRepository;
import smrs.backend_gestion_absence_ism.data.repositories.AnneeScolaireRepository;
import smrs.backend_gestion_absence_ism.data.repositories.EtudiantRepository;
import smrs.backend_gestion_absence_ism.mobile.dto.response.AbsenceMobileDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.EtudiantAccueilMobileDto;
import smrs.backend_gestion_absence_ism.mobile.mapper.AbsenceMobileMapper;
import smrs.backend_gestion_absence_ism.services.AbsenceService;
import smrs.backend_gestion_absence_ism.utils.exceptions.EntityNotFoundException;
import smrs.backend_gestion_absence_ism.web.dto.AbsenceDetailWithJustificationDto;
import smrs.backend_gestion_absence_ism.web.dto.AbsenceWebDto;
import smrs.backend_gestion_absence_ism.web.dto.JustificationForAbsenceDetail;
import smrs.backend_gestion_absence_ism.web.mapper.AbsenceWebMapper;
import smrs.backend_gestion_absence_ism.web.mapper.JustificationWebMapper;

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
    private final AnneeScolaireRepository anneeScolaireRepository;

    /**
     * récupérer toutes les absences
     */
    @Override
    public List<Absence> getAllAbsences() {
        return absenceRepository.findAll();
    }

    /**
     * récupérer les absences d'un étudiant par son id
     */
    @Override
    public List<Absence> getAbsencesByEtudiant(String etudiantId) {
        if (etudiantId == null)
            return List.of();
        return absenceRepository.findByEtudiantId(etudiantId);
    }

    /**
     * récupérer les absences d'un étudiant par son matricule
     */
    @Override
    public List<Absence> getAbsencesByEtudiantMatricule(String matricule) {
        return List.of();
    }

    /**
     * récupére les absences date
     * 
     * @param date
     */
    @Override
    public List<Absence> getAbsencesByDate(LocalDateTime date) {
        return absenceRepository.findByCreatedAt(date);
    }

    /**
     * récupére les absences par l'id cours
     * 
     * @param coursId, l'id du cours
     */
    @Override
    public List<Absence> getAbsencesByCours(String coursId) {
        return List.of();
    }

    /**
     * on envoie les infos de l'etudiant avec les 5 derniers absences
     * 
     * @param etudiantId, l'id de l'étudiant
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
     * specifique et aussi par nom cours
     * debut et fin
     * 
     * @param etudiantId l'id de l'etudiant
     * @param type       le type d'absence
     * @param date       une date spécifique
     * @param coursNom   le nom du cours
     */
    @Override
    public List<AbsenceMobileDto> getAbsencesEtudiant(String etudiantId, TypeAbsence type,
            // LocalDate dateDebut,LocalDate dateFin,
            LocalDate date,
            String coursNom) {

        List<Absence> absences;

        if (type != null) {
            absences = absenceRepository.findByEtudiantIdAndType(etudiantId, type);
        } else {
            absences = absenceRepository.findByEtudiantId(etudiantId);
        }

        // if (dateDebut != null && dateFin != null) {
        // LocalDateTime startDateTime = dateDebut.atStartOfDay();
        // LocalDateTime endDateTime = dateFin.atTime(23, 59, 59);

        // absences = absences.stream()
        // .filter(a -> a.getHeurePointage().isAfter(startDateTime) &&
        // a.getHeurePointage().isBefore(endDateTime))
        // .toList();
        // }

        if (date != null) {
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(23, 59, 59);

            absences = absences.stream()
                    .filter(a -> !a.getHeurePointage().isBefore(startOfDay) &&
                            !a.getHeurePointage().isAfter(endOfDay))
                    .toList();
        }

        if (coursNom != null && !coursNom.isEmpty()) {
            String searchTerm = coursNom.toLowerCase();
            absences = absences.stream()
                    .filter(a -> a.getCours() != null &&
                            a.getCours().getNom() != null &&
                            (a.getCours().getNom().toLowerCase().startsWith(searchTerm)))
                    // || a.getCours().getNom().toLowerCase().contains(searchTerm)))
                    .toList();
        }

        return absences.stream()
                .map(absenceMobileMapper::toMobileDto)
                .sorted((a, b) -> b.getHeurePointage().compareTo(a.getHeurePointage()))
                .toList();
    }

    /**
     * récupére les infos de l'absence et celle des justification
     * 
     * @param absenceId l'id de l'absence
     */
    @Override
    public AbsenceDetailWithJustificationDto getAbsenceDetailWithJustification(String absenceId) {
        Absence absence = absenceRepository.findById(absenceId)
                .orElseThrow(() -> new RuntimeException("Absence non trouvée"));
        AbsenceWebDto dto = AbsenceWebMapper.INSTANCE.toDto(absence);
        JustificationForAbsenceDetail justificationDto = absence.getJustification() != null
                ? JustificationWebMapper.INSTANCE.toJustificationForAbsenceDetail(absence.getJustification())
                : null;
        return new AbsenceDetailWithJustificationDto(dto, justificationDto);
    }

    /**
     * 
     */
    @Override
    public List<AbsenceWebDto> getAbsencesForActiveYear() {
        try {
            // Récupérer l'année scolaire active
            AnneeScolaire activeYear = anneeScolaireRepository.findByActive(Boolean.TRUE);
            if (activeYear == null) {
                throw new EntityNotFoundException("Aucune année scolaire active n'est configurée");
            }

            // Récupérer toutes les absences
            List<Absence> allAbsences = absenceRepository.findAll();

            // Filtrer les absences des étudiants inscrits pour l'année active
            allAbsences.stream()
                    .filter(absence -> {
                        Etudiant etudiant = absence.getEtudiant();
                        if (etudiant == null) {
                            return false;
                        }

                        // Vérifier si l'étudiant a une inscription valide pour l'année active
                        return etudiant.getInscriptions().stream()
                                .anyMatch(inscription -> inscription.getAnneeScolaire() != null &&
                                        inscription.getAnneeScolaire().isActive() &&
                                        inscription.isValide());
                    }).sorted(Comparator.comparing(Absence::getHeurePointage).reversed()).toList();

            // Convertir en DTO
            return allAbsences.stream()
                    .map(AbsenceWebMapper.INSTANCE::toDto)
                    .toList();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erreur lors de la récupération des absences pour l'année active : " + e.getMessage(), e);
        }
    }
}
