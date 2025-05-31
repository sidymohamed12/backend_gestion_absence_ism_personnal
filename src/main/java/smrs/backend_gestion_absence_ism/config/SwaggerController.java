package smrs.backend_gestion_absence_ism.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController {
    @GetMapping("/")
    public String home() {
        return "redirect:/swagger-ui/index.html";
    }
}
