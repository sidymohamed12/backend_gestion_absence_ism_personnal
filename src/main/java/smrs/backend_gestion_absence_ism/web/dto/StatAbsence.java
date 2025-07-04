package smrs.backend_gestion_absence_ism.web.dto;

import lombok.Data;

@Data
public class StatAbsence {
    private Integer totalAbsence;
    private Integer totalRetard;
    private Integer totalAbsenceRetard;
    private Integer totalJustifie;
    private Integer totalNonJustifie;
    private Integer totalTraite;

}
