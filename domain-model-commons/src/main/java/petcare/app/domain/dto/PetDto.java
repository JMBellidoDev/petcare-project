package petcare.app.domain.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

/**
 * Mascota asociada con los clientes<br/>
 * Deberá ser un tipo de animal y pertenecer a una raza.<br/>
 */
@Data
public class PetDto {

  /** ID autogenerado de una mascota */
  private Long id;

  /** Número de chip o identificación animal */
  private String chipNumber;

  /** Tipo de animal que se registra en el sistema */
  private String type;

  /** Raza del animal */
  private String breed;

  /** Nombre */
  private String name;

  /** Fecha de nacimiento */
  private LocalDate birthdate;

  /** Indica si la mascota está viva o no */
  private Boolean alive = true;

  /** Indica si el animal está o no castrado */
  private Boolean castrated;

  /** Lista de clientes */
  private List<ClientDto> clientsDto;

}
