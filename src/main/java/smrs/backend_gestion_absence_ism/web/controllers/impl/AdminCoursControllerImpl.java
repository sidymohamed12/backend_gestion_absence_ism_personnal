package smrs.backend_gestion_absence_ism.web.controllers.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import smrs.backend_gestion_absence_ism.data.entities.Cours;
import smrs.backend_gestion_absence_ism.services.CoursService;
import smrs.backend_gestion_absence_ism.web.controllers.AdminCoursController;
import smrs.backend_gestion_absence_ism.web.dto.CoursClasseWebDto;
import smrs.backend_gestion_absence_ism.web.mapper.CoursClasseWebMapper;

@RestController
@AllArgsConstructor
public class AdminCoursControllerImpl implements AdminCoursController {
    private final CoursService coursService;

    @Override
    public ResponseEntity<List<CoursClasseWebDto>> getCoursByClasse(String classeId) {
        try {
            List<Cours> coursList = coursService.getCoursByClasse(classeId);
            if (coursList.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            List<CoursClasseWebDto> dtoList = CoursClasseWebMapper.INSTANCE.toDtoList(coursList);
            return ResponseEntity.ok(dtoList);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
