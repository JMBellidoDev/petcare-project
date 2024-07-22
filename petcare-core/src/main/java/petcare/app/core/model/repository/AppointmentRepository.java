package petcare.app.core.model.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import petcare.app.domain.entity.Appointment;

/**
 * Repositorio de acceso a los datos de una cita veterinaria.<br/>
 * 
 * Todas las citas obtenidas mediante consultas serán posteriores a la hora actual, puesto que la gestión de la
 * aplicación implica que, pasada la hora de la cita (con un cierto margen) debe generarse un informe con la visita del
 * día actual, o ninguno en caso de no asistencia. Sin embargo, no se podrán buscar citas anteriores al momento actual.
 * 
 */
public interface AppointmentRepository extends CrudRepository<Appointment, Long>, JpaRepository<Appointment, Long> {

  /**
   * Realiza una búsqueda de todas las citas próximas que tiene alguna de las mascotas asociadas con el cliente dado
   * 
   * @param clientId ID del cliente
   * @param pageable Sistema de paginación
   * @return List(Appointment) - Lista con todas las citas que cumplen la condición establecida
   */
  @Query("""
    SELECT a FROM Appointment a
    JOIN a.pet p
    JOIN p.clients c
    WHERE c.id = :clientId
      AND a.appointmentDate > CURRENT_TIMESTAMP
    ORDER BY a.appointmentDate ASC
    """)
  List<Appointment> findByClientId(@Param("clientId") Long clientId, Pageable pageable);

  /**
   * Realiza una búsqueda de todas las citas próximas que tiene asignado un veterinario
   * 
   * @param vetId    ID del veterinario
   * @param pageable Sistema de paginación
   * @return List(Appointment) - Lista con todas las citas que cumplen la condición establecida
   */
  @Query("SELECT a FROM Appointment a WHERE a.vet.id = :vetId AND a.appointmentDate > CURRENT_TIMESTAMP ORDER BY a.appointmentDate ASC")
  List<Appointment> findByVetId(@Param("vetId") Long vetId, Pageable pageable);

  /**
   * Realiza una búsqueda de todas las citas próximas que tiene asignado un veterinario entre dos fechas concretas
   * 
   * @param vetId      ID del veterinario
   * @param startOfDay Fecha y hora de comienzo del día sobre el que se buscan las citas
   * @param endOfDay   Fecha y hora de finalización del día sobre el que se buscan las citas
   * @param pageable   Sistema de paginación
   * @return List(Appointment) - Lista con todas las citas que cumplen la condición establecida
   */
  @Query("""
    SELECT a FROM Appointment a
      WHERE a.vet.id = :vetId
        AND a.appointmentDate BETWEEN :startOfDay AND :endOfDay
      ORDER BY a.appointmentDate ASC
    """)
  List<Appointment> findByVetIdAndDay(@Param("vetId") Long vetId, @Param("startOfDay") LocalDateTime startOfDay,
      @Param("endOfDay") LocalDateTime endOfDay, Pageable pageable);

  /**
   * Realiza una búsqueda de todas las citas próximas que tiene asignada una clínica veterinaria
   * 
   * @param vetEntityId ID de la entidad veterinaria
   * @param pageable    Sistema de paginación
   * @return List(Appointment) - Lista con todas las citas que cumplen la condición establecida
   */
  @Query("""
    SELECT a FROM Appointment a
      JOIN a.vet v
      WHERE v.vetEntity.id = :vetEntityId
        AND a.appointmentDate > CURRENT_TIMESTAMP
      ORDER BY a.appointmentDate ASC
    """)
  List<Appointment> findByVetEntityId(@Param("vetEntityId") Long vetEntityId, Pageable pageable);

  /**
   * Realiza una búsqueda de todas las citas próximas que tiene asignada una clínica veterinaria entre dos fechas
   * concretas
   * 
   * @param vetEntityId ID de la entidad veterinaria
   * @param startOfDay  Fecha y hora de comienzo del día sobre el que se buscan las citas
   * @param endOfDay    Fecha y hora de finalización del día sobre el que se buscan las citas
   * @param pageable    Sistema de paginación
   * @return List(Appointment) - Lista con todas las citas que cumplen la condición establecida
   */
  @Query("""
    SELECT a FROM Appointment a
      JOIN a.vet v
      WHERE v.vetEntity.id = :vetEntityId
        AND a.appointmentDate BETWEEN :startOfDay AND :endOfDay
      ORDER BY a.appointmentDate ASC
    """)
  List<Appointment> findByVetEntityIdAndDay(@Param("vetEntityId") Long vetEntityId,
      @Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay, Pageable pageable);

}
