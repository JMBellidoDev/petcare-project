package petcare.app.core.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import petcare.app.domain.dto.AppointmentDto;
import petcare.app.domain.entity.Appointment;
import petcare.app.domain.utils.exceptions.ResourceNotFoundException;

/** Interfaz de servicio de citas veterinarias */
@Service
public interface IAppointmentService {

  /**
   * Busca todas las citas de mascotas asociadas con un cliente
   * 
   * @param clientId ID del cliente o usuario
   * @param pageable Sistema de paginación
   * @return List(Appointment) - Lista con las citas solicitadas
   */
  @Transactional(readOnly = true)
  List<Appointment> findByClientId(Long clientId, Pageable pageable);

  /**
   * Busca todas las citas próximas asignadas a un veterinario
   * 
   * @param vetId    ID del veterinario
   * @param pageable Sistema de paginación
   * @return List(Appointment) - Lista con las citas solicitadas
   */
  @Transactional(readOnly = true)
  List<Appointment> findByVetId(Long vetId, Pageable pageable);

  /**
   * Busca todas las citas asignadas a un veterinario en el día proporcionado
   * 
   * @param vetId    ID del veterinario
   * @param date     Fecha en la que buscar las citas
   * @param pageable Sistema de paginación
   * @return List(Appointment) - Lista con las citas solicitadas
   */
  @Transactional(readOnly = true)
  List<Appointment> findByVetIdAndDate(Long vetId, LocalDate date, Pageable pageable);

  /**
   * Busca todas las citas próximas asignadas a una entidad veterinaria
   * 
   * @param vetEntityId ID de la entidad veterinaria
   * @param pageable    Sistema de paginación
   * @return List(Appointment) - Lista con las citas solicitadas
   */
  @Transactional(readOnly = true)
  List<Appointment> findByVetEntityId(Long vetEntityId, Pageable pageable);

  /**
   * Busca todas las citas próximas asignadas a una entidad veterinaria en el día proporcionado
   * 
   * @param vetEntityId ID de la entidad veterinaria
   * @param date        Fecha en la que buscar las citas
   * @param pageable    Sistema de paginación
   * @return List(Appointment) - Lista con las citas solicitadas
   */
  @Transactional(readOnly = true)
  List<Appointment> findByVetEntityIdAndDate(Long vetEntityId, LocalDate date, Pageable pageable);

  /**
   * Almacena una nueva cita en el sistema
   * 
   * @param appointmentDto Cita a almacenar en formato DTO
   * @return AppointmentDto
   * @throws ResourceNotFoundException Si no se encuentra alguno de los recursos necesarios
   */
  @Transactional
  AppointmentDto save(AppointmentDto appointmentDto) throws ResourceNotFoundException;

  /**
   * Modifica una cita del sistema
   * 
   * @param appointmentDto Cita con los nuevos datos
   * @param id             ID de la cita a modificar
   * @return AppointmentDto - Cita modificada
   * @throws ResourceNotFoundException Si no se encuentra alguno de los recursos necesarios
   */
  @Transactional
  AppointmentDto update(AppointmentDto appointmentDto, Long id) throws ResourceNotFoundException;

  /**
   * Elimina una cita del sistema
   * 
   * @param id ID de la cita a eliminar
   * @throws ResourceNotFoundException Si no se encuentra alguno de los recursos necesarios
   */
  @Transactional
  void delete(Long id) throws ResourceNotFoundException;

}
