package petcare.app.domain.dto.security;

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
@Data
public class RoleDto {

  /** ID autogenerado en la base de datos */
  private Long id;

  /** Nombre del role. Será una de las opciones detalladas en la documentación de la clase */
  private String name;

}
