package petcare.app.domain.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Cita que se apunta en la agenda de un cliente en conjunto con una mascota. Debe anotar, además, al veterinario que
 * tratará dicha cita
 */
@Entity
@Data
@Table(name = "appointment")
public class Appointment {

  /** ID autogenerado de una cita veterinaria */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** Cliente que traerá a la mascota */
  @ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id")
  private Client client;

  /** Mascota a tratar en la cita */
  @ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
  @JoinColumn(name = "pet_id")
  private Pet pet;

  /** Veterinario que tratará la cita */
  @ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
  @JoinColumn(name = "vet_id")
  @JsonIgnore
  private Vet vet;

  /** Fecha de la cita */
  @Column(name = "appointment_date")
  private LocalDateTime appointmentDate;

}
