package smrs.backend_gestion_absence_ism.web.controllers.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import smrs.backend_gestion_absence_ism.services.JustificationService;
import smrs.backend_gestion_absence_ism.web.controllers.AdminJustificationController;
import smrs.backend_gestion_absence_ism.web.dto.JustificationTraitementRequest;
import smrs.backend_gestion_absence_ism.web.dto.JustificationWebDto;
import smrs.backend_gestion_absence_ism.web.mapper.JustificationWebMapper;

import java.util.List;

@RestController
@AllArgsConstructor
public class AdminJustificationControllerImpl implements AdminJustificationController {
    private final JustificationService justificationService;
    private final JustificationWebMapper justificationWebMapper;

    @Override
    public ResponseEntity<List<JustificationWebDto>> getAllJustifications() {
        List<JustificationWebDto> dtos = justificationService.getAllJustifications()
                .stream()
                .map(justificationWebMapper::toJustificationWebDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<List<JustificationWebDto>> getJustificationsEnAttente() {
        List<JustificationWebDto> dtos = justificationService.getJustificationsEnAttente()
                .stream()
                .map(justificationWebMapper::toJustificationWebDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @Override
    public ResponseEntity<JustificationWebDto> validerJustification(String id,
            @Valid JustificationTraitementRequest request) {
        var response = justificationService.traiterJustification(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
