package petcare.app.domain.utils.conversor.simple;

import petcare.app.domain.dto.simple.PetDtoSimple;
import petcare.app.domain.entity.Pet;

/** Conversor de entidades entre un objeto de la clase Pet y un objeto de la clase PetDtoSimple */
public class PetDtoSimpleConversor {

  /** Constructor privado para evitar inicialización. Clase de métodos estáticos */
  private PetDtoSimpleConversor() {

  }

  /**
   * Conversor de un objeto Pet a un objeto PetDtoSimple
   * 
   * @param pet Pet a convertir
   * @return PetDtoSimple
   */
  public static PetDtoSimple toPetDtoSimple(Pet pet) {

    PetDtoSimple petDtoSimple = new PetDtoSimple();

    petDtoSimple.setId(pet.getId());
    petDtoSimple.setName(pet.getName());
    petDtoSimple.setAlive(pet.getAlive());

    return petDtoSimple;
  }

  /**
   * Conversor de un objeto PetDtoSimple a un objeto Pet
   * 
   * @param petDtoSimple PetDtoSimple a convertir
   * @return Pet
   */
  public static Pet toPet(PetDtoSimple petDtoSimple) {

    Pet pet = new Pet();

    pet.setId(petDtoSimple.getId());
    pet.setName(petDtoSimple.getName());

    pet.setAlive(petDtoSimple.getAlive());

    return pet;
  }

}
