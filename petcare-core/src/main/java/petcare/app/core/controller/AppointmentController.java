package petcare.app.core.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import petcare.app.core.service.IAppointmentService;
import petcare.app.domain.dto.AppointmentDto;
import petcare.app.domain.utils.conversor.AppointmentDtoConversor;
import petcare.app.domain.utils.exceptions.ResourceNotFoundException;

/** Controlador de citas veterinarias */
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentController.class);

  /** Servicio de gestión de citas */
  private IAppointmentService appointmentService;

  /**
   * Setter - IAppointmentService. Inyección de dependencias
   * 
   * @param appointmentService Servicio de gestión de citas
   */
  @Autowired
  public void setAppointmentService(IAppointmentService appointmentService) {
    this.appointmentService = appointmentService;
  }

  /**
   * GetMapping - Busca todas las citas asociadas con los animales relacionados directamente con el cliente aportado
   * 
   * @param clientId ID del cliente
   * @param pageable Sistema de paginación
   * @return List(AppointmentDto) - Lista con las citas solicitadas en formato DTO
   */
  @GetMapping("/find/client-id/{clientId}")
  public List<AppointmentDto> findByClientId(@PathVariable Long clientId, Pageable pageable) {

    return appointmentService
        .findByClientId(clientId, pageable)
        .stream()
        .map(AppointmentDtoConversor::toAppointmentDto)
        .toList();
  }

  /**
   * GetMapping - Busca todas las citas asociadas con un veterinario
   * 
   * @param vetId    ID del veterinario
   * @param pageable Sistema de paginación
   * @return List(AppointmentDto) - Lista con las citas solicitadas en formato DTO
   */
  @GetMapping("/find/vet-id/{vetId}")
  public List<AppointmentDto> findByVetId(@PathVariable Long vetId, Pageable pageable) {

    return appointmentService
        .findByVetId(vetId, pageable)
        .stream()
        .map(AppointmentDtoConversor::toAppointmentDto)
        .toList();
  }

  /**
   * GetMapping - Busca todas las citas asociadas con veterinario en un día concreto<br/>
   * La fecha aportada debe encontrarse en formato 'yyyy-MM-dd'. En otro caso, se lanzará una excepción.
   * 
   * @param vetId    ID del veterinario
   * @param date     Fecha en la que se buscan las citas veterinarias
   * @param pageable Sistema de paginación
   * @return List(AppointmentDto) - Lista con las citas solicitadas en formato DTO
   */
  @GetMapping("/find/vet-id/{vetId}/date/{date}")
  public List<AppointmentDto> findByVetIdAndDate(@PathVariable Long vetId, @PathVariable String date,
      Pageable pageable) {

    LocalDate parsedDate = LocalDate.parse(date);

    return appointmentService
        .findByVetIdAndDate(vetId, parsedDate, pageable)
        .stream()
        .map(AppointmentDtoConversor::toAppointmentDto)
        .toList();
  }

  /**
   * GetMapping - Busca todas las citas asociadas con una entidad veterinaria
   * 
   * @param vetEntityId ID de la entidad veterinaria
   * @param pageable    Sistema de paginación
   * @return List(AppointmentDto) - Lista con las citas solicitadas en formato DTO
   */
  @GetMapping("/find/vet-entity-id/{vetEntityId}")
  public List<AppointmentDto> findByVetEntityId(@PathVariable Long vetEntityId, Pageable pageable) {

    return appointmentService
        .findByVetEntityId(vetEntityId, pageable)
        .stream()
        .map(AppointmentDtoConversor::toAppointmentDto)
        .toList();
  }

  /**
   * GetMapping - Busca todas las citas asociadas con una entidad veterinaria en un día concreto<br/>
   * La fecha aportada debe encontrarse en formato 'yyyy-MM-dd'. En otro caso, se lanzará una excepción.
   * 
   * @param vetEntityId ID del la entidad veterinaria
   * @param date        Fecha en la que se buscan las citas veterinarias
   * @param pageable    Sistema de paginación
   * @return List(AppointmentDto) - Lista con las citas solicitadas en formato DTO
   */
  @GetMapping("/find/vet-entity-id/{vetEntityId}/date/{date}")
  public List<AppointmentDto> findByVetEntityIdAndDate(@PathVariable Long vetEntityId, @PathVariable String date,
      Pageable pageable) {

    LocalDate parsedDate = LocalDate.parse(date);

    return appointmentService
        .findByVetEntityIdAndDate(vetEntityId, parsedDate, pageable)
        .stream()
        .map(AppointmentDtoConversor::toAppointmentDto)
        .toList();
  }

  /**
   * PostMapping - Almacena una nueva cita en el sistema
   * 
   * @param appointmentDto DTO de la cita a almacenar
   * @return ResponseEntity(AppointmentDto) - Respuesta con la cita almacenada en formato DTO
   */
  @PostMapping
  public ResponseEntity<AppointmentDto> save(@RequestBody AppointmentDto appointmentDto) {

    ResponseEntity<AppointmentDto> result;

    // Se intenta guardar la entidad. Se lanza la excepción si alguno de los atributos no se puede encontrar
    try {
      AppointmentDto savedAppointment = appointmentService.save(appointmentDto);
      result = ResponseEntity.status(HttpStatus.CREATED).body(savedAppointment);

    } catch (ResourceNotFoundException e) {

      result = ResponseEntity.notFound().build();
      LOGGER.error(e.getMessage());
    }

    return result;
  }

  /**
   * PutMapping - Modifica una cita existente en el sistema
   * 
   * @param appointmentDto DTO con la cita ya modificada
   * @param id             ID de la cita a modificar
   * @return ResponseEntity(AppointmentDto) - Respuesta con la cita ya modificada en formato DTO
   */
  @PutMapping("/{id}")
  public ResponseEntity<AppointmentDto> update(@RequestBody AppointmentDto appointmentDto, @PathVariable Long id) {

    ResponseEntity<AppointmentDto> result;

    // Se intenta actualizar la entidad. Se lanza la excepción si alguno de los atributos no se puede encontrar
    try {
      AppointmentDto savedAppointmentDto = appointmentService.update(appointmentDto, id);
      result = ResponseEntity.status(HttpStatus.CREATED).body(savedAppointmentDto);

    } catch (ResourceNotFoundException e) {

      result = ResponseEntity.notFound().build();
      LOGGER.error(e.getMessage());
    }

    return result;

  }

  /**
   * DeleteMapping - Elimina una cita del sistema
   * 
   * @param id ID de la cita a eliminar
   * @return ResponseEntity(AppointmentDto) - Respuesta con la confirmación de la eliminación de la cita
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<AppointmentDto> delete(@PathVariable Long id) {

    ResponseEntity<AppointmentDto> result;

    // Se intenta eliminar la entidad. Se lanza la excepción si alguno de los atributos no se puede encontrar
    try {
      appointmentService.delete(id);
      result = ResponseEntity.status(HttpStatus.OK).build();

    } catch (ResourceNotFoundException e) {

      result = ResponseEntity.notFound().build();
      LOGGER.error(e.getMessage());
    }

    return result;

  }

}
