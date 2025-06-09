package smrs.backend_gestion_absence_ism.services.impl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
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
public class AuthentificationServiceImpl {

        private final AuthenticationManager authenticationManager;
        private final JwtUtils jwtUtils;
        private final UtilisateurRepository utilisateurRepository;
        private final EtudiantRepository etudiantRepository;
        private final AdminRepository adminRepository;
        private final VigileRepository vigileRepository;
        private final AbsenceRepository absenceRepository;
        private final AuthentificationMapper authentificationMapper;

        public LoginResponseDto authenticateUser(LoginRequestDTO loginRequest) {
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                                                loginRequest.getMotDePasse()));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtUtils.generateJwtToken(authentication);

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

        public void logout() {
                // For JWT, logout is primarily client-side by discarding the token.
                // Server-side, we can clear the SecurityContext.
                SecurityContextHolder.clearContext();
                // If you had a token blacklist or revoked tokens, you would implement that
                // here.
        }

}
