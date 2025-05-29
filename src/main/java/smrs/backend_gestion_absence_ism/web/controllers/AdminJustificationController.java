package smrs.backend_gestion_absence_ism.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smrs.backend_gestion_absence_ism.web.dto.JustificationWebDto;

import java.util.List;

@RequestMapping("/api/web/admin/justifications")
public interface AdminJustificationController {
    @GetMapping
    ResponseEntity<List<JustificationWebDto>> getAllJustifications();
    @GetMapping("/en-attente")
    ResponseEntity<List<JustificationWebDto>> getJustificationsEnAttente();
    @PutMapping("/{id}/validation")
    ResponseEntity<JustificationWebDto> validerJustification(
            @PathVariable Long id,
            @RequestParam Long adminId,
            @RequestParam boolean estValidee);
}
