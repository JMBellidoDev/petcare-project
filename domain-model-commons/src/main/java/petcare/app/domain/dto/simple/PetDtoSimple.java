package petcare.app.domain.dto.simple;

import lombok.Data;

/** Dto con la información básica de una mascota */
@Data
public class PetDtoSimple {

  /** ID autogenerado de una mascota */
  private Long id;

  /** Nombre */
  private String name;

  /** Indica si la mascota está viva o no */
  private Boolean alive;

}
