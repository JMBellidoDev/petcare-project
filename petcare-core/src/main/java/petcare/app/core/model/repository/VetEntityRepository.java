package petcare.app.core.model.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import petcare.app.domain.entity.VetEntity;

/** Repositorio de acceso a los datos de una entidad veterinaria */
public interface VetEntityRepository extends CrudRepository<VetEntity, Long>, JpaRepository<VetEntity, Long> {

  /**
   * Busca una entidad veterinaria que contenga el nombre aportado
   * 
   * @param name     Nombre
   * @param pageable Sistema de paginación
   * @return List(VetEntity) - Lista con todas las entidades veterinarias que cumplan con el nombre
   */
  List<VetEntity> findByNameContaining(String name, Pageable pageable);

}
