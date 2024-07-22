package petcare.app.domain.dto.simple;

import lombok.Data;
import petcare.app.domain.dto.VetEntityDto;

/** Dto con la información básica de un veterinario */
@Data
public class VetDtoSimple {

  /** ID autogenerado de un veterinario */
  private Long id;

  /** Nombre real del veterinario */
  private String name;

  /** Entidad Veterinaria a la que pertenece */
  private VetEntityDto vetEntityDto;

}
