package smrs.backend_gestion_absence_ism.services;

public interface AbsenceGeneratorScheduler {
    void genererAbsencesCoursTermines();

    void reinitialiserPointageQuotidien();

    void forcerGenerationAbsences(String coursId);
}
