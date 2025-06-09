package smrs.backend_gestion_absence_ism.security;

import lombok.RequiredArgsConstructor;
import smrs.backend_gestion_absence_ism.data.entities.Utilisateur;
import smrs.backend_gestion_absence_ism.data.repositories.UtilisateurRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

        private final UtilisateurRepository utilisateurRepository;

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                Utilisateur utilisateur = utilisateurRepository
                                .findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "Utilisateur non trouvé avec l'email: " + email));

                List<GrantedAuthority> authorities = List.of(
                                new SimpleGrantedAuthority("ROLE_" + utilisateur.getRole().name()));

                return User.builder()
                                .username(utilisateur.getEmail())
                                .password(utilisateur.getMotDePasse())
                                // .roles(utilisateur.getRole().name())
                                .authorities(authorities)
                                .build();
        }
}