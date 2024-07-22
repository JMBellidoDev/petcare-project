package petcare.app.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import petcare.app.domain.entity.security.AppUser;

/**
 * Entidad o negocio veterinario. Se trata de cualquier tipo de clínica veterinaria, la cual se encargará de mantener el
 * acceso a la aplicación a todos sus veterinarios contratados
 */
@Entity
@Data
@Table(name = "vet_entity")
@EqualsAndHashCode(callSuper = false)
public class VetEntity extends AppUser {

  /**
   * CIF de la clínica veterinaria<br/>
   * Debe tener un formato de una letra, seguida de 7 dígitos numéricos y, por último, otra letra de control, de forma
   * que la primera se encuentre entre las siguientes: [ABCDEFGHJNPQRSTUVW]
   * 
   */
  @Pattern(regexp = "[ABCDEFGHJNPQRSTUVW]\\d{7}[A-Z]")
  @Column(name = "cif", unique = true, length = 9)
  private String cif;

  /** Nombre de la cĺinica */
  @Column(name = "name", length = 100)
  private String name;

  /** Dirección de la clínica */
  @Column(name = "address", length = 255)
  private String address;

  /** Número de teléfono de la clínica */
  @Column(name = "phone_number", unique = true, length = 9)
  @Pattern(regexp = "\\d{9}")
  private String phoneNumber;

}
