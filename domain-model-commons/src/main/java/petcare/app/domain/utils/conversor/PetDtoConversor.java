package petcare.app.domain.utils.conversor;

import petcare.app.domain.dto.PetDto;
import petcare.app.domain.entity.Pet;

/** Conversor de entidades entre un objeto de la clase Pet y un objeto de la clase PetDto */
public class PetDtoConversor {

  /** Constructor privado para evitar inicialización. Clase de métodos estáticos */
  private PetDtoConversor() {

  }

  /**
   * Conversor de un objeto Pet a un objeto PetDto
   * 
   * @param pet Pet a convertir
   * @return PetDto
   */
  public static PetDto toPetDto(Pet pet) {

    PetDto petDto = new PetDto();

    petDto.setId(pet.getId());
    petDto.setChipNumber(pet.getChipNumber());
    petDto.setType(pet.getType());
    petDto.setBreed(pet.getBreed());
    petDto.setName(pet.getName());
    petDto.setBirthdate(pet.getBirthdate());

    petDto.setAlive(pet.getAlive());
    petDto.setCastrated(pet.getCastrated());

    if (pet.getClients() != null) {
      petDto.setClientsDto(pet.getClients().stream().map(ClientDtoConversor::toClientDto).toList());
    }

    return petDto;
  }

  /**
   * Conversor de un objeto PetDto a un objeto Pet
   * 
   * @param petDto PetDto a convertir
   * @return Pet
   */
  public static Pet toPet(PetDto petDto) {

    Pet pet = new Pet();

    pet.setId(petDto.getId());
    pet.setChipNumber(petDto.getChipNumber());
    pet.setType(petDto.getType());
    pet.setBreed(petDto.getBreed());
    pet.setName(petDto.getName());
    pet.setBirthdate(petDto.getBirthdate());

    pet.setAlive(petDto.getAlive());
    pet.setCastrated(petDto.getCastrated());

    if (petDto.getClientsDto() != null) {
      pet.setClients(petDto.getClientsDto().stream().map(ClientDtoConversor::toClient).toList());
    }

    return pet;
  }

}
