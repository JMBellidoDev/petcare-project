package petcare.app.security.users.utils;

/** Rutas del controlador */
public class Paths {

  /** Constructor privado para evitar inicialización */
  private Paths() {

  }

  /** Prefijo de las rutas referentes a creación y modificación de usuarios */
  public static final String USERS_PREFIX = "/users";

  /** Registro de clientes */
  public static final String REGISTER_CLIENT = "/register/client";

  /** Registro de veterinarios */
  public static final String REGISTER_VET = "/register/vet";

  /** Registro de entidades veterinarias */
  public static final String REGISTER_VET_ENTITY = "/register/vet-entity";

  /** Modificación de clientes */
  public static final String UPDATE_CLIENT = "/update/client/{id}";

  /** Modificación de veterinarios */
  public static final String UPDATE_VET = "/register/vet/{id}";

  /** Modificación de clientes */
  public static final String UPDATE_VET_ENTITY = "/register/vet-entity/{id}";
}
