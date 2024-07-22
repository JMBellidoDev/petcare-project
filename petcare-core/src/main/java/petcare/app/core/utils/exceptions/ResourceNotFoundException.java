package petcare.app.core.utils.exceptions;

/** Excepción lanzada cuando un recurso no es encontrado en el sistema */
public class ResourceNotFoundException extends Exception {

  /** SerialVersionUID */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor con mensaje
   * 
   * @param message Mensaje de error
   */
  public ResourceNotFoundException(String message) {
    super(message);
  }

}
