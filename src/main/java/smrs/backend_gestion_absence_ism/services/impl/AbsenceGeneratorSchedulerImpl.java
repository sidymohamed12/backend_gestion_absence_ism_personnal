package smrs.backend_gestion_absence_ism.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import smrs.backend_gestion_absence_ism.data.entities.Absence;
import smrs.backend_gestion_absence_ism.data.entities.AnneeScolaire;
import smrs.backend_gestion_absence_ism.data.entities.Cours;
import smrs.backend_gestion_absence_ism.data.entities.Etudiant;
import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;
import smrs.backend_gestion_absence_ism.data.repositories.AbsenceRepository;
import smrs.backend_gestion_absence_ism.data.repositories.AnneeScolaireRepository;
import smrs.backend_gestion_absence_ism.data.repositories.CoursRepository;
import smrs.backend_gestion_absence_ism.data.repositories.EtudiantRepository;
import smrs.backend_gestion_absence_ism.services.AbsenceGeneratorScheduler;
import smrs.backend_gestion_absence_ism.utils.exceptions.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AbsenceGeneratorSchedulerImpl implements AbsenceGeneratorScheduler {
    private final CoursRepository coursRepository;
    private final AbsenceRepository absenceRepository;
    private final AnneeScolaireRepository anneeScolaireRepository;
    private final EtudiantRepository etudiantRepository;

    /**
     * Tâche planifiée qui s'exécute toutes les minutes pour vérifier
     * si des cours viennent de se terminer et générer les absences
     * des étudiants non pointés
     */
    @Scheduled(fixedRate = 60000)
    @Transactional
    @Override
    public void genererAbsencesCoursTermines() {
        try {
            LocalDateTime maintenant = LocalDateTime.now();
            LocalTime heureMaintenant = maintenant.toLocalTime();

            log.debug("Vérification des cours terminés à {}", maintenant);

            // Récupérer l'année scolaire active
            AnneeScolaire anneeScolaire = anneeScolaireRepository.findByActive(Boolean.TRUE);
            if (anneeScolaire == null) {
                log.warn("Aucune année scolaire active trouvée");
                return;
            }

            // Trouver tous les cours qui viennent de se terminer (dans les 2 dernières
            // minutes)
            LocalTime heureDebut = heureMaintenant.minusMinutes(2);
            LocalTime heureFin = heureMaintenant;

            List<Cours> coursTermines = coursRepository.findByDateAndCurrentYearIdAndPointageFermeFalse(
                    LocalDate.now(), anneeScolaire.getId())
                    .stream()
                    .filter(cours -> heureMaintenant.isAfter(cours.getHeureFin()))
                    .toList();

            log.info("Trouvé {} cours terminés entre {} et {}",
                    coursTermines.size(), heureDebut, heureFin);

            for (Cours cours : coursTermines) {
                genererAbsencesPourCours(cours);
            }

        } catch (Exception e) {
            log.error("Erreur lors de la génération automatique des absences", e);
        }
    }

    /**
     * Génère les absences pour un cours donné
     * 
     * @param cours
     */
    private void genererAbsencesPourCours(Cours cours) {
        try {
            // Vérifier si les absences ont déjà été générées pour ce cours
            // si cours.isPoi... c'ets true donc pointage déja généré
            if (cours.isPointageFerme()) {
                log.debug("Pointage déjà fermé pour le cours {} - {}",
                        cours.getNom(), cours.getHeureDebut());
                return;
            }

            log.info("Génération des absences pour le cours: {} - {} ({}-{})",
                    cours.getNom(), cours.getDate(), cours.getHeureDebut(), cours.getHeureFin());

            // Récupérer tous les étudiants de la classe
            List<Etudiant> etudiantsClasse = etudiantRepository.findByClasseId(cours.getClasse().getId());

            int absencesGenerees = 0;

            for (Etudiant etudiant : etudiantsClasse) {
                // Vérifier si l'étudiant a déjà été pointé pour ce cours
                boolean dejaPointe = absenceRepository.existsByEtudiantIdAndCoursId(
                        etudiant.getId(), cours.getId());

                if (!dejaPointe) {
                    // Créer une absence complète pour l'étudiant non pointé
                    creerAbsenceComplete(etudiant, cours);
                    absencesGenerees++;

                    log.debug("Absence générée pour l'étudiant {} (matricule: {}) - cours: {}",
                            etudiant.getUtilisateur().getNom() + " " + etudiant.getUtilisateur().getPrenom(),
                            etudiant.getMatricule(),
                            cours.getNom());
                }
            }

            // Marquer le pointage comme fermé pour ce cours
            marquerCoursCommeTermine(cours);

            log.info("Génération terminée pour le cours {} - {} absences créées sur {} étudiants",
                    cours.getNom(), absencesGenerees, etudiantsClasse.size());

        } catch (Exception e) {
            log.error("Erreur lors de la génération des absences pour le cours {}",
                    cours.getNom(), e);
        }
    }

    /**
     * Tâche de nettoyage quotidienne - remet à zéro les flags pointageFerme
     * pour permettre une nouvelle journée de pointage
     */
    @Scheduled(cron = "0 0 22 * * *")
    @Transactional
    @Override
    public void reinitialiserPointageQuotidien() {
        try {
            log.info("Réinitialisation quotidienne des flags de pointage");

            // Récupérer l'année scolaire active
            AnneeScolaire anneeScolaire = anneeScolaireRepository.findByActive(Boolean.TRUE);
            if (anneeScolaire == null) {
                return;
            }

            // Remettre à false tous les pointageFerme pour une nouvelle journée
            List<Cours> tousLesCours = coursRepository.findByCurrentYearId(anneeScolaire.getId());
            for (Cours cours : tousLesCours) {
                cours.setPointageFerme(false);
            }
            coursRepository.saveAll(tousLesCours);

            log.info("Réinitialisation terminée pour {} cours", tousLesCours.size());

        } catch (Exception e) {
            log.error("Erreur lors de la réinitialisation quotidienne", e);
        }
    }

    /**
     * Méthode utilitaire pour forcer la génération des absences
     * d'un cours spécifique (pour usage manuel)
     * 
     * @param coursId
     */
    @Override
    public void forcerGenerationAbsences(String coursId) {
        try {
            Cours cours = coursRepository.findById(coursId)
                    .orElseThrow(() -> new EntityNotFoundException("Cours non trouvé"));

            log.info("Génération forcée des absences pour le cours: {}", cours.getNom());
            genererAbsencesPourCours(cours);

        } catch (Exception e) {
            log.error("Erreur lors de la génération forcée pour le cours {}", coursId, e);
            throw e;
        }
    }

    /**
     * créer l'absence compléte d'un etudiant
     * 
     * @param etudiant
     * @param cours
     * @return
     */
    private Absence creerAbsenceComplete(Etudiant etudiant, Cours cours) {
        Absence absence = new Absence();
        absence.setType(TypeAbsence.ABSENCE_COMPLETE);
        absence.setEtudiant(etudiant);
        absence.setCours(cours);
        absence.setHeurePointage(null); // Pas de pointage = absence complète
        absence.setMinutesRetard(null); // Pas applicable pour absence complète
        absence.setDuree(null); // Pas de durée de présence
        absence.setVigile(null); // Pas de vigile car pas de pointage
        absence.setJustification(null); // Pas de justification initiale
        absence.onPrePersist();

        log.debug("Absence complète préparée pour l'étudiant {} - cours {}",
                etudiant.getMatricule(), cours.getNom());

        return absence;
    }

    /**
     * marque un cours comme terminer en fermant la pointage
     * 
     * @param cours
     */
    private void marquerCoursCommeTermine(Cours cours) {
        cours.setPointageFerme(true);
        coursRepository.save(cours);
    }

}
