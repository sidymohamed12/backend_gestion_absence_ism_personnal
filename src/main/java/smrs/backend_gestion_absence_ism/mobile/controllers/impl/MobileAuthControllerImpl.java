package smrs.backend_gestion_absence_ism.mobile.controllers.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import smrs.backend_gestion_absence_ism.mobile.controllers.MobileAuthController;
import smrs.backend_gestion_absence_ism.mobile.dto.request.LoginRequestDTO;

import java.util.Map;

@RestController
public class MobileAuthControllerImpl implements MobileAuthController {

    // private final AuthentificationService authentificationService;

    @Override
    public ResponseEntity<Map<String, Object>> login(@Valid LoginRequestDTO loginRequest) {
        // LoginResponseDto loginResponse = authentificationService.login(loginRequest);
        // return new ResponseEntity<>(RestResponse.response(HttpStatus.OK,
        // loginResponse, "LoginResponseDTO"),
        // HttpStatus.OK);
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> logout() {
        // authentificationService.logout();
        // return ResponseEntity.ok(Map.of("message", "Déconnexion réussie"));
        return null;
    }

}
