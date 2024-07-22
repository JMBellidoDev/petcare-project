package petcare.app.core.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import petcare.app.domain.entity.Vet;

/** Repositorio de acceso a los distintos veterinarios almacenados en el sistema */
public interface VetRepository extends CrudRepository<Vet, Long>, JpaRepository<Vet, Long> {

  /**
   * Busca el veterinario encargado de una cita
   * 
   * @param appointmentId ID de la cita
   * @return Optional(Vet) - Un objeto del tipo Optional que podrá contener el veterinario en caso de ser encontrado
   */
  Optional<Vet> findByAppointmentsId(Long appointmentsId);

  /**
   * Busca todos los veterinarios pertenecientes a la misma entidad o clínica dado su nombre completo<br/>
   * El objeto obtenido será paginable a través de los parámetros del buscador
   * 
   * @param name        Nombre del veterinario
   * @param vetEntityId ID asociado a la entidad o clínica veterinaria
   * @param pageable    Sistema de paginación
   * @return List(Vet) - Una lista con todos los veterinarios que cumplen la condición
   */
  @Query("SELECT v FROM Vet v WHERE LOWER(v.name) LIKE %:name% AND v.vetEntity.id = :vetEntityId")
  List<Vet> findByNameAndVetEntityId(@Param("name") String name, @Param("vetEntityId") Long vetEntityId,
      Pageable pageable);

  /**
   * Busca todos los veterinarios pertenecientes a la misma entidad o clínica<br/>
   * El objeto obtenido será paginable a través de los parámetros del buscador
   * 
   * @param name        Nombre del veterinario
   * @param vetEntityId ID asociado a la entidad o clínica veterinaria
   * @param pageable    Sistema de paginación
   * @return List(Vet) - Una lista con todos los veterinarios que cumplen la condición
   */
  @Query("SELECT v FROM Vet v WHERE v.vetEntity.id = :vetEntityId")
  List<Vet> findByVetEntityId(@Param("vetEntityId") Long vetEntityId, Pageable pageable);

}
