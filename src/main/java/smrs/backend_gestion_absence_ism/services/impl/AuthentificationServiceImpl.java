package smrs.backend_gestion_absence_ism.services.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import smrs.backend_gestion_absence_ism.data.entities.Absence;
import smrs.backend_gestion_absence_ism.data.entities.Admin;
import smrs.backend_gestion_absence_ism.data.entities.Etudiant;
import smrs.backend_gestion_absence_ism.data.entities.Utilisateur;
import smrs.backend_gestion_absence_ism.data.entities.Vigile;
import smrs.backend_gestion_absence_ism.data.enums.Role;
import smrs.backend_gestion_absence_ism.data.repositories.AbsenceRepository;
import smrs.backend_gestion_absence_ism.data.repositories.AdminRepository;
import smrs.backend_gestion_absence_ism.data.repositories.EtudiantRepository;
import smrs.backend_gestion_absence_ism.data.repositories.UtilisateurRepository;
import smrs.backend_gestion_absence_ism.data.repositories.VigileRepository;
import smrs.backend_gestion_absence_ism.mobile.dto.request.LoginRequestDTO;
import smrs.backend_gestion_absence_ism.mobile.dto.response.AbsenceMobileDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.LoginResponseDto;
import smrs.backend_gestion_absence_ism.mobile.dto.response.UtilisateurMobileDto;
import smrs.backend_gestion_absence_ism.mobile.mapper.AbsenceMobileMapper;
import smrs.backend_gestion_absence_ism.mobile.mapper.AuthentificationMapper;
import smrs.backend_gestion_absence_ism.security.JwtUtils;
import org.springframework.security.core.Authentication;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthentificationServiceImpl {

        private final AuthenticationManager authenticationManager;
        private final JwtUtils jwtUtils;
        private final UtilisateurRepository utilisateurRepository;
        private final EtudiantRepository etudiantRepository;
        private final AdminRepository adminRepository;
        private final VigileRepository vigileRepository;
        private final AbsenceRepository absenceRepository;
        private final AuthentificationMapper authentificationMapper;

        public LoginResponseDto authenticateUser(LoginRequestDTO loginRequest, HttpServletResponse response) {
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                                                loginRequest.getMotDePasse()));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtUtils.generateJwtToken(authentication);
                log.info("JWT généré avec succès pour l'utilisateur: {}",
                                loginRequest.getEmail());
                log.info("JWT token (premiers caractères): {}", jwt.substring(0, Math.min(20,
                                jwt.length())) + "...");
                // gérer les cookies
                ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwt)
                                .httpOnly(true)
                                .secure(false) // FALSE pour développement HTTP
                                .path("/")
                                .maxAge(Duration.ofHours(1))
                                .sameSite("Lax") // Changé de Strict à Lax pour le développement
                                .build();

                response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
                log.info("Cookie JWT ajouté à la réponse: {}", jwtCookie.toString());
                Utilisateur utilisateur = utilisateurRepository.findByEmail(loginRequest.getEmail())
                                .orElseThrow(() -> new RuntimeException(
                                                "Utilisateur non trouvé après authentification."));

                UtilisateurMobileDto utilisateurMobileDto = null;
                String realId = null;
                String redirectEndpoint = "";

                if (utilisateur.getRole() == Role.ETUDIANT) {
                        Etudiant etudiant = etudiantRepository.findByUtilisateurId(utilisateur.getId())
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Etudiant non trouvé pour cet utilisateur."));
                        utilisateurMobileDto = authentificationMapper.etudiantToUtilisateurMobileDto(etudiant);
                        realId = etudiant.getId();
                        redirectEndpoint = "";
                        LocalDateTime ilYADepuis30Jours = LocalDateTime.now().minusDays(30);
                        List<Absence> absencesRecentes = absenceRepository.findByEtudiantIdAndCreatedAtBetween(
                                        realId, ilYADepuis30Jours, LocalDateTime.now());
                        List<AbsenceMobileDto> absencesDto = absencesRecentes.stream()
                                        .sorted(Comparator.comparing(Absence::getCreatedAt).reversed())
                                        .limit(5)
                                        .map(AbsenceMobileMapper.INSTANCE::toMobileDto)
                                        .toList();
                        utilisateurMobileDto.setAbsences(absencesDto);
                } else if (utilisateur.getRole() == Role.ADMIN) {
                        Admin admin = adminRepository.findByUtilisateurId(utilisateur.getId())
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Admin non trouvé pour cet utilisateur."));
                        utilisateurMobileDto = authentificationMapper.adminToUtilisateurMobileDto(admin);
                        realId = admin.getId();
                        redirectEndpoint = "";
                } else if (utilisateur.getRole() == Role.VIGILE) {
                        Vigile vigile = vigileRepository.findByUtilisateurId(utilisateur.getId())
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Vigile non trouvé pour cet utilisateur."));
                        utilisateurMobileDto = authentificationMapper.vigileToUtilisateurMobileDto(vigile);
                        realId = vigile.getId();
                        redirectEndpoint = "";
                }

                if (utilisateurMobileDto == null) {
                        // Fallback if role-specific mapping fails or is not found, map basic user info
                        utilisateurMobileDto = authentificationMapper.utilisateurToUtilisateurMobileDto(utilisateur);
                }

                return LoginResponseDto.builder()
                                .token(jwt)
                                .realId(realId) // The ID of the specific role entity (Etudiant, Admin, Vigile)
                                .utilisateur(utilisateurMobileDto)
                                .redirectEndpoint(redirectEndpoint)
                                .build();
        }

        public void logout(HttpServletResponse response) {
                // Crée un cookie vide qui expire immédiatement
                ResponseCookie expiredCookie = ResponseCookie.from("jwt", "")
                                .httpOnly(true)
                                .secure(false)
                                .path("/")
                                .maxAge(Duration.ZERO)
                                .sameSite("Lax")
                                .build();

                response.addHeader(HttpHeaders.SET_COOKIE, expiredCookie.toString());
                log.info("Cookie JWT supprimé");
                SecurityContextHolder.clearContext();
        }

        public LoginResponseDto loadUserInfo(String email) {
                Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));

                UtilisateurMobileDto utilisateurMobileDto = null;
                String realId = null;
                String redirectEndpoint = "";

                if (utilisateur.getRole() == Role.ETUDIANT) {
                        Etudiant etudiant = etudiantRepository.findByUtilisateurId(utilisateur.getId())
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Etudiant non trouvé pour cet utilisateur."));
                        utilisateurMobileDto = authentificationMapper.etudiantToUtilisateurMobileDto(etudiant);
                        realId = etudiant.getId();
                        redirectEndpoint = "";
                        LocalDateTime ilYADepuis30Jours = LocalDateTime.now().minusDays(30);
                        List<Absence> absencesRecentes = absenceRepository.findByEtudiantIdAndCreatedAtBetween(
                                        realId, ilYADepuis30Jours, LocalDateTime.now());
                        List<AbsenceMobileDto> absencesDto = absencesRecentes.stream()
                                        .sorted(Comparator.comparing(Absence::getCreatedAt).reversed())
                                        .limit(5)
                                        .map(AbsenceMobileMapper.INSTANCE::toMobileDto)
                                        .toList();
                        utilisateurMobileDto.setAbsences(absencesDto);
                } else if (utilisateur.getRole() == Role.ADMIN) {
                        Admin admin = adminRepository.findByUtilisateurId(utilisateur.getId())
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Admin non trouvé pour cet utilisateur."));
                        utilisateurMobileDto = authentificationMapper.adminToUtilisateurMobileDto(admin);
                        realId = admin.getId();
                        redirectEndpoint = "";
                } else if (utilisateur.getRole() == Role.VIGILE) {
                        Vigile vigile = vigileRepository.findByUtilisateurId(utilisateur.getId())
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Vigile non trouvé pour cet utilisateur."));
                        utilisateurMobileDto = authentificationMapper.vigileToUtilisateurMobileDto(vigile);
                        realId = vigile.getId();
                        redirectEndpoint = "";
                }

                if (utilisateurMobileDto == null) {
                        utilisateurMobileDto = authentificationMapper.utilisateurToUtilisateurMobileDto(utilisateur);
                }

                return LoginResponseDto.builder()
                                .token(null) // Pas besoin de renvoyer le token ici
                                .realId(realId)
                                .utilisateur(utilisateurMobileDto)
                                .redirectEndpoint(redirectEndpoint)
                                .build();
        }

}
