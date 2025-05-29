package smrs.backend_gestion_absence_ism.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Value("${spring.application.version}")
    private String version;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GESTION ABSENCE API")
                        .version("1.0.0")
                        .description(
                                "API pour gérer les absences des étudiants. Grace au vigile qui va pointer via scan le qr code des etudiants on marquera ce dernier comme present ou retard en fonction de son arrivé. A la fin du cours la liste d'absence doit se générer en vérifiant seulement les etudiants qui onr été pointé."));
    }

}
