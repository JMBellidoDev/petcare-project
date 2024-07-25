package petcare.app.security.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import petcare.app.domain.entity.Client;
import petcare.app.domain.entity.Vet;
import petcare.app.domain.entity.VetEntity;
import petcare.app.domain.utils.exceptions.ResourceNotFoundException;
import petcare.app.security.users.service.UserService;
import petcare.app.security.users.utils.Paths;

/** Controlador de usuarios */
@RestController
@RequestMapping(path = { Paths.USERS_PREFIX })
public class UserController {

  /** Servicio de usuarios */
  private UserService userService;

  /**
   * Setter - UserService. Inyección de dependencias
   * 
   * @param userService Servicio de usuarios
   */
  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  /**
   * PostMapping - Genera un nuevo cliente
   * 
   * @param client Nuevo cliente a almacenar
   * @return ResponseEntity(Client) - Respuesta con el nuevo cliente almacenado
   */
  @PostMapping(Paths.REGISTER_CLIENT)
  public ResponseEntity<Client> saveClient(@RequestBody Client client) {

    return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveClient(client));
  }

  /**
   * PostMapping - Genera un nuevo veterinario
   * 
   * @param vet Nuevo veterinario a almacenar
   * @return ResponseEntity(Vet) - Respuesta con el veterinario almacenado
   */
  @PostMapping(Paths.REGISTER_VET)
  public ResponseEntity<Vet> saveVet(@RequestBody Vet vet) {

    return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveVet(vet));
  }

  /**
   * PostMapping - Genera una nueva entidad veterinaria
   * 
   * @param vetEntity Entidad veterinaria
   * @return ResponseEntity(VetEntity) - Respuesta con la entidad veterinaria almacenada
   */
  @PostMapping(Paths.REGISTER_VET_ENTITY)
  public ResponseEntity<VetEntity> saveVetEntity(@RequestBody VetEntity vetEntity) {

    return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveVetEntity(vetEntity));

  }

  /**
   * PutMapping - Modifica un cliente ya existente
   * 
   * @param client Cliente con los datos modificados
   * @param id     ID del cliente a modificar
   * @return ResponseEntity(Client) - Respuesta con el cliente modificado
   * @throws ResourceNotFoundException En caso de no encontrar algun recurso
   */
  @PutMapping(Paths.UPDATE_CLIENT)
  public ResponseEntity<Client> updateClient(@RequestBody Client client, @PathVariable Long id)
      throws ResourceNotFoundException {

    // Se busca la entidad y se modifica
    ResponseEntity<Client> result;

    try {
      Client modifiedClient = userService.updateClient(client, id);
      result = ResponseEntity.ok(modifiedClient);

    } catch (ResourceNotFoundException e) {
      result = ResponseEntity.notFound().build();
    }

    return result;
  }

  /**
   * PutMapping - Modifica un veterinario ya existente
   * 
   * @param vet Veterinario con los datos modificados
   * @param id  ID del veterinario a modificar
   * @return ResponseEntity(Vet) - Respuesta con el veterinario modificado
   * @throws ResourceNotFoundException En caso de no encontrar algun recurso
   */
  @PutMapping(Paths.UPDATE_VET)
  public ResponseEntity<Vet> updateVet(@RequestBody Vet vet, @PathVariable Long id) throws ResourceNotFoundException {

    // Se busca la entidad y se modifica
    ResponseEntity<Vet> result;

    try {
      Vet modifiedVet = userService.updateVet(vet, id);
      result = ResponseEntity.ok(modifiedVet);

    } catch (ResourceNotFoundException e) {
      result = ResponseEntity.notFound().build();
    }

    return result;
  }

  /**
   * PutMapping - Modifica una entidad veterinaria existente
   * 
   * @param vetEntity Entidad veterinaria con los datos modificados
   * @param id        ID de la entidad a modificar
   * @return ResponseEntity(VetEntity) - Respuesta con la entidad veterinaria modificada
   * @throws ResourceNotFoundException En caso de no encontrar algun recurso
   */
  @PutMapping(Paths.UPDATE_VET_ENTITY)
  public ResponseEntity<VetEntity> updateVetEntity(@RequestBody VetEntity vetEntity, @PathVariable Long id)
      throws ResourceNotFoundException {

    // Se busca la entidad y se modifica
    ResponseEntity<VetEntity> result;

    try {
      VetEntity modifiedVetEntity = userService.updateVetEntity(vetEntity, id);
      result = ResponseEntity.ok(modifiedVetEntity);

    } catch (ResourceNotFoundException e) {
      result = ResponseEntity.notFound().build();
    }

    return result;
  }

}
