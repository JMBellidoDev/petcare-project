package petcare.app.domain.dto.security;

import java.util.List;

import lombok.Data;

/** Usuario de la aplicación. Clase que contiene todos los datos referentes al acceso del usuario */
@Data
public abstract class AppUserDto {

  /** ID autogenerado del usuario */
  private Long id;

  /** Nombre de usuario o Username necesario para el login */
  private String username;

  /** Contraseña usada para el login del usuario */
  private String password;

  /** Indica si la cuenta del usuario está activa y se permite su login en la aplicación */
  private boolean enabled;

  /** Email del usuario */
  private String email;

  /** Lista de roles que posee el usuario */
  private List<RoleDto> roles;

}
