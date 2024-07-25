package petcare.app.security.oauth;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import petcare.app.security.users.utils.Paths;
import petcare.app.security.users.utils.RoleConstants;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  public UserDetailsService usuarioService;

  @Bean
  @Order(1)
  public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {

    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

    http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());

    http

        // Redirección a la página de login si no está autenticado desde el endpoint
        .exceptionHandling(exceptions -> exceptions
            .defaultAuthenticationEntryPointFor(new LoginUrlAuthenticationEntryPoint("/login"),
                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)))

        // Token de acceso JWT
        .oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()));

    return http.build();
  }

  @Bean
  @Order(2)
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

    http

        .authorizeHttpRequests(authorize -> authorize

            // La única ruta permitida para todos es la de login de usuarios
            .requestMatchers(HttpMethod.POST, Paths.USERS_PREFIX + Paths.REGISTER_CLIENT, "/login**")
            .permitAll()

            // Sólo puede modificar sus datos el mismo cliente
            .requestMatchers(HttpMethod.PUT, Paths.USERS_PREFIX + Paths.UPDATE_CLIENT)
            .hasRole(RoleConstants.CLIENT)

            // Los veterinarios modifican datos de clientes y de ellos mismos
            .requestMatchers(Paths.USERS_PREFIX + Paths.UPDATE_CLIENT, Paths.USERS_PREFIX + Paths.UPDATE_VET)
            .hasRole(RoleConstants.VET)

            // Las entidades veterinarias pueden crear y modificar datos del veterinario y modificar de usuario entidad
            .requestMatchers(Paths.USERS_PREFIX + Paths.REGISTER_VET, Paths.USERS_PREFIX + Paths.UPDATE_VET_ENTITY,
                Paths.USERS_PREFIX + Paths.UPDATE_VET, Paths.USERS_PREFIX + Paths.UPDATE_CLIENT)
            .hasRole(RoleConstants.VET_ENTITY)

            // Root puede y crear modificar datos de cualquier entidad
            .anyRequest()
            .authenticated()

        )

        .csrf(csrf -> csrf.disable())
        .formLogin(Customizer.withDefaults());

    return http.build();
  }

  @Bean
  public RegisteredClientRepository registeredClientRepository() {

    // Clientes que van a consumir la configuración de seguridad. Gateway + Angular
    RegisteredClient clientGateway = RegisteredClient
        .withId(UUID.randomUUID().toString())
        .clientId("client-gateway")
        .clientSecret("{noop}12345")

        // Configuración de autorización
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)

        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)

        // Redirecciones
        .redirectUri("http://127.0.0.1:8090/login/oauth2/code/client-gateway")
        .redirectUri("http://127.0.0.1:8090/authorized")
        .postLogoutRedirectUri("http://127.0.0.1:8090/logout")

        .scope("client")
        .scope("vet")
        .scope("vet_entity")
        .scope("root")
        .scope(OidcScopes.OPENID)
        .scope(OidcScopes.PROFILE)
        .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
        .build();

    RegisteredClient clientFront = RegisteredClient
        .withId(UUID.randomUUID().toString())
        .clientId("client-app-front")
        .clientSecret("{noop}123456")

        // Configuración de autorización
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)

        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)

        // Redirecciones
        .redirectUri("http://127.0.0.1:4200/login/oauth2/code/client-app-front")
        .redirectUri("http://127.0.0.1:4200/authorized")
        .postLogoutRedirectUri("http://127.0.0.1:4200/logout")

        .scope("client")
        .scope("vet")
        .scope("vet_entity")
        .scope(OidcScopes.OPENID)
        .scope(OidcScopes.PROFILE)
        .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
        .build();

    return new InMemoryRegisteredClientRepository(clientGateway, clientFront);
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource() {

    // Claves pública y privada (RSA)
    KeyPair keyPair = generateRsaKey();
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

    // Firma y almacenamiento del token
    RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return new ImmutableJWKSet<>(jwkSet);
  }

  private static KeyPair generateRsaKey() {

    KeyPair keyPair;

    // Generación de clave
    try {
      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(2048);

      keyPair = keyPairGenerator.generateKeyPair();

    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
    return keyPair;
  }

  @Bean
  public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
  }

  @Bean
  public AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder().build();
  }

}