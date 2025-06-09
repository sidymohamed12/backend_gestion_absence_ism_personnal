package smrs.backend_gestion_absence_ism.services.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import smrs.backend_gestion_absence_ism.data.entities.Absence;
import smrs.backend_gestion_absence_ism.data.entities.AnneeScolaire;
import smrs.backend_gestion_absence_ism.data.entities.Cours;
import smrs.backend_gestion_absence_ism.data.entities.Etudiant;
import smrs.backend_gestion_absence_ism.data.entities.Vigile;
import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;
import smrs.backend_gestion_absence_ism.data.repositories.AbsenceRepository;
import smrs.backend_gestion_absence_ism.data.repositories.AnneeScolaireRepository;
import smrs.backend_gestion_absence_ism.data.repositories.CoursRepository;
import smrs.backend_gestion_absence_ism.data.repositories.EtudiantRepository;
import smrs.backend_gestion_absence_ism.data.repositories.VigileRepository;
import smrs.backend_gestion_absence_ism.mobile.dto.request.PointageRequestDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.HistoriquePointageMobileDto;
import smrs.backend_gestion_absence_ism.mobile.mapper.PointageMobileMapper;
import smrs.backend_gestion_absence_ism.services.PointageService;
import smrs.backend_gestion_absence_ism.utils.exceptions.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointageServiceImpl implements PointageService {

    private final EtudiantRepository etudiantRepository;
    private final CoursRepository coursRepository;
    private final VigileRepository vigileRepository;
    private final AbsenceRepository absenceRepository;
    private final AnneeScolaireRepository anneeScolaireRepository;
    private static final int TOLERANCE_RETARD_MINUTES = 15;
    private final PointageMobileMapper pointageMobileMapper;

    // --------------------------------- FONCTION PRIVATE POUR ADD POINTAGE
    // -----------------------------------------

    /**
     * on va recercher le cours en cours ou le prochain cours
     */
    private Cours trouverCoursActuel(List<Cours> cours, LocalTime heureActuelle) {
        // Cours en cours (dans les 15 minutes après le début)
        for (Cours c : cours) {
            LocalTime debut = c.getHeureDebut();
            LocalTime fin = c.getHeureFin();

            if (heureActuelle.isAfter(debut.minusMinutes(5)) && heureActuelle.isBefore(fin)) {
                return c;
            }
        }

        // Prochain cours (dans l'heure qui suit (60mn))
        for (Cours c : cours) {
            LocalTime debut = c.getHeureDebut();

            if (heureActuelle.isBefore(debut) &&
                    heureActuelle.isAfter(debut.minusMinutes(60))) {
                return c;
            }
        }

        return null;
    }

    /**
     * Calcul des minutes de retard
     */
    private int calculerMinutesRetard(LocalTime heureDebut, LocalTime heureArrivee) {
        if (heureArrivee.isBefore(heureDebut) || heureArrivee.equals(heureDebut)) {
            return 0;
        }
        return (int) Duration.between(heureDebut, heureArrivee).toMinutes();
    }

    /**
     * Déterminer le type d'absence selon le retard
     */
    private TypeAbsence determinerTypeAbsence(int minutesRetard) {
        if (minutesRetard <= TOLERANCE_RETARD_MINUTES) {
            return TypeAbsence.PRESENT;
        } else {
            return TypeAbsence.RETARD;
        }
    }

    // ----------------------------------------------------------------------------------------------

    /**
     * pour enregistrer pointage
     * 
     * @param request pointageRequest
     */
    @Async
    @Override
    public CompletableFuture<HistoriquePointageMobileDto> effectuerPointage(PointageRequestDto request) {

        Etudiant etudiant = etudiantRepository.findByMatricule(request.getMatriculeEtudiant())
                .orElseThrow(() -> new EntityNotFoundException("Étudiant non trouvé"));

        Vigile vigile = vigileRepository.findById(request.getVigileId())
                .orElseThrow(() -> new EntityNotFoundException("Vigile non trouvé"));

        // on calcule la date à la quelle on est et on récupére aussi le jour
        LocalDateTime maintenant = LocalDateTime.now();

        // on récupère l'année encours
        AnneeScolaire anneeScolaire = anneeScolaireRepository.findByActive(Boolean.TRUE);
        if (anneeScolaire == null) {
            log.warn("Aucune année scolaire active");
            return CompletableFuture.completedFuture(null);
        }

        // on doit verifier si l'etudiant à cours ajourd'hui
        List<Cours> coursAujourdhui = coursRepository.findByClasseIdAndDateAndCurrentYearId(
                etudiant.getClasse().getId(), LocalDate.now(), anneeScolaire.getId());

        if (coursAujourdhui.isEmpty()) {
            log.info("Aucun cours aujourd'hui pour l'étudiant {}, pointage historique seulement",
                    etudiant.getMatricule());
            Absence absence = new Absence();
            absence.setHeurePointage(maintenant);
            absence.setType(TypeAbsence.PRESENT);
            absence.setEtudiant(etudiant);
            absence.setVigile(vigile);
            absence.onPrePersist();
            absenceRepository.save(absence);
            return CompletableFuture.completedFuture(pointageMobileMapper.toHistoriquePointageMobileDto(absence));
        }

        // Trouver le cours en cours ou à venir
        Cours coursActuel = trouverCoursActuel(coursAujourdhui, maintenant.toLocalTime());

        // on regarde si l'etudiant a deja ete pointe
        if (absenceRepository.existsByEtudiantIdAndCoursId(etudiant.getId(), coursActuel.getId())) {
            log.warn("Étudiant déjà pointé pour ce cours");
            return CompletableFuture.completedFuture(null);
        }

        // Calculer le retard et déterminer le type d'absence
        LocalTime heureDebut = coursActuel.getHeureDebut();
        LocalTime heureArrivee = maintenant.toLocalTime();

        Integer minutesRetard = calculerMinutesRetard(heureDebut, heureArrivee);
        TypeAbsence typeAbsence = determinerTypeAbsence(minutesRetard);

        // Créer l'absence/présence
        Absence absence = new Absence();
        absence.setHeurePointage(maintenant);
        absence.setMinutesRetard(Math.max(0, minutesRetard));
        absence.setType(typeAbsence);
        absence.setEtudiant(etudiant);
        absence.setCours(coursActuel);
        absence.setVigile(vigile);
        absence.setDuree(Duration.between(heureDebut, heureArrivee).toMinutes() > 0 ? heureArrivee : heureDebut);
        absence.onPrePersist();
        absenceRepository.save(absence);

        return CompletableFuture.completedFuture(pointageMobileMapper.toHistoriquePointageMobileDto(absence));

    }

    /**
     * Récupère l'historique de pointage pour un vigile
     */
    public List<HistoriquePointageMobileDto> getHistoriquePointage(String vigileId,
            // LocalDate dateDebut,
            // LocalDate dateFin,
            LocalDate date,
            String etudiantMatricule) {

        vigileRepository.findById(vigileId)
                .orElseThrow(() -> new EntityNotFoundException("Vigile non trouvé"));

        LocalDateTime startOfDay = date != null
                ? date.atStartOfDay()
                : LocalDateTime.now().minusDays(30).withHour(0).withMinute(0);

        LocalDateTime endOfDay = date != null
                ? date.atTime(23, 59, 59)
                : LocalDateTime.now();

        List<Absence> pointages = absenceRepository.findByVigileIdOrderByHeurePointageDesc(vigileId);

        List<Absence> filteredPointages = pointages.stream()
                .filter(a -> a.getHeurePointage().isAfter(startOfDay) &&
                        a.getHeurePointage().isBefore(endOfDay))
                .filter(a -> etudiantMatricule == null || etudiantMatricule.isEmpty() ||
                        (a.getEtudiant() != null &&
                                a.getEtudiant().getMatricule() != null &&
                                a.getEtudiant().getMatricule().toLowerCase()
                                        .contains(etudiantMatricule.toLowerCase())))
                .toList();

        return filteredPointages.stream()
                .map(pointageMobileMapper::toHistoriquePointageMobileDto)
                .toList();

    }

}
