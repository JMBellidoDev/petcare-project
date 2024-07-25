package petcare.app.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .authorizeHttpRequests(authHttp -> authHttp

            .anyRequest()
            .permitAll()
        // Permiso a todos los usuarios
        /*
         * .requestMatchers(HttpMethod.GET, "/authorized") .permitAll()
         * 
         * // Permiso a todos los usuarios autenticados .requestMatchers(HttpMethod.GET, "/list")
         * .hasAnyAuthority("SCOPE_user", "SCOPE_admin")
         * 
         * // Permiso a todos los usuarios con role de admin .requestMatchers(HttpMethod.POST, "/create")
         * .hasAuthority("SCOPE_admin") .anyRequest() .authenticated()
         */

        )

        // Deshabilitando el csrf para producción
        .csrf(csrf -> csrf.disable())

        // Manejo del estado desde el token, no desde la sesión
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // Login
        .oauth2Login(login -> login.loginPage("/oauth2/authorization/client-app-front"))

        // Cliente
        .oauth2Client(Customizer.withDefaults())

        // Resource Server
        .oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()));

    return http.build();
  }

}
