package petcare.app.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import petcare.app.domain.entity.VetEntity;

/** Repositorio de acceso a los datos de una entidad veterinaria */
@Repository
public interface VetEntityRepository extends CrudRepository<VetEntity, Long>, JpaRepository<VetEntity, Long> {

  /**
   * Busca una entidad veterinaria dado su username
   * 
   * @param username Username
   * @return Optional(Client) - Un objeto del tipo Optional que podrá contener la entidad veterinaria en caso de ser
   *         encontrada
   */
  Optional<VetEntity> findByUsername(String username);

  /**
   * Busca una entidad veterinaria que contenga el nombre aportado
   * 
   * @param name     Nombre
   * @param pageable Sistema de paginación
   * @return List(VetEntity) - Lista con todas las entidades veterinarias que cumplan con el nombre
   */
  List<VetEntity> findByNameContaining(String name, Pageable pageable);

}
