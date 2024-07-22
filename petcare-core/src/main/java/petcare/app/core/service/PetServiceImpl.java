package petcare.app.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import petcare.app.core.model.repository.ClientRepository;
import petcare.app.core.model.repository.PetRepository;
import petcare.app.core.utils.ExceptionMessages;
import petcare.app.core.utils.exceptions.ResourceNotFoundException;
import petcare.app.domain.entity.Client;
import petcare.app.domain.entity.Pet;

/** Implementación del servicio de mascotas */
@Service
public class PetServiceImpl implements IPetService {

  /** Repositorio de mascotas */
  private PetRepository petRepository;

  /** Repositorio de clientes */
  private ClientRepository clientRepository;

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
   * @param clientRepository Repositorio de mascotas
   */
  @Autowired
  public void setClientRepository(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Pet> findById(Long id) {
    return petRepository.findById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Pet> findByClientId(Long clientId, Pageable pageable) {
    return petRepository.findByClientsIdOrderByAliveDesc(clientId, pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Pet> findByClientNationalIdDocument(String clientNationalIdDocument, Pageable pageable) {
    return petRepository.findByClientNationalIdDocument(clientNationalIdDocument, pageable);
  }

  @Override
  @Transactional
  public Pet save(Pet pet) {

    return petRepository.save(pet);
  }

  @Override
  @Transactional
  public Pet update(Pet pet, Long id) throws ResourceNotFoundException {

    Optional<Pet> optSavedPet = petRepository.findById(id);

    // Se obtiene la mascota del sistema y se modifican los datos
    if (optSavedPet.isPresent()) {

      Pet savedPet = optSavedPet.get();

      savedPet.setChipNumber(pet.getChipNumber());
      savedPet.setName(pet.getName());
      savedPet.setType(pet.getType());
      savedPet.setBreed(pet.getBreed());
      savedPet.setBirthdate(pet.getBirthdate());

      savedPet.setCastrated(pet.getCastrated());
      savedPet.setAlive(pet.getAlive());

      return petRepository.save(savedPet);

      // Se lanza la excepción si no se encuentra la mascota
    } else {
      throw new ResourceNotFoundException(String.format("No se pudo encontrar la mascota con ID: %d", id));
    }
  }

  @Override
  @Transactional
  public void addClient(Long petId, String clientNationalIdDocument) throws ResourceNotFoundException {

    // Se buscan la mascota y el cliente
    Pet foundPet = petRepository
        .findById(petId)
        .orElseThrow(() -> new ResourceNotFoundException(String.format(ExceptionMessages.PET_NOT_FOUND_BY_ID, petId)));

    Client foundClient = clientRepository
        .findByNationalIdDocument(clientNationalIdDocument)
        .orElseThrow(() -> new ResourceNotFoundException(
            String.format(ExceptionMessages.CLIENT_NOT_FOUND_BY_NATIONAL_ID_DOCUMENT, clientNationalIdDocument)));

    // Se añaden el cliente y mascota en la relación bidireccional
    foundPet.getClients().add(foundClient);
    foundClient.getPets().add(foundPet);

    // Se guardan en la base de datos
    petRepository.save(foundPet);
    clientRepository.save(foundClient);

  }

  @Override
  @Transactional
  public void removeClient(Long petId, Long clientId) throws ResourceNotFoundException {

    // Se buscan la mascota y el cliente
    Pet foundPet = petRepository
        .findById(petId)
        .orElseThrow(() -> new ResourceNotFoundException(String.format(ExceptionMessages.PET_NOT_FOUND_BY_ID, petId)));

    Client foundClient = clientRepository
        .findById(clientId)
        .orElseThrow(
            () -> new ResourceNotFoundException(String.format(ExceptionMessages.CLIENT_NOT_FOUND_BY_ID, clientId)));

    // Se eliminan de la relación bidireccional
    foundPet.getClients().remove(foundClient);
    foundClient.getPets().remove(foundPet);

    // Se guardan en la base de datos
    petRepository.save(foundPet);
    clientRepository.save(foundClient);

  }

  @Override
  @Transactional
  public void delete(Long id) throws ResourceNotFoundException {

    Optional<Pet> optSavedPet = petRepository.findById(id);

    // Si se encuentra la mascota
    if (optSavedPet.isPresent()) {

      // Se eliminan las asociaciones cliente - mascota
      List<Client> clients = clientRepository.findByPetsId(id);
      clients.forEach(client -> {

        client.getPets().remove(optSavedPet.get());
        clientRepository.save(client);
      });

      // Se elimina la mascota
      petRepository.deleteById(id);

    } else {
      throw new ResourceNotFoundException(String.format("No se pudo encontrar la mascota con ID: %d", id));
    }
  }

}
