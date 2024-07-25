package petcare.app.security.users.utils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

/** Constantes de Roles. Incluye todos los nombres de Roles del sistema */
public class RoleConstants {

  /** Constructor privado para evitar inicialización */
  private RoleConstants() {

  }

  /** User */
  public static final String CLIENT = "CLIENT";

  /** Vet */
  public static final String VET = "VET";

  /** Vet Entity */
  public static final String VET_ENTITY = "VET_ENTITY";

  /** Root */
  public static final String ROOT = "ROOT";

  /** Role User */
  public static final String ROLE_CLIENT = "ROLE_CLIENT";

  /** Role Vet */
  public static final String ROLE_VET = "ROLE_VET";

  /** Role Vet Entity */
  public static final String ROLE_VET_ENTITY = "ROLE_VET_ENTITY";

  /** Role Root */
  public static final String ROLE_ROOT = "ROLE_ROOT";

  /** SimpleGrantedAuthority - USER */
  public static final SimpleGrantedAuthority USER_AUTHORITY = new SimpleGrantedAuthority(ROLE_CLIENT);

  /** SimpleGrantedAuthority - VET */
  public static final SimpleGrantedAuthority VET_AUTHORITY = new SimpleGrantedAuthority(ROLE_VET);

  /** SimpleGrantedAuthority - VET ENTITY */
  public static final SimpleGrantedAuthority VET_ENTITY_AUTHORITY = new SimpleGrantedAuthority(ROLE_VET_ENTITY);

  /** SimpleGrantedAuthority - ROOT */
  public static final SimpleGrantedAuthority ROOT_AUTHORITY = new SimpleGrantedAuthority(ROLE_ROOT);
}
