package petcare.app.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import petcare.app.domain.dto.ReportDto;
import petcare.app.domain.entity.Report;
import petcare.app.domain.utils.exceptions.ResourceNotFoundException;

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
  @Transactional(readOnly = true)
  Optional<Report> findById(Long reportId);

  /**
   * Busca todos los informes referentes a un animal dado su ID, ordenado de forma descendente por fecha
   * 
   * @param petId    ID de la mascota cuyos informes se van a buscar
   * @param pageable Sistema de paginación
   * @return List(Report) Lista con los informes encontrados de la mascota solicitada
   */
  @Transactional(readOnly = true)
  List<Report> findByPetId(Long petId, Pageable pageable);

  /**
   * Busca todos los informes de las mascotas relacionadas directamente con un cliente concreto (dado su ID), ordenado
   * de forma descendente por fecha
   * 
   * @param clientId ID del cliente cuyos informes se van a buscar
   * @param pageable Sistema de paginación
   * @return List(Report) Lista con los informes encontrados de las mascotas relacionadas con el cliente o usuario
   */
  @Transactional(readOnly = true)
  List<Report> findByClientId(Long clientId, Pageable pageable);

  /**
   * Busca todos los informes de las mascotas relacionadas directamente con un cliente concreto (dado su NIF/NIE),
   * ordenado de forma descendente por fecha
   * 
   * @param clientNationalIdDocument NIF/NIE del cliente cuyos informes se van a buscar
   * @param pageable                 Sistema de paginación
   * @return List(Report) Lista con los informes encontrados de las mascotas relacionadas con el cliente o usuario
   */
  @Transactional(readOnly = true)
  List<Report> findByClientNationalIdDocument(String clientNationalIdDocument, Pageable pageable);

  /**
   * Busca todos los informes emitidos por un veterinario concreto del sistema
   * 
   * @param vetId    ID del veterinario
   * @param pageable SIstema de paginación
   * @return Lista con los informes encontrados emitidos por el veterinario
   */
  @Transactional(readOnly = true)
  List<Report> findByVetId(Long vetId, Pageable pageable);

  /**
   * Busca todos los informes emitidos por un veterinario que trabaje en la entidad veterinaria
   * 
   * @param vetEntityId ID de la entidad veterinaria
   * @param pageable    Sistema de paginación
   * @return Lista con los informes encontrados emitidos por un veterinario de la entidad
   */
  @Transactional(readOnly = true)
  List<Report> findByVetEntityId(Long vetEntityId, Pageable pageable);

  /**
   * Almacena un nuevo informe en el sistema
   * 
   * @param reportDto Informe a guardar en formato DTO
   * @return ReportDto
   * @throws ResourceNotFoundException En caso de que algún recurso no sea encontrado
   */
  @Transactional
  ReportDto save(ReportDto reportDto) throws ResourceNotFoundException;

  /**
   * Actualiza un informe del sistema
   * 
   * @param reportDto Informe a con los datos modificados en formato DTO
   * @param id        ID del informe a modificar
   * @return ReportDto
   * @throws ResourceNotFoundException En caso de que algún recurso no sea encontrado
   */
  @Transactional
  ReportDto update(ReportDto reportDto, Long id) throws ResourceNotFoundException;

  /**
   * Elimina un informe almacenado
   * 
   * @param id ID del informe a eliminar
   * @throws ResourceNotFoundException En caso de que algún recurso no sea encontrado
   */
  @Transactional
  void delete(Long id) throws ResourceNotFoundException;

}
