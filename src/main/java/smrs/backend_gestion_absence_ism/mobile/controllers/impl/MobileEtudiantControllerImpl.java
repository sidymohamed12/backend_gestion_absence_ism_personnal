package smrs.backend_gestion_absence_ism.mobile.controllers.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import smrs.backend_gestion_absence_ism.data.enums.TypeAbsence;
import smrs.backend_gestion_absence_ism.mobile.controllers.MobileEtudiantController;
import smrs.backend_gestion_absence_ism.mobile.dto.RestResponse;
import smrs.backend_gestion_absence_ism.mobile.dto.request.JustificationRequestDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.AbsenceMobileDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.EtudiantAccueilMobileDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.JustificationMobileDto;
import smrs.backend_gestion_absence_ism.services.AbsenceService;
import smrs.backend_gestion_absence_ism.services.JustificationService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Etudiant", description = "Gestion des fonctionnalité de L'étudiant")
public class MobileEtudiantControllerImpl implements MobileEtudiantController {

        private final AbsenceService absenceService;
        private final JustificationService justificationService;

        @Override
        public ResponseEntity<Map<String, Object>> getInfoEtudiantConnectAccueil(String etudiantId) {
                EtudiantAccueilMobileDto accueil = absenceService.getAccueilEtudiant(etudiantId);
                return new ResponseEntity<>(RestResponse.response(HttpStatus.OK, accueil, "EtudiantAccueilMobileDto"),
                                HttpStatus.OK);
        }

        @Override
        public ResponseEntity<Map<String, Object>> getAbsencesByEtudiant(String etudiantId, TypeAbsence type,
                        // LocalDate dateDebut, LocalDate dateFin,
                        LocalDate date, String coursNom) {
                if (etudiantId.isEmpty()) {
                        return new ResponseEntity<>(
                                        RestResponse.response(HttpStatus.NOT_FOUND, null,
                                                        "Id de l'étudiant n'existe pas"),
                                        HttpStatus.NOT_FOUND);
                }
                List<AbsenceMobileDto> absences = absenceService.getAbsencesEtudiant(
                                etudiantId, type, date, coursNom);
                return new ResponseEntity<>(RestResponse.response(HttpStatus.OK, absences, "Liste de AbsenceMobileDto"),
                                HttpStatus.OK);
        }

        @Override
        public ResponseEntity<Map<String, Object>> creerJustification(JustificationRequestDto request) {
                JustificationMobileDto response = justificationService.ajouterJustification(request);
                return new ResponseEntity<>(
                                RestResponse.response(HttpStatus.CREATED, response, "JustificationMobileDTO"),
                                HttpStatus.CREATED);
        }

        @Override
        public ResponseEntity<Map<String, Object>> getJustificationbyId(String justificationId) {
                if (justificationId.isEmpty()) {
                        return new ResponseEntity<>(
                                        RestResponse.response(HttpStatus.NOT_FOUND, null,
                                                        "Id de cette justification n'existe pas"),
                                        HttpStatus.NOT_FOUND);
                }
                var response = justificationService.getJustificationById(justificationId);
                return new ResponseEntity<>(RestResponse.response(HttpStatus.OK, response, "JustificationMobileDTO"),
                                HttpStatus.OK);
        }

}
