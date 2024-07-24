package petcare.app.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import petcare.app.domain.entity.Pet;

/** Repositorio de mascotas */
@Repository
public interface PetRepository extends CrudRepository<Pet, Long>, JpaRepository<Pet, Long> {

  /**
   * Obtiene una lista de mascotas asociadas a un cliente, dado su ID
   * 
   * @param clientId ID del cliente
   * @return List(Pet)
   */
  List<Pet> findByClientsIdOrderByAliveDesc(Long clientId, Pageable pageable);

  /**
   * Obtiene una lista de mascotas asociadas a un cliente, dado su documento nacional de identidad (NIF/NIE)
   * 
   * @param nationalIdDocument Documento nacional de identidad del cliente (NIF/NIE)
   * @param pageable           Sistema de paginación
   * @return List(Pet) - Lista con las mascotas encontradas
   */
  @Query("""
    SELECT p FROM Pet p
      JOIN p.clients c
      WHERE c.nationalIdDocument = :nationalIdDocument
      ORDER BY p.alive DESC
    """)
  List<Pet> findByClientNationalIdDocument(String nationalIdDocument, Pageable pageable);

}
