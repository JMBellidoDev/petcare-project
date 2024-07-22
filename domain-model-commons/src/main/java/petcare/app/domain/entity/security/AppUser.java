package petcare.app.domain.entity.security;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

/** Usuario de la aplicación. Clase que contiene todos los datos referentes al acceso del usuario */
@Entity
@Table(name = "app_user")
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AppUser {

  /** ID autogenerado del usuario */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** Nombre de usuario o Username necesario para el login */
  @Column(unique = true, length = 20)
  private String username;

  /** Contraseña usada para el login del usuario */
  @Column(length = 60)
  private String password;

  /** Indica si la cuenta del usuario está activa y se permite su login en la aplicación */
  private boolean enabled;

  /** Email del usuario */
  @Column(length = 80)
  private String email;

  /** Lista de roles que posee el usuario */
  @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
  @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "app_user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"),
      uniqueConstraints = { @UniqueConstraint(columnNames = { "app_user_id", "role_id" }) })
  private List<Role> roles;

}
