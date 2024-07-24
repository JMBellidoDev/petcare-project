package petcare.app.domain.utils.conversor.simple;

import petcare.app.domain.dto.simple.VetDtoSimple;
import petcare.app.domain.entity.Vet;
import petcare.app.domain.utils.conversor.VetEntityDtoConversor;

/** Conversor de entidades entre un objeto de la clase Vet y un objeto de la clase VetDtoSimple */
public class VetDtoSimpleConversor {

  /** Constructor privado para evitar inicialización. Clase de métodos estáticos */
  private VetDtoSimpleConversor() {

  }

  /**
   * Conversor de un objeto Vet a un objeto VetDtoSimple
   * 
   * @param vet Vet a convertir
   * @return VetDtoSimple
   */
  public static VetDtoSimple toVetDtoSimple(Vet vet) {

    VetDtoSimple vetDtoSimple = new VetDtoSimple();

    vetDtoSimple.setId(vet.getId());
    vetDtoSimple.setName(vet.getName());

    if (vet.getVetEntity() != null) {
      vetDtoSimple.setVetEntityDto(VetEntityDtoConversor.toVetEntityDto(vet.getVetEntity()));
    }

    return vetDtoSimple;
  }

  /**
   * Conversor de un objeto VetDtoSimple a un objeto Vet
   * 
   * @param vetDtoSimple VetDtoSimple a convertir
   * @return Vet
   */
  public static Vet toVet(VetDtoSimple vetDtoSimple) {

    Vet vet = new Vet();

    vet.setId(vetDtoSimple.getId());
    vet.setName(vetDtoSimple.getName());

    if (vetDtoSimple.getVetEntityDto() != null) {
      vet.setVetEntity(VetEntityDtoConversor.toVetEntity(vetDtoSimple.getVetEntityDto()));
    }

    return vet;
  }

}
