package petcare.app.domain.utils.conversor;

import petcare.app.domain.dto.VetDto;
import petcare.app.domain.entity.Vet;

/** Conversor de entidades entre un objeto de la clase Vet y un objeto de la clase VetDto */
public class VetDtoConversor {

  /** Constructor privado para evitar inicialización. Clase de métodos estáticos */
  private VetDtoConversor() {

  }

  /**
   * Conversor de un objeto Vet a un objeto VetDto
   * 
   * @param vet Vet a convertir
   * @return VetDto
   */
  public static VetDto toVetDto(Vet vet) {

    VetDto vetDto = new VetDto();

    vetDto.setId(vet.getId());
    vetDto.setNationalIdDocument(vet.getNationalIdDocument());
    vetDto.setRegistrationNumber(vet.getRegistrationNumber());
    vetDto.setName(vet.getName());

    if (vet.getVetEntity() != null) {
      vetDto.setVetEntityDto(VetEntityDtoConversor.toVetEntityDto(vet.getVetEntity()));
    }

    return vetDto;
  }

  /**
   * Conversor de un objeto VetDto a un objeto Vet
   * 
   * @param vetDto VetDto a convertir
   * @return Vet
   */
  public static Vet toVet(VetDto vetDto) {

    Vet vet = new Vet();

    vet.setId(vetDto.getId());
    vet.setNationalIdDocument(vetDto.getNationalIdDocument());
    vet.setRegistrationNumber(vetDto.getRegistrationNumber());
    vet.setName(vetDto.getName());

    if (vetDto.getVetEntityDto() != null) {
      vet.setVetEntity(VetEntityDtoConversor.toVetEntity(vetDto.getVetEntityDto()));
    }

    return vet;
  }

}
