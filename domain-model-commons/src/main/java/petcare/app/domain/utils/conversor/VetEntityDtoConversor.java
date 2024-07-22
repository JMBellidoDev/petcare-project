package petcare.app.domain.utils.conversor;

import petcare.app.domain.dto.VetEntityDto;
import petcare.app.domain.entity.VetEntity;

/** Conversor de entidades entre un objeto de la clase VetEntity y un objeto de la clase VetEntityDto */
public class VetEntityDtoConversor {

  /** Constructor privado para evitar inicialización. Clase de métodos estáticos */
  private VetEntityDtoConversor() {

  }

  /**
   * Conversor de un objeto VetEntity a un objeto VetEntityDto
   * 
   * @param vetEntity VetEntity a convertir
   * @return VetEntityDto
   */
  public static VetEntityDto toVetEntityDto(VetEntity vetEntity) {

    VetEntityDto vetEntityDto = new VetEntityDto();

    vetEntityDto.setId(vetEntity.getId());
    vetEntityDto.setName(vetEntity.getName());
    vetEntityDto.setCif(vetEntity.getCif());
    vetEntityDto.setAddress(vetEntity.getAddress());
    vetEntityDto.setPhoneNumber(vetEntity.getPhoneNumber());

    return vetEntityDto;
  }

  /**
   * Conversor de un objeto VetEntityDto a un objeto VetEntity
   * 
   * @param VetEntityDto VetEntityDto a convertir
   * @return VetEntity
   */
  public static VetEntity toVetEntity(VetEntityDto vetEntityDto) {

    VetEntity vetEntity = new VetEntity();

    vetEntity.setId(vetEntityDto.getId());
    vetEntity.setName(vetEntityDto.getName());
    vetEntity.setCif(vetEntityDto.getCif());
    vetEntity.setAddress(vetEntityDto.getAddress());
    vetEntity.setPhoneNumber(vetEntityDto.getPhoneNumber());

    return vetEntity;
  }

}
