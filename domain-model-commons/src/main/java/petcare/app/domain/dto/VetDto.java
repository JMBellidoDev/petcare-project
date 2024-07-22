package petcare.app.domain.dto;

import lombok.Data;

@Data
public class VetDto {

  /** ID autogenerado de un veterinario */
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

  /**
   * Número de colegiado de un practicante de veterinaria.<br/>
   * Existe diversidad de formatos dentro de España, por lo que no se pondrá restricción de validación más allá de su
   * longitud, tales como:<br/>
   * <ul>
   * <li>Galicia: 15/V-6789</li>
   * <li>Andalucía: 41/12345-V</li>
   * <li>Alicante: 03C1234</li>
   * </ul>
   */
  private String registrationNumber;

  /** Nombre real del veterinario */
  private String name;

  /** Entidad Veterinaria a la que pertenece */
  private VetEntityDto vetEntityDto;

}
