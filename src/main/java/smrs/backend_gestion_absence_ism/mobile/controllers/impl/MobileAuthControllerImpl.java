package smrs.backend_gestion_absence_ism.mobile.controllers.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import smrs.backend_gestion_absence_ism.mobile.controllers.MobileAuthController;

import java.util.Map;

@RestController
public class MobileAuthControllerImpl implements MobileAuthController {
    @Override
    public ResponseEntity<?> login(Map<String, String> credentials) {
        return null;
    }
}
