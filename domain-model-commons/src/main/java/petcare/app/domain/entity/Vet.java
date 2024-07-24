package petcare.app.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import petcare.app.domain.entity.security.AppUser;

@Entity
@Data
@Table(name = "vet")
@EqualsAndHashCode(callSuper = false)
@DiscriminatorValue("2")
public class Vet extends AppUser {

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
  @Column(name = "national_id_document", unique = true, length = 9)
  @Pattern(regexp = "[0-9KLMXYZ]\\d{7}[A-Z]")
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
  @Column(name = "registration_number", unique = true, length = 20)
  private String registrationNumber;

  /** Nombre real del veterinario */
  @Column(name = "name", length = 100)
  private String name;

  /** Entidad Veterinaria a la que pertenece */
  @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  private VetEntity vetEntity;

}
