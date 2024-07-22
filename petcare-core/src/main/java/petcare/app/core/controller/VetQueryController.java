package petcare.app.core.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import petcare.app.core.service.query.IVetQueryService;
import petcare.app.domain.dto.VetDto;
import petcare.app.domain.entity.Vet;
import petcare.app.domain.utils.conversor.VetDtoConversor;

/** Controlador de acceso a la información de un veterinario */
@RestController
@RequestMapping("/vets")
public class VetQueryController {

  private static final Logger LOGGER = LoggerFactory.getLogger(VetQueryController.class);

  /** Servicio de veterinarios */
  private IVetQueryService vetService;

  /**
   * Setter - vetService. Inyección de dependencias
   * 
   * @param vetService - Servicio de veterinarios
   */
  @Autowired
  public void setVetService(IVetQueryService vetService) {
    this.vetService = vetService;
  }

  /**
   * Busca un veterinario dado su ID
   * 
   * @param id ID del veterinario
   * @return ResponseEntity(VetDto)
   */
  @GetMapping("/find/{id}")
  public VetDto findById(@PathVariable Long id) {

    Optional<Vet> optVet = vetService.findById(id);
    VetDto result;

    if (optVet.isPresent()) {
      result = VetDtoConversor.toVetDto(optVet.get());

    } else {
      LOGGER.info("Not found");
      result = null;
    }

    return result;
  }

  @GetMapping("/find/appointment-id/{appointmentId}")
  public VetDto findByAppointmentId(@PathVariable Long appointmentId) {

    Optional<Vet> optVet = vetService.findByAppointmentsId(appointmentId);
    VetDto result;

    if (optVet.isPresent()) {
      result = VetDtoConversor.toVetDto(optVet.get());

    } else {
      LOGGER.info("Not found");
      result = null;
    }

    return result;

  }

  @GetMapping("/find/name/{name}/entity/{vetEntityId}")
  public List<VetDto> findByNameAndVetEntityId(@PathVariable(name = "name") String name,
      @PathVariable(name = "vetEntityId") Long vetEntityId, Pageable pageable) {

    LOGGER.info("enter");

    return vetService
        .findByNameAndVetEntityId(name, vetEntityId, pageable)
        .stream()
        .map(VetDtoConversor::toVetDto)
        .toList();
  }

  @GetMapping("/find/entity/{vetEntityId}")
  public List<VetDto> findByVetEntityId(@PathVariable(name = "vetEntityId") Long vetEntityId, Pageable pageable) {

    LOGGER.info("enter");

    return vetService.findByVetEntityId(vetEntityId, pageable).stream().map(VetDtoConversor::toVetDto).toList();
  }

}
