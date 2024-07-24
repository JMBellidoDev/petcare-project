package petcare.app.domain.dto;

import java.time.LocalDate;

import lombok.Data;

/**
 * Representa un cliente de la aplicación. <br/>
 * El término cliente hace referencia al concepto de consumo de la aplicación, es decir, es un usuario que usará la
 * aplicación
 */
@Data
public class ClientDto {

  /** ID autogenerado de un cliente o usuario */
  private Long id;

  /**
   * Documento de identidad nacional. Deberá ser un NIF o un NIE.<br/>
   * <ul>
   * <li>En caso de ser un NIF de una persona mayor de 14 años, tendrá formato de 8 dígitos seguidos de una letra de
   * control</li>
   * 
   * <li>Si se trata de un NIF de una persona menor de 14 años, tendrá formato de una letra 'K', seguida de 7 dígitos y,
   * por último, una letra de control</li>
   * 
   * <li>Si se trata de un extranjero con documento de identificación nacional, tendrá un formato de una letra, seguida
   * de 7 dígitos y, por último, una letra de control, de forma que la primera letra sea K, L, M, X, Y o Z</li>
   * </ul>
   */
  private String nationalIdDocument;

  /** Nombre real del usuario */
  private String name;

  /** Fecha de nacimiento */
  private LocalDate birthdate;

  /** Dirección del usuario */
  private String address;

  /** Número de teléfono del usuario */
  private String phoneNumber;

}