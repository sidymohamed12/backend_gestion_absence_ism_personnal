package smrs.backend_gestion_absence_ism.web.controllers.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import smrs.backend_gestion_absence_ism.data.entities.Classe;
import smrs.backend_gestion_absence_ism.services.ClasseService;
import smrs.backend_gestion_absence_ism.services.EtudiantService;
import smrs.backend_gestion_absence_ism.web.controllers.AdminClasseController;
import smrs.backend_gestion_absence_ism.web.dto.ClasseWebDto;
import smrs.backend_gestion_absence_ism.web.dto.EtudiantListDto;
import smrs.backend_gestion_absence_ism.web.mapper.ClasseWebMapper;

@RestController
@AllArgsConstructor
public class AdminClasseControllerImpl implements AdminClasseController {
    private final ClasseService classeService;
    private final EtudiantService etudiantService;

    @Override
    public ResponseEntity<List<ClasseWebDto>> getAllClasses(String searchFiliere, String searchNiveau) {
        try {
            var response = classeService.getAllClasses();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<List<ClasseWebDto>> getClassesForActiveYear() {
        try {
            List<Classe> classes = classeService.getClassesForActiveAcademicYear();
            List<ClasseWebDto> classeDto = ClasseWebMapper.INSTANCE.toDtoList(classes);
            return ResponseEntity.ok(classeDto);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Aucune année scolaire active n'est configurée")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.internalServerError().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<List<EtudiantListDto>> getAllEtudiantsByClasseId(String classeId) {
        if (classeId == null || classeId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var etudiants = etudiantService.getAllEtudiantsByClasseId(classeId);
        if (etudiants.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(etudiants);
    }

    @Override
    public ResponseEntity<ClasseWebDto> getClasseById(String classeId) {
        if (classeId == null || classeId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var response = classeService.getClasseById(classeId);
        return ResponseEntity.ok(response);
    }
}
