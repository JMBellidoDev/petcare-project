package petcare.app.domain.dto.simple;

import lombok.Data;

/** Dto con la información básica de un cliente o usuario de la aplicación */
@Data
public class ClientDtoSimple {

  /** ID autogenerado de un cliente o usuario */
  private Long id;

  /** Nombre real del usuario */
  private String name;

}
