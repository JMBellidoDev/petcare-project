package petcare.app.core.model.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import petcare.app.domain.entity.Report;

/** Repositorio de informes veterinarios */
public interface ReportRepository extends CrudRepository<Report, Long>, JpaRepository<Report, Long> {

  /**
   * Consulta de todos los informes clínicos de una mascota concreta
   * 
   * @param petId    ID de la mascota
   * @param pageable Sistema de paginación
   * @return List(Report) - Lista con todos los informes clínicos solicitados
   */
  @Query("SELECT r FROM Report r WHERE r.pet.id = :petId ORDER BY r.reportDate DESC")
  List<Report> findByPetId(@Param("petId") Long petId, Pageable pageable);

  /**
   * Consulta de todos los informes clínicos de todas las mascotas dado el ID del cliente
   * 
   * @param clientId ID del cliente cuyas mascotas se van a buscar
   * @param pageable Sistema de paginación
   * @return List(Report) - Lista con todos los informes clínicos solicitados
   */
  @Query("""
    SELECT r FROM Report r
      JOIN r.pet p
      JOIN p.clients c
      WHERE c.id = :clientId
      ORDER BY r.reportDate DESC
    """)
  List<Report> findByClientId(@Param("clientId") Long clientId, Pageable pageable);

  /**
   * Consulta de todos los informes clínicos de todas las mascotas dado el documento nacional de identidad del cliente
   * 
   * @param nationalIdDocument NIF/NIE del cliente
   * @param pageable           Sistema de paginación
   * @return List(Report) - Lista con todos los informes clínicos solicitados
   */
  @Query("""
    SELECT r FROM Report r
      JOIN r.pet p
      JOIN p.clients c
      WHERE c.nationalIdDocument = :nationalIdDocument
      ORDER BY r.reportDate DESC
    """)
  List<Report> findByClientNationalIdDocument(@Param("nationalIdDocument") String nationalIdDocument,
      Pageable pageable);

  /**
   * Consulta de todos los informes clínicos relacionados con el veterinario aportado
   * 
   * @param vetId    ID del veterinario
   * @param pageable Sistema de paginación
   * @return List(Report) - Lista con todos los informes clínicos solicitados
   */
  @Query("SELECT r FROM Report r WHERE r.vet.id = :vetId ORDER BY r.reportDate DESC")
  List<Report> findByVetId(@Param("vetId") Long vetId, Pageable pageable);

  /**
   * Consulta de todos los informes clínicos relacionados con la entidad veterinaria
   * 
   * @param vetEntityId ID de la entidad veterinaria
   * @param pageable    Sistema de paginación
   * @return List(Report) - Lista con todos los informes clínicos solicitados
   */
  @Query("""
    SELECT r FROM Report r
      JOIN r.vet v
      WHERE v.vetEntity.id = :vetEntityId
      ORDER BY r.reportDate DESC
    """)
  List<Report> findByVetEntityId(@Param("vetEntityId") Long vetEntityId, Pageable pageable);

}
