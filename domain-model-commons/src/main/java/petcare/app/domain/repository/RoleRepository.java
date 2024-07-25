package petcare.app.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import petcare.app.domain.entity.security.Role;

/** Repositorio de roles */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

  /**
   * Busca un Role con el nombre aportado
   * 
   * @param name Nombre del Role
   * @return Role
   */
  Role findByName(String name);

}
