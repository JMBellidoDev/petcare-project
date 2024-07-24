package petcare.app.domain.utils;

/** Clase de utilidad que contiene todos los mensajes de error */
public class ExceptionMessages {

  /** Constructor privado para evitar inicialización */
  private ExceptionMessages() {

  }

  /** Mensaje de error de cita no encontrada dado su ID */
  public static final String APPOINTMENT_NOT_FOUND_BY_ID = "No se pudo encontrar la cita veterinaria con ID: %d";

  /** Mensaje de error de veterinario no encontrado dado su ID */
  public static final String VET_NOT_FOUND_BY_ID = "No se pudo encontrar el veterinario con ID: %d";

  /** Mensaje de error de cliente no encontrado dado su ID */
  public static final String CLIENT_NOT_FOUND_BY_ID = "No se pudo encontrar el cliente con ID: %d";

  /** Mensaje de error de entidad veterinaria no encontrada dado su ID */
  public static final String VET_ENTITY_NOT_FOUND_BY_ID = "No se pudo encontrar la entidad veterinaria con ID: %d";

  /** Mensaje de error de cliente no encontrado */
  public static final String CLIENT_NOT_FOUND_BY_NATIONAL_ID_DOCUMENT = "No se pudo encontrar el cliente con NIF/NIE: %s";

  /** Mensaje de error de mascota no encontrado dado su ID */
  public static final String PET_NOT_FOUND_BY_ID = "No se pudo encontrar la mascota con ID: %d";

  /** Mensaje de error de informe no encontrado dado su ID */
  public static final String REPORT_NOT_FOUND_BY_ID = "No se pudo encontrar el informe con ID: %d";

}
