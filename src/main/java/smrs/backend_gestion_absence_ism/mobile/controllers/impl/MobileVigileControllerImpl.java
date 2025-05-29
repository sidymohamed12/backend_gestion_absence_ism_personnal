package smrs.backend_gestion_absence_ism.mobile.controllers.impl;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import smrs.backend_gestion_absence_ism.mobile.controllers.MobileVigileController;
import smrs.backend_gestion_absence_ism.mobile.dto.RestResponse;
import smrs.backend_gestion_absence_ism.mobile.dto.request.PointageRequestDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.AbsenceMobileDto;
import smrs.backend_gestion_absence_ism.services.PointageService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Vigile", description = "Gestion des fonctionnalité du vigile")
public class MobileVigileControllerImpl implements MobileVigileController {

    private final PointageService pointageService;

    @Override
    public ResponseEntity<Map<String, Object>> pointerEtudiant(PointageRequestDto requestDto) {
        if (requestDto.getVigileId().isEmpty() || requestDto.getMatriculeEtudiant().isEmpty()) {
            return new ResponseEntity<>(
                    RestResponse.response(HttpStatus.NOT_FOUND, null, "Id de l'étudiant ou du vigile est invalid"),
                    HttpStatus.NOT_FOUND);
        }
        AbsenceMobileDto response = pointageService.effectuerPointage(requestDto);
        return new ResponseEntity<>(RestResponse.response(
                HttpStatus.CREATED,
                response, "AbsenceMobileDto"), HttpStatus.CREATED);
    }

}
