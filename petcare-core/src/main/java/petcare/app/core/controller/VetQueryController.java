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

  /**
   * GetMapping - Busca todos los veterinarios por nombre dentro de la entidad veterinaria especificada
   * 
   * @param name        Nombre del veterinario a buscar
   * @param vetEntityId ID de la entidad veterinaria
   * @param pageable    Sistema de paginación
   * @return List(VetDto) - Lista con todos los veterinarios encontrados en formato DTO
   */
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

  /**
   * GetMapping - Busca todos los veterinarios que pertenezcan a una entidad veterinaria especificada
   * 
   * @param vetEntityId ID de la entidad veterinaria
   * @param pageable    SIstema de paginación
   * @return List(VetDto) - Lista con todos los veterinarios encontrados en formato DTO
   */
  @GetMapping("/find/entity/{vetEntityId}")
  public List<VetDto> findByVetEntityId(@PathVariable(name = "vetEntityId") Long vetEntityId, Pageable pageable) {

    LOGGER.info("enter");

    return vetService.findByVetEntityId(vetEntityId, pageable).stream().map(VetDtoConversor::toVetDto).toList();
  }

}
