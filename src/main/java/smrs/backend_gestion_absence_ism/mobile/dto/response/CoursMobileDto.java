package smrs.backend_gestion_absence_ism.mobile.dto.response;

import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Informations détaillées sur un cours")
public class CoursMobileDto {
    @Schema(description = "ID du cours", example = "663bd21f9f5c4c7d88ab3456")
    private String id;

    @Schema(description = "Nom du cours", example = "Architecture des systèmes")
    private String nom;

    @Schema(description = "Nom de l'enseignant", example = "Pr. DIOP")
    private String enseignant;

    @Schema(description = "Salle du cours", example = "302")
    private String salle;

    @Schema(description = "Jour du cours", example = "Mardi")
    private String jour;

    @Schema(description = "l'heure de debut du cours", example = "14:00")
    private LocalTime heureDebut;

    @Schema(description = "l'heure de fin du cours", example = "16:00")
    private LocalTime heureFin;

    @Schema(description = "Nom de la matière", example = "POO")
    private String matiereNom;

    @Schema(description = "Nom de la classe", example = "L2 Informatique")
    private String classeNom;
}
