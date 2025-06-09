package smrs.backend_gestion_absence_ism.web.controllers.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import smrs.backend_gestion_absence_ism.mobile.dto.response.UtilisateurMobileDto;
import smrs.backend_gestion_absence_ism.services.EtudiantService;
import smrs.backend_gestion_absence_ism.web.controllers.AdminEtudiantController;
import smrs.backend_gestion_absence_ism.web.dto.EtudiantListDto;

@RestController
@AllArgsConstructor
public class AdminEtudiantControllerImpl implements AdminEtudiantController {
    private final EtudiantService etudiantService;

    @Override
    public ResponseEntity<List<EtudiantListDto>> listerEtudiantsAnneeActive() {
        // List<EtudiantListDto> etudiants =
        // etudiantService.listerEtudiantsAnneeActive();
        // if (etudiants.isEmpty()) {
        // return ResponseEntity.notFound().build();
        // }
        // return ResponseEntity.ok(etudiants);
        return null;
    }

    @Override
    public ResponseEntity<List<UtilisateurMobileDto>> getAllEtudiants() {
        var response = etudiantService.listerEtudiantsAnneeActive();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
