package petcare.app.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import petcare.app.core.utils.exceptions.ResourceNotFoundException;
import petcare.app.domain.entity.Pet;

/** Interfaz de servicio de mascotas */
@Service
public interface IPetService {

  /**
   * Busca todas las mascotas dado su ID
   * 
   * @param id ID de la mascota
   * @return Optional(Pet) - Un objeto de la clase Optional que podrá contener la mascota en caso de encontrarla en el
   *         sistema
   */
  @Transactional(readOnly = true)
  Optional<Pet> findById(Long id);

  /**
   * Busca todas las mascotas asociadas con un cliente
   * 
   * @param clientId ID del cliente
   * @param pageable Sistema de paginación
   * @return List(Pet) - Lista con todas las mascotas encontradas
   */
  @Transactional(readOnly = true)
  List<Pet> findByClientId(Long clientId, Pageable pageable);

  /**
   * Busca todas las mascotas asociadas con un cliente, dado su NIF/NIE
   * 
   * @param clientNationalIdDocument Documento nacional de identidad (NIF/NIE)
   * @param pageable                 Sistema de paginación
   * @return List(Pet) - Lista con todas las mascotas encontradas
   */
  @Transactional(readOnly = true)
  List<Pet> findByClientNationalIdDocument(String clientNationalIdDocument, Pageable pageable);

  /**
   * Almacena una nueva mascota en el sistema
   * 
   * @param pet Mascota a guardar
   * @return Pet - Mascota con los datos proporcionados y el ID generado
   */
  @Transactional
  Pet save(Pet pet);

  /**
   * Modifica una mascota del sistema. Los datos modificados serán los datos propios del animal. Los datos que
   * relacionan la entidad Pet con otras serán modificadas en otros métodos
   * 
   * @param pet Mascota con los datos modificados
   * @return Pet - Mascota con los datos proporcionados y el ID generado
   * @throws ResourceNotFoundException En caso de no encontrar alguno de los recursos
   */
  @Transactional
  Pet update(Pet pet, Long id) throws ResourceNotFoundException;

  /**
   * Añade un nuevo cliente a la lista que tiene asociada la mascota
   * 
   * @param petId                    ID de la mascota
   * @param clientNationalIdDocument NIF/NIE del cliente a añadir
   * @throws ResourceNotFoundException En caso de no encontrar alguno de los recursos
   */
  @Transactional
  void addClient(Long petId, String clientNationalIdDocument) throws ResourceNotFoundException;

  /**
   * Elimina un cliente a la lista que tiene asociada la mascota
   * 
   * @param petId    ID de la mascota
   * @param clientId Id del cliente a eliminar
   * @throws ResourceNotFoundException En caso de no encontrar alguno de los recursos
   */
  @Transactional
  void removeClient(Long petId, Long clientId) throws ResourceNotFoundException;

  /**
   * Elimina una mascota del sistema dado el id del animal
   * 
   * @param id ID de la mascota
   * @throws ResourceNotFoundException En caso de no encontrar alguno de los recursos
   */
  @Transactional
  void delete(Long id) throws ResourceNotFoundException;
}
