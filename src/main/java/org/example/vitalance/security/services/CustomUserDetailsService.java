package org.example.vitalance.security.services;
import org.example.vitalance.entidades.User;

import org.example.vitalance.repositorios.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

/**
 * Busca al usuario en la BD por su username.
 * Si no existe → lanza excepción.
 * Convierte sus roles en GrantedAuthority.
 * Devuelve un UserDetails que Spring Security usará para:
 * - Verificar la contraseña al hacer login.
 * - Saber qué roles/authorities tiene para autorización (@PreAuthorize, .hasRole(), etc.).
 * ------------------
 * Su propósito principal es cargar los detalles de un usuario a partir de un identificador,
 * que generalmente es el nombre de usuario (username).
 * Es usado por JwtRequestFilter
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        //Buscar por correoUser(que es "username" en la vida real)
        User user = userRepository.findByCorreoUser(correo)
                .orElseThrow(() -> new UsernameNotFoundException("User not found" + correo));

        //Como tu User tiene un solo Role(ManyToOne)
        Set<GrantedAuthority> authorities = Collections.singleton(
                //ROLE_:para usar "hasRole" o "hasAnyRole"
                new SimpleGrantedAuthority("ROLE_"+user.getRole().getNombreRole())
        );
        //Set<GrantedAuthority> authorities = user.getRoles().stream()
          //      .map(role -> new SimpleGrantedAuthority(role.getName()))
            //    .collect(Collectors.toSet());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getCorreoUser())   // lo que Spring reconocerá como username
                .password(user.getPasswordUser())     // tu campo de contraseña
                .authorities(authorities)             // rol convertido en autoridad
                .accountLocked(!user.getActivoUser()) // si activoUser es false, lo bloquea
                .build();

        //return org.springframework.security.core.userdetails.User
          //      .withUsername(user.getUsername())
            //    .password(user.getPassword())
              //  .authorities(authorities)
                //.build();
    }
}
