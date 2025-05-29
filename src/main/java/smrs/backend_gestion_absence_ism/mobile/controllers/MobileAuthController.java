package smrs.backend_gestion_absence_ism.mobile.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/api/mobile/auth")
public interface MobileAuthController {
    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody Map<String, String> credentials);
}
