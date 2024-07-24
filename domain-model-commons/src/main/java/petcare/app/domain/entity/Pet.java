package petcare.app.domain.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Mascota asociada con los clientes<br/>
 * Deberá ser un tipo de animal y pertenecer a una raza.<br/>
 */
@Entity
@Data
@Table(name = "pet")
public class Pet {

  /** ID autogenerado de una mascota */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** Número de chip o identificación animal */
  @Column(name = "chip_number", length = 15)
  private String chipNumber;

  /** Tipo de animal que se registra en el sistema */
  @Column(name = "type",
      columnDefinition = "varchar(20) check (type in ('dog', 'cat', 'horse', 'ferret', 'bird', 'rabbit'))")
  private String type;

  /** Raza del animal */
  @Column(name = "breed", length = 50)
  private String breed;

  /** Nombre */
  @Column(name = "name", length = 50)
  private String name;

  /** Fecha de nacimiento */
  @Column(name = "birthdate")
  private LocalDate birthdate;

  /** Indica si la mascota está viva o no */
  @Column(name = "alive", columnDefinition = "boolean default true")
  private Boolean alive = true;

  /** Indica si el animal está o no castrado */
  private Boolean castrated;

  /** Informes de visitas a un veterinario */
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pet")
  @JsonIgnore
  private List<Report> reports;

  /** Citas de visitas a un veterinario */
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pet")
  @JsonIgnore
  private List<Appointment> appointments;

  /** Listado de clientes de la aplicación, o usuarios, que están asociados con esta mascota */
  @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.LAZY,
      mappedBy = "pets")
  private List<Client> clients;

}
