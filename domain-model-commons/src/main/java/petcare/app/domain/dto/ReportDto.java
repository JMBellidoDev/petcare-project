package petcare.app.domain.dto;

import java.time.LocalDate;

import lombok.Data;
import petcare.app.domain.dto.simple.VetDtoSimple;

/**
 * Informe de visita de un animal a una clínica veterinaria, donde se almacena la información básica necesaria que debe
 * conocer el cliente
 */
@Data
public class ReportDto {

  /** ID autogenerado de un informe de visita clínica */
  private Long id;

  /** Razón o motivo de la visita */
  private String reason;

  /** Diagnóstico clínico del animal */
  private String diagnosis;

  /** Tratamiento / Medicación impuesta por el / la profesional */
  private String treatment;

  /** Fecha de emisión del informe */
  private LocalDate reportDate;

  /** Perro sobre el que se establece el informe de visita clínica */
  private PetDto petDto;

  /** Veterinario que realiza el informe de visita clínica */
  private VetDtoSimple vetDtoSimple;

}
