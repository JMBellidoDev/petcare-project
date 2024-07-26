package petcare.app.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  @Autowired
  private JwtAuthenticationFilter authenticationFilter;

  @Bean
  SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {

    http
        .authorizeExchange(authHttp -> authHttp

            // Permiso a todos los usuarios
            .pathMatchers("/login", "/users/register/client")
            .permitAll()

            // Permiso de consulta de datos
            .pathMatchers(HttpMethod.GET, "/api/**")
            .hasAnyRole("CLIENT", "VET", "VET_ENTITY", "ROOT")

            // Permisos de almacenamiento, modificación y eliminación datos y modificación de veterinarios
            .pathMatchers(HttpMethod.POST, "/api/**")
            .hasAnyRole("VET", "VET_ENTITY", "ROOT")

            .pathMatchers(HttpMethod.PUT, "/api/**")
            .hasAnyRole("VET", "VET_ENTITY", "ROOT")

            .pathMatchers(HttpMethod.DELETE, "/api/**")
            .hasAnyRole("VET", "VET_ENTITY", "ROOT")

            .pathMatchers("/users/update/vet/**")
            .hasAnyRole("VET", "VET_ENTITY", "ROOT")

            // Permisos de creación de veterinarios y modificación de entidades
            .pathMatchers("/users/register/vet", "/users/update/vet-entity/**")
            .hasAnyRole("VET_ENTITY", "ROOT")

            // Permisos de creación de entidades veterinarias
            .pathMatchers("/users/register/vet-entity")
            .hasRole("ROOT")

            .anyExchange()
            .authenticated()

        )

        // Deshabilitando el csrf para producción

        .addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        .csrf(csrf -> csrf
            .disable()

            // Cliente
            .oauth2Client(Customizer.withDefaults())

            // Resource Server
            .oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults())));

    return http.build();
  }

}
