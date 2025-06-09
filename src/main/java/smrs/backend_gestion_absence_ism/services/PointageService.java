package smrs.backend_gestion_absence_ism.services;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import smrs.backend_gestion_absence_ism.mobile.dto.request.PointageRequestDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.HistoriquePointageMobileDto;

public interface PointageService {

    CompletableFuture<HistoriquePointageMobileDto> effectuerPointage(PointageRequestDto request);

    List<HistoriquePointageMobileDto> getHistoriquePointage(String vigileId,
            // LocalDate dateDebut,
            // LocalDate dateFin,
            LocalDate date,
            String etudiantMatricule);

}
