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

import petcare.app.core.model.repository.AppointmentRepository;
import petcare.app.core.model.repository.ClientRepository;
import petcare.app.core.model.repository.PetRepository;
import petcare.app.core.model.repository.VetRepository;
import petcare.app.core.utils.ExceptionMessages;
import petcare.app.core.utils.exceptions.ResourceNotFoundException;
import petcare.app.domain.entity.Appointment;
import petcare.app.domain.entity.Client;
import petcare.app.domain.entity.Pet;
import petcare.app.domain.entity.Vet;

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
  public Appointment save(Appointment appointment) throws ResourceNotFoundException {

    // Se asigna el veterinario
    Vet vet = vetRepository
        .findById(appointment.getVet().getId())
        .orElseThrow(() -> new ResourceNotFoundException(
            String.format(ExceptionMessages.VET_NOT_FOUND_BY_ID, appointment.getVet().getId())));

    // Se asigna la mascota
    Pet pet = petRepository
        .findById(appointment.getPet().getId())
        .orElseThrow(() -> new ResourceNotFoundException(
            String.format(ExceptionMessages.PET_NOT_FOUND_BY_ID, appointment.getPet().getId())));

    // Se asigna el cliente
    Client client = clientRepository
        .findById(appointment.getClient().getId())
        .orElseThrow(() -> new ResourceNotFoundException(
            String.format(ExceptionMessages.CLIENT_NOT_FOUND_BY_ID, appointment.getClient().getId())));

    appointment.setVet(vet);
    appointment.setPet(pet);
    appointment.setClient(client);

    return appointmentRepository.save(appointment);
  }

  @Override
  @Transactional
  public Appointment update(Appointment appointment, Long id) throws ResourceNotFoundException {

    Optional<Appointment> optSavedAppointment = appointmentRepository.findById(id);

    // Se obtiene la cita del sistema y se modifican los datos
    if (optSavedAppointment.isPresent()) {

      Appointment savedAppointment = optSavedAppointment.get();

      savedAppointment.setAppointmentDate(appointment.getAppointmentDate());

      // Se asigna el veterinario
      Vet vet = vetRepository
          .findById(appointment.getVet().getId())
          .orElseThrow(() -> new ResourceNotFoundException(
              String.format(ExceptionMessages.VET_NOT_FOUND_BY_ID, appointment.getVet().getId())));

      // Se asigna la mascota
      Pet pet = petRepository
          .findById(appointment.getPet().getId())
          .orElseThrow(() -> new ResourceNotFoundException(
              String.format(ExceptionMessages.PET_NOT_FOUND_BY_ID, appointment.getPet().getId())));

      // Se asigna el cliente
      Client client = clientRepository
          .findById(appointment.getClient().getId())
          .orElseThrow(() -> new ResourceNotFoundException(
              String.format(ExceptionMessages.CLIENT_NOT_FOUND_BY_ID, appointment.getClient().getId())));

      savedAppointment.setVet(vet);
      savedAppointment.setPet(pet);
      savedAppointment.setClient(client);

      return appointmentRepository.save(savedAppointment);

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
      appointmentRepository.deleteById(id);

    } else {
      throw new ResourceNotFoundException(String.format(ExceptionMessages.APPOINTMENT_NOT_FOUND_BY_ID, id));
    }

  }

}
