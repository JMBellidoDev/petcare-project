package petcare.app.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import petcare.app.domain.entity.Report;

/** Interfaz de servicio de informes veterinarios */
@Service
public interface IReportService {

  /**
   * Busca un informe referente a un animal dado el ID del informe
   * 
   * @param reportId ID del informe a buscar
   * @return Optional(Report) - Un objeto de la clase Optional que podrá contener el informe en caso de encontrarlo en
   *         el sistema
   */
  Optional<Report> findById(Long reportId);

  /**
   * Busca todos los informes referentes a un animal dado su ID, ordenado de forma descendente por fecha
   * 
   * @param petId    ID de la mascota cuyos informes se van a buscar
   * @param pageable Sistema de paginación
   * @return List(Report) Lista con los informes encontrados de la mascota solicitada
   */
  List<Report> findByPetId(Long petId, Pageable pageable);

  /**
   * Busca todos los informes de las mascotas relacionadas directamente con un cliente concreto (dado su ID), ordenado
   * de forma descendente por fecha
   * 
   * @param clientId ID del cliente cuyos informes se van a buscar
   * @param pageable Sistema de paginación
   * @return List(Report) Lista con los informes encontrados de las mascotas relacionadas con el cliente o usuario
   */
  List<Report> findByClientId(Long clientId, Pageable pageable);

  /**
   * Busca todos los informes de las mascotas relacionadas directamente con un cliente concreto (dado su NIF/NIE),
   * ordenado de forma descendente por fecha
   * 
   * @param clientNationalIdDocument NIF/NIE del cliente cuyos informes se van a buscar
   * @param pageable                 Sistema de paginación
   * @return List(Report) Lista con los informes encontrados de las mascotas relacionadas con el cliente o usuario
   */
  List<Report> findByClientNationalIdDocument(String clientNationalIdDocument, Pageable pageable);

  /**
   * Busca todos los informes emitidos por un veterinario concreto del sistema
   * 
   * @param vetId    ID del veterinario
   * @param pageable SIstema de paginación
   * @return Lista con los informes encontrados emitidos por el veterinario
   */
  List<Report> findByVetId(Long vetId, Pageable pageable);

  /**
   * Busca todos los informes emitidos por un veterinario que trabaje en la entidad veterinaria
   * 
   * @param vetEntityId ID de la entidad veterinaria
   * @param pageable    Sistema de paginación
   * @return Lista con los informes encontrados emitidos por un veterinario de la entidad
   */
  List<Report> findByVetEntityId(Long vetEntityId, Pageable pageable);

}
