package petcare.app.domain.entity.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Role que podrá poseer un usuario. Este será:
 * <ul>
 * <li>ROLE_USER: Usuario consumidor de la aplicación</li>
 * <li>ROLE_VET: Veterinario. Principalmente, se encargará de gestionar las asociaciones entre clientes y mascotas,
 * además de actualizar informes</li>
 * <li>ROLE_VET_ENTITY: Entidad o clínica veterinaria. Principalmente, será la encargada de gestionar a sus cuentas de
 * veterinario</li>
 * <li>ROLE_ROOT: Developers y encargados de la gestión y mantenimiento de la aplicación</li>
 * </ul>
 */
@Entity
@Table(name = "role")
@Data
public class Role {

  /** ID autogenerado en la base de datos */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** Nombre del role. Será una de las opciones detalladas en la documentación de la clase */
  @Column(unique = true, length = 30)
  private String name;

}
