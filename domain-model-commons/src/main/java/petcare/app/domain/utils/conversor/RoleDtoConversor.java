package petcare.app.domain.utils.conversor;

import petcare.app.domain.dto.security.RoleDto;
import petcare.app.domain.entity.security.Role;

/** Conversor de entidades entre un objeto de la clase Role y un objeto de la clase RoleDto */
public class RoleDtoConversor {

  /** Constructor privado para evitar inicialización. Clase de métodos estáticos */
  private RoleDtoConversor() {

  }

  /**
   * Conversor de un objeto Role a un objeto RoleDto
   * 
   * @param role Role a convertir
   * @return RoleDto
   */
  public static RoleDto toRoleDto(Role role) {

    RoleDto roleDto = new RoleDto();

    roleDto.setId(role.getId());
    roleDto.setName(role.getName());

    return roleDto;
  }

  /**
   * Conversor de un objeto RoleDto a un objeto Role
   * 
   * @param roleDto RoleDto a convertir
   * @return Role
   */
  public static Role toRole(RoleDto roleDto) {

    Role role = new Role();

    role.setId(roleDto.getId());
    role.setName(roleDto.getName());

    return role;
  }

}
