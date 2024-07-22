package petcare.app.domain.dto;

import lombok.Data;

/**
 * Entidad o negocio veterinario. Se trata de cualquier tipo de clínica veterinaria, la cual se encargará de mantener el
 * acceso a la aplicación a todos sus veterinarios contratados
 */
@Data
public class VetEntityDto {

  /** ID autogenerado de una entidad o negocio veterinario */
  private Long id;

  /**
   * CIF de la clínica veterinaria<br/>
   * Debe tener un formato de una letra, seguida de 7 dígitos numéricos y, por último, otra letra de control, de forma
   * que la primera se encuentre entre las siguientes: [ABCDEFGHJNPQRSTUVW]
   * 
   */
  private String cif;

  /** Nombre de la cĺinica */
  private String name;

  /** Dirección de la clínica */
  private String address;

  /** Número de teléfono de la clínica */
  private String phoneNumber;

}
