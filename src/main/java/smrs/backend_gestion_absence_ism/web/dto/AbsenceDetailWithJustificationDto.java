package smrs.backend_gestion_absence_ism.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceDetailWithJustificationDto {
    private AbsenceWebDto absence;
    private JustificationForAbsenceDetail justification;
}