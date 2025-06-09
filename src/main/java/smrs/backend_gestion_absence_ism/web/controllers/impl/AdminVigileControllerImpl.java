package smrs.backend_gestion_absence_ism.web.controllers.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import smrs.backend_gestion_absence_ism.mobile.dto.response.HistoriquePointageMobileDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.UtilisateurMobileDto;
import smrs.backend_gestion_absence_ism.services.PointageService;
import smrs.backend_gestion_absence_ism.services.VigileService;
import smrs.backend_gestion_absence_ism.web.controllers.AdminVigileController;

@RestController
@AllArgsConstructor
public class AdminVigileControllerImpl implements AdminVigileController {
    private final VigileService vigileService;
    private final PointageService pointageService;

    @Override
    public ResponseEntity<List<UtilisateurMobileDto>> getAllVigiles() {
        List<UtilisateurMobileDto> response = vigileService.getAllVigiles();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<HistoriquePointageMobileDto>> getHistoriquePointage(String vigileId, LocalDate date,
            String etudiantMatricule) {
        if (vigileId.isEmpty()) {
            return new ResponseEntity<>(List.of(),
                    HttpStatus.NOT_FOUND);
        }
        var response = pointageService.getHistoriquePointage(vigileId, date, etudiantMatricule);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
