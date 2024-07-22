package petcare.app.domain.utils.conversor;

import petcare.app.domain.dto.AppointmentDto;
import petcare.app.domain.entity.Appointment;
import petcare.app.domain.utils.conversor.simple.ClientDtoSimpleConversor;
import petcare.app.domain.utils.conversor.simple.PetDtoSimpleConversor;
import petcare.app.domain.utils.conversor.simple.VetDtoSimpleConversor;

/** Conversor de entidades entre un objeto de la clase Appointment y un objeto de la clase AppointmentDto */
public class AppointmentDtoConversor {

  /** Constructor privado para evitar inicialización. Clase de métodos estáticos */
  private AppointmentDtoConversor() {

  }

  /**
   * Conversor de un objeto Appointment a un objeto AppointmentDto
   * 
   * @param Appointment appointment a convertir
   * @return AppointmentDto
   */
  public static AppointmentDto toAppointmentDto(Appointment appointment) {

    AppointmentDto appointmentDto = new AppointmentDto();

    appointmentDto.setId(appointment.getId());
    appointmentDto.setAppointmentDate(appointment.getAppointmentDate());

    if (appointment.getClient() != null) {
      appointmentDto.setClientDtoSimple(ClientDtoSimpleConversor.toClientDtoSimple(appointment.getClient()));
    }

    if (appointment.getVet() != null) {
      appointmentDto.setVetDtoSimple(VetDtoSimpleConversor.toVetDtoSimple(appointment.getVet()));
    }

    if (appointment.getPet() != null) {
      appointmentDto.setPetDtoSimple(PetDtoSimpleConversor.toPetDtoSimple(appointment.getPet()));
    }

    return appointmentDto;

  }

  /**
   * Conversor de un objeto AppointmentDto a un objeto Appointment
   * 
   * @param AppointmentDto AppointmentDto a convertir
   * @return Appointment
   */
  public static Appointment toAppointment(AppointmentDto appointmentDto) {

    Appointment appointment = new Appointment();

    appointment.setId(appointmentDto.getId());
    appointment.setAppointmentDate(appointmentDto.getAppointmentDate());

    if (appointmentDto.getClientDtoSimple() != null) {
      appointment.setClient(ClientDtoSimpleConversor.toClient(appointmentDto.getClientDtoSimple()));
    }

    if (appointmentDto.getVetDtoSimple() != null) {
      appointment.setVet(VetDtoSimpleConversor.toVet(appointmentDto.getVetDtoSimple()));
    }

    if (appointmentDto.getPetDtoSimple() != null) {
      appointment.setPet(PetDtoSimpleConversor.toPet(appointmentDto.getPetDtoSimple()));
    }

    return appointment;

  }

}
