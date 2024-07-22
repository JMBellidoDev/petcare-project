package petcare.app.domain.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import petcare.app.domain.entity.security.AppUser;

/**
 * Representa un cliente de la aplicación. <br/>
 * El término cliente hace referencia al concepto de consumo de la aplicación, es decir, es un usuario que usará la
 * aplicación
 */
@Entity
@Data
@Table(name = "client")
@EqualsAndHashCode(callSuper = false)
public class Client extends AppUser {

  /**
   * Documento de identidad nacional. Deberá ser un NIF o un NIE.<br/>
   * <ul>
   * <li>En caso de ser un NIF de una persona mayor de 14 años, tendrá formato de 8 dígitos seguidos de una letra de
   * control</li>
   * 
   * <li>Si se trata de un NIF de una persona menor de 14 años, tendrá formato de una letra 'K', seguida de 7 dígitos y,
   * por último, una letra de control</li>
   * 
   * <li>Si se trata de un extranjero con documento de identificación nacional, tendrá un formato de una letra, seguida
   * de 7 dígitos y, por último, una letra de control, de forma que la primera letra sea K, L, M, X, Y o Z</li>
   * </ul>
   */
  @Column(name = "national_id_document", unique = true, length = 9)
  @Pattern(regexp = "[0-9KLMXYZ]\\d{7}[A-Z]")
  private String nationalIdDocument;

  /** Nombre real del usuario */
  @Column(name = "name", length = 100)
  private String name;

  /** Fecha de nacimiento */
  @Column(name = "birthdate")
  @Temporal(TemporalType.DATE)
  private LocalDate birthdate;

  /** Dirección del usuario */
  @Column(name = "address", length = 100)
  private String address;

  /** Número de teléfono del usuario */
  @Column(name = "phone_number", length = 9)
  @Pattern(regexp = "\\d{9}")
  private String phoneNumber;

  /**
   * Lista de mascotas asociadas con el cliente o usuario. Se trata de todos aquellos animales a los que tiene acceso en
   * la aplicación (Es decir, puede ver la información relacionada con los animales)
   */
  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
  @JoinTable(name = "client_pets", joinColumns = @JoinColumn(name = "client_id"),
      inverseJoinColumns = @JoinColumn(name = "pet_id"),
      uniqueConstraints = { @UniqueConstraint(columnNames = { "pet_id", "client_id" }) })
  @JsonIgnore
  private List<Pet> pets;

  /** Citas de visitas a un veterinario */
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
  @JsonIgnore
  private List<Appointment> appointments;

}
