package petcare.app.domain.dto;

import java.time.LocalDateTime;

import lombok.Data;
import petcare.app.domain.dto.simple.ClientDtoSimple;
import petcare.app.domain.dto.simple.PetDtoSimple;
import petcare.app.domain.dto.simple.VetDtoSimple;

/**
 * Cita que se apunta en la agenda de un cliente en conjunto con una mascota. Debe anotar, además, al veterinario que
 * tratará dicha cita
 */
@Data
public class AppointmentDto {

  /** ID autogenerado de una cita veterinaria */
  private Long id;

  /** Mascota a tratar en la cita */
  private PetDtoSimple petDtoSimple;

  /** Cliente que traerá el animal a la cita */
  private ClientDtoSimple clientDtoSimple;

  /** Veterinario que atenderá la cita */
  private VetDtoSimple vetDtoSimple;

  /** Fecha de la cita */
  private LocalDateTime appointmentDate;

}
