package petcare.app.core.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import petcare.app.domain.dto.AppointmentDto;
import petcare.app.domain.entity.Appointment;
import petcare.app.domain.entity.Client;
import petcare.app.domain.entity.Pet;
import petcare.app.domain.entity.Vet;
import petcare.app.domain.repository.AppointmentRepository;
import petcare.app.domain.repository.ClientRepository;
import petcare.app.domain.repository.PetRepository;
import petcare.app.domain.repository.VetRepository;
import petcare.app.domain.utils.ExceptionMessages;
import petcare.app.domain.utils.conversor.AppointmentDtoConversor;
import petcare.app.domain.utils.exceptions.ResourceNotFoundException;

/** Implementación del servicio de gestión de citas */
@Service
public class AppointmentServiceImpl implements IAppointmentService {

  /** Repositorio de citas veterinarias */
  private AppointmentRepository appointmentRepository;

  /** Repositorio de mascotas */
  private PetRepository petRepository;

  /** Repositorio de clientes */
  private ClientRepository clientRepository;

  /** Repositorio de veterinarios */
  private VetRepository vetRepository;

  /**
   * Setter - AppointmentRepository. Inyección de dependencias
   * 
   * @param appointmentRepository Repositorio de citas veterinarias
   */
  @Autowired
  public void setAppointmentRepository(AppointmentRepository appointmentRepository) {
    this.appointmentRepository = appointmentRepository;
  }

  /**
   * Setter - PetRepository. Inyección de dependencias
   * 
   * @param petRepository Repositorio de mascotas
   */
  @Autowired
  public void setPetRepository(PetRepository petRepository) {
    this.petRepository = petRepository;
  }

  /**
   * Setter - ClientRepository. Inyección de dependencias
   * 
   * @param clientRepository Repositorio de clientes
   */
  @Autowired
  public void setClientRepository(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  /**
   * Setter - VetRepository. Inyección de dependencias
   * 
   * @param vetRepository Repositorio de veterinarios
   */
  @Autowired
  public void setVetRepository(VetRepository vetRepository) {
    this.vetRepository = vetRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Appointment> findByClientId(Long clientId, Pageable pageable) {

    return appointmentRepository.findByClientId(clientId, pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Appointment> findByVetId(Long vetId, Pageable pageable) {

    return appointmentRepository.findByVetId(vetId, pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Appointment> findByVetIdAndDate(Long vetId, LocalDate date, Pageable pageable) {

    // LocalDateTime entre el comienzo del día y el final del mismo día para obtener todas las citas del día completo
    LocalDateTime startOfDay = date.atStartOfDay();
    LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

    return appointmentRepository.findByVetIdAndDay(vetId, startOfDay, endOfDay, pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Appointment> findByVetEntityId(Long vetEntityId, Pageable pageable) {

    return appointmentRepository.findByVetEntityId(vetEntityId, pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Appointment> findByVetEntityIdAndDate(Long vetEntityId, LocalDate date, Pageable pageable) {

    // LocalDateTime entre el comienzo del día y el final del mismo día para obtener todas las citas del día completo
    LocalDateTime startOfDay = date.atStartOfDay();
    LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

    return appointmentRepository.findByVetEntityIdAndDay(vetEntityId, startOfDay, endOfDay, pageable);
  }

  @Override
  @Transactional
  public AppointmentDto save(AppointmentDto appointmentDto) throws ResourceNotFoundException {

    Appointment appointment = AppointmentDtoConversor.toAppointment(appointmentDto);

    // Se asigna el veterinario
    Vet vet = vetRepository
        .findById(appointmentDto.getVetDtoSimple().getId())
        .orElseThrow(() -> new ResourceNotFoundException(
            String.format(ExceptionMessages.VET_NOT_FOUND_BY_ID, appointmentDto.getVetDtoSimple().getId())));

    // Se asigna la mascota
    Pet pet = petRepository
        .findById(appointmentDto.getPetDtoSimple().getId())
        .orElseThrow(() -> new ResourceNotFoundException(
            String.format(ExceptionMessages.PET_NOT_FOUND_BY_ID, appointmentDto.getPetDtoSimple().getId())));

    // Se asigna el cliente
    Client client = clientRepository
        .findById(appointmentDto.getClientDtoSimple().getId())
        .orElseThrow(() -> new ResourceNotFoundException(
            String.format(ExceptionMessages.CLIENT_NOT_FOUND_BY_ID, appointmentDto.getClientDtoSimple().getId())));

    // Se actualizan vet, pet y client
    pet.getAppointments().add(appointment);

    appointment.setVet(vet);
    appointment.setPet(pet);
    appointment.setClient(client);

    Appointment savedAppointment = appointmentRepository.save(appointment);

    return AppointmentDtoConversor.toAppointmentDto(savedAppointment);
  }

  @Override
  @Transactional
  public AppointmentDto update(AppointmentDto appointmentDto, Long id) throws ResourceNotFoundException {

    Optional<Appointment> optSavedAppointment = appointmentRepository.findById(id);

    // Se obtiene la cita del sistema y se modifican los datos
    if (optSavedAppointment.isPresent()) {

      Appointment foundAppointment = optSavedAppointment.get();

      foundAppointment.setAppointmentDate(appointmentDto.getAppointmentDate());

      // Se asigna el veterinario
      Vet vet = vetRepository
          .findById(appointmentDto.getVetDtoSimple().getId())
          .orElseThrow(() -> new ResourceNotFoundException(
              String.format(ExceptionMessages.VET_NOT_FOUND_BY_ID, appointmentDto.getVetDtoSimple().getId())));

      // Se asigna la mascota
      Pet pet = petRepository
          .findById(appointmentDto.getPetDtoSimple().getId())
          .orElseThrow(() -> new ResourceNotFoundException(
              String.format(ExceptionMessages.PET_NOT_FOUND_BY_ID, appointmentDto.getPetDtoSimple().getId())));

      // Se asigna el cliente
      Client client = clientRepository
          .findById(appointmentDto.getClientDtoSimple().getId())
          .orElseThrow(() -> new ResourceNotFoundException(
              String.format(ExceptionMessages.CLIENT_NOT_FOUND_BY_ID, appointmentDto.getClientDtoSimple().getId())));

      // Se almacenan todos los atributos y se guardan los datos
      pet.getAppointments().add(foundAppointment);

      foundAppointment.setVet(vet);
      foundAppointment.setPet(pet);
      foundAppointment.setClient(client);

      Appointment modifiedAppointment = appointmentRepository.save(foundAppointment);

      return AppointmentDtoConversor.toAppointmentDto(modifiedAppointment);

      // Se lanza la excepción si no se encuentra la mascota
    } else {
      throw new ResourceNotFoundException(String.format(ExceptionMessages.APPOINTMENT_NOT_FOUND_BY_ID, id));
    }
  }

  @Override
  @Transactional
  public void delete(Long id) throws ResourceNotFoundException {

    Optional<Appointment> optSavedAppointment = appointmentRepository.findById(id);

    // Se elimina si se encuentra la cita. Sino, se lanza excepción
    if (optSavedAppointment.isPresent()) {

      // Se elimina la cita del resto de relaciones
      Appointment appointment = optSavedAppointment.get();

      Pet pet = appointment.getPet();
      pet.getAppointments().remove(appointment);

      // Se almacenan de nuevo las entidades sin la cita
      petRepository.save(pet);

      // Se elimina la cita
      appointmentRepository.deleteById(id);

    } else {
      throw new ResourceNotFoundException(String.format(ExceptionMessages.APPOINTMENT_NOT_FOUND_BY_ID, id));
    }

  }

}
