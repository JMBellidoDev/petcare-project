package petcare.app.domain.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * Informe de visita de un animal a una clínica veterinaria, donde se almacena la información básica necesaria que debe
 * conocer el cliente
 */
@Entity
@Data
public class Report {

  /** ID autogenerado de un informe de visita clínica */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** Razón o motivo de la visita */
  @Column(name = "reason", columnDefinition = "text")
  private String reason;

  /** Diagnóstico clínico del animal */
  @Column(name = "diagnosis", columnDefinition = "text")
  private String diagnosis;

  /** Tratamiento / Medicación impuesta por el / la profesional */
  @Column(name = "treatment", columnDefinition = "text")
  private String treatment;

  /** Fecha de emisión del informe */
  @Column(name = "report_date")
  private LocalDate reportDate;

  /** Veterinarioque realiza el informe */
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
  private Vet vet;

  /** Mascota sobre la cual está el informe realizado */
  @JsonIgnore
  @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
  private Pet pet;
}
