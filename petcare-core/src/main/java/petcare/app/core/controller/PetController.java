package petcare.app.core.controller;

import java.util.List;
import java.util.Optional;

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

import petcare.app.core.service.IPetService;
import petcare.app.domain.dto.PetDto;
import petcare.app.domain.entity.Pet;
import petcare.app.domain.utils.conversor.PetDtoConversor;
import petcare.app.domain.utils.exceptions.ResourceNotFoundException;

/** Controlador de gestión de mascotas */
@RestController
@RequestMapping("/pets")
public class PetController {

  private static final Logger LOGGER = LoggerFactory.getLogger(PetController.class);

  /** Servicio de mascotas */
  private IPetService petService;

  /**
   * Setter - IPetService. Inyección de dependencias
   * 
   * @param petService Servicio de mascotas
   */
  @Autowired
  public void setPetService(IPetService petService) {
    this.petService = petService;
  }

  /**
   * GetMapping - Busca una mascota dado su ID
   * 
   * @param id ID de la mascota
   * @return PetDto - DTO de la mascota si se encuentra, null en caso contrario
   */
  @GetMapping("/find/{id}")
  PetDto findById(@PathVariable Long id) {

    Optional<Pet> optPet = petService.findById(id);
    PetDto dto;

    if (optPet.isPresent()) {
      dto = PetDtoConversor.toPetDto(optPet.get());

    } else {
      dto = null;
    }

    return dto;
  }

  /**
   * GetMapping - Busca todas las mascotas asociadas con el cliente
   * 
   * @param clientId ID del cliente
   * @param pageable Sistema de paginación
   * @return List(PetDto) - Lista con todas las mascotas encontradas
   */
  @GetMapping("/find/client-id/{clientId}")
  List<PetDto> findByClientId(@PathVariable Long clientId, Pageable pageable) {

    return petService.findByClientId(clientId, pageable).stream().map(PetDtoConversor::toPetDto).toList();
  }

  /**
   * GetMapping - Busca todas las mascotas asociadas con el cliente, dado su NIF/NIE
   * 
   * @param clientNationalIdDocument Documento nacional de identidad del cliente (NIF/NIE)
   * @param pageable                 Sistema de paginación
   * @return List(PetDto) - Lista con todas las mascotas encontradas
   */
  @GetMapping("/find/client-national-id-document/{clientNationalIdDocument}")
  List<PetDto> findByClientNationalIdDocument(@PathVariable String clientNationalIdDocument, Pageable pageable) {

    return petService
        .findByClientNationalIdDocument(clientNationalIdDocument, pageable)
        .stream()
        .map(PetDtoConversor::toPetDto)
        .toList();
  }

  /**
   * PostMapping - Almacena una nueva mascota en el sistema
   * 
   * @param pet Mascota a almacenar
   * @return ResponseEntity(Pet) - Respuesta con la mascota ya almacenada en la base de datos
   */
  @PostMapping
  ResponseEntity<Pet> save(@RequestBody Pet pet) {

    Pet savedPet = petService.save(pet);

    return ResponseEntity.status(HttpStatus.CREATED).body(savedPet);
  }

  /**
   * PutMapping - Modifica una mascota ya almacenada en el sistema
   * 
   * @param pet Mascota con los datos ya modificados
   * @param id  ID de la mascota a modificar
   * @return ResponseEntity(Pet) - Respuesta con la mascota ya modificada en la base de datos
   */
  @PutMapping("/{id}")
  ResponseEntity<Pet> update(@RequestBody Pet pet, @PathVariable Long id) {

    ResponseEntity<Pet> result;

    try {
      // Se intenta modificar la mascota
      Pet modifiedPet = petService.update(pet, id);
      result = ResponseEntity.status(HttpStatus.OK).body(modifiedPet);

      // Si no se encuentra la mascota durante el proceso
    } catch (ResourceNotFoundException e) {

      LOGGER.error(e.getMessage());
      result = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    return result;
  }

  /**
   * PutMapping - Añade una nueva asociación cliente - mascota
   * 
   * @param petId              ID de la mascota
   * @param nationalIdDocument Documento nacional de identidad del cliente (NIF/NIE)
   * @return ResponseEntity(Pet) - Respuesta con el resultado de la operación
   */
  @PutMapping("/pet-id/{petId}/client-national-id-document/{nationalIdDocument}")
  ResponseEntity<Pet> addClient(@PathVariable Long petId, @PathVariable String nationalIdDocument) {

    ResponseEntity<Pet> result;

    try {
      petService.addClient(petId, nationalIdDocument);
      result = ResponseEntity.ok().build();

      // Si no se encuentra alguna entidad durante el proceso
    } catch (ResourceNotFoundException e) {

      LOGGER.error(e.getMessage());
      result = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    return result;
  }

  /**
   * DeleteMapping - Elimina una mascota del sistema.
   * 
   * @param id ID de la mascota a eliminar
   * @return ResponseEntity(Pet) - Respuesta con el resultado de la operación
   */
  @DeleteMapping("/{id}")
  ResponseEntity<Pet> delete(@PathVariable Long id) {

    ResponseEntity<Pet> result;

    try {
      // Se intenta eliminar la mascota
      petService.delete(id);
      result = ResponseEntity.status(HttpStatus.OK).build();

      // Si no se encuentra la mascota durante el proceso
    } catch (ResourceNotFoundException e) {
      result = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    return result;
  }

  /**
   * DeleteMapping - Elimina una asociación cliente - mascota
   * 
   * @param petId    ID de la mascota
   * @param clientId ID del cliente
   * @return ResponseEntity(Pet) - Respuesta con el resultado de la operación
   */
  @DeleteMapping("/pet-id/{petId}/client-id/{clientId}")
  ResponseEntity<Pet> removeClient(@PathVariable Long petId, @PathVariable Long clientId) {

    ResponseEntity<Pet> result;

    try {
      petService.removeClient(petId, clientId);
      result = ResponseEntity.ok().build();

      // Si no se encuentra alguna entidad durante el proceso
    } catch (ResourceNotFoundException e) {

      LOGGER.error(e.getMessage());
      result = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    return result;
  }

}
