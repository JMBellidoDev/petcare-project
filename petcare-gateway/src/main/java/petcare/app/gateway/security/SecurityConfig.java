package petcare.app.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  SecurityWebFilterChain configure(ServerHttpSecurity http) throws Exception {

    http
        .authorizeExchange(authHttp -> authHttp

            .anyExchange()
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

        // Cliente
        .oauth2Client(Customizer.withDefaults())

        // Resource Server
        .oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()));

    return http.build();
  }

}
