package smrs.backend_gestion_absence_ism.mobile.controllers.impl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import smrs.backend_gestion_absence_ism.mobile.controllers.MobileVigileController;
import smrs.backend_gestion_absence_ism.mobile.dto.RestResponse;
import smrs.backend_gestion_absence_ism.mobile.dto.request.PointageRequestDto;
import smrs.backend_gestion_absence_ism.services.EtudiantService;
import smrs.backend_gestion_absence_ism.services.PointageService;
import smrs.backend_gestion_absence_ism.services.VigileService;
import smrs.backend_gestion_absence_ism.utils.exceptions.EntityNotFoundException;

@RestController
@RequiredArgsConstructor
public class MobileVigileControllerImpl implements MobileVigileController {

    private final PointageService pointageService;
    private final EtudiantService etudiantService;
    private final VigileService vigileService;

    @Override
    public ResponseEntity<Map<String, Object>> getInfoVigileConnect(String vigileId) {
        if (vigileId.isEmpty()) {
            return new ResponseEntity<>(
                    RestResponse.response(HttpStatus.NOT_FOUND, null, "Id du vigile est invalid"),
                    HttpStatus.NOT_FOUND);
        }
        var response = vigileService.getVigileById(vigileId);
        return new ResponseEntity<>(RestResponse.response(HttpStatus.OK, response, "Vigile dto"), HttpStatus.OK);
    }

    @Override
    public CompletableFuture<ResponseEntity<Map<String, Object>>> pointerEtudiant(PointageRequestDto requestDto) {
        if (requestDto.getMatriculeEtudiant() == null || requestDto.getVigileId() == null) {
            return CompletableFuture.completedFuture(
                    new ResponseEntity<>(
                            RestResponse.response(HttpStatus.BAD_REQUEST, null, "Paramètres manquants"),
                            HttpStatus.BAD_REQUEST));
        }
        return pointageService.effectuerPointage(requestDto)
                .thenApply(dto -> {
                    if (dto == null) {
                        // Étudiant déjà pointé
                        return new ResponseEntity<>(
                                RestResponse.response(HttpStatus.CONFLICT, null, "Étudiant déjà pointé pour ce cours"),
                                HttpStatus.CONFLICT);
                    }

                    // Succès
                    return new ResponseEntity<>(
                            RestResponse.response(HttpStatus.CREATED, dto, "HistoriquePointageMobileDto"),
                            HttpStatus.CREATED);
                })
                .exceptionally(ex -> {
                    Throwable cause = ex.getCause() != null ? ex.getCause() : ex;

                    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
                    String message = "Erreur interne du serveur";

                    if (cause instanceof EntityNotFoundException) {
                        status = HttpStatus.NOT_FOUND;
                        message = cause.getMessage();
                    }

                    return new ResponseEntity<>(
                            RestResponse.response(status, null, message),
                            status);
                });
    }

    @Override
    public ResponseEntity<Map<String, Object>> getHistoriquePointage(String vigileId, LocalDate date,
            String etudiantMatricule) {
        if (vigileId.isEmpty()) {
            return new ResponseEntity<>(
                    RestResponse.response(HttpStatus.NOT_FOUND, null, "Id du vigile est invalid"),
                    HttpStatus.NOT_FOUND);
        }
        var response = pointageService.getHistoriquePointage(vigileId, date, etudiantMatricule);
        return new ResponseEntity<>(RestResponse.response(HttpStatus.OK, response, vigileId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getEtudiantByRecherche(HttpServletRequest request) {
        String fullPath = request.getRequestURI();
        String basePath = "/api/mobile/vigile/etudiant/";
        String matricule = fullPath.substring(fullPath.indexOf(basePath) + basePath.length());

        // Décodage manuel si nécessaire
        String decodedMatricule = java.net.URLDecoder.decode(matricule, StandardCharsets.UTF_8);
        var etudiant = etudiantService.getEtudiantByMatricule(decodedMatricule);
        return new ResponseEntity<>(RestResponse.response(HttpStatus.OK, etudiant, "EtudiantMobileDTO"), HttpStatus.OK);
    }
}
