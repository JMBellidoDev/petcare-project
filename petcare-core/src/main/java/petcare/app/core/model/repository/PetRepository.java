package petcare.app.core.model.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import petcare.app.domain.entity.Pet;

/** Repositorio de mascotas */
public interface PetRepository extends CrudRepository<Pet, Long>, JpaRepository<Pet, Long> {

  /**
   * Obtiene una lista de mascotas asociadas a un cliente
   * 
   * @param clientId ID del cliente
   * @return List(Pet)
   */
  List<Pet> findByClientsIdOrderByAliveDesc(Long clientId, Pageable pageable);

  @Query("""
    SELECT p FROM Pet p
      JOIN p.clients c
      WHERE c.nationalIdDocument = :nationalIdDocument
      ORDER BY p.alive DESC
    """)
  List<Pet> findByClientNationalIdDocument(String nationalIdDocument, Pageable pageable);

}
