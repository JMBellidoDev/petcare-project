package petcare.app.core.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import petcare.app.core.service.query.IVetEntityQueryService;
import petcare.app.domain.dto.VetEntityDto;
import petcare.app.domain.entity.VetEntity;
import petcare.app.domain.utils.conversor.VetEntityDtoConversor;

/** Controlador de acceso a la información de una entidad veterinaria */
@RestController
@RequestMapping("/vet-entities")
public class VetEntityQueryController {

  /** Servicio de consultas a entidades veterinarias */
  private IVetEntityQueryService vetEntityQueryService;

  /**
   * Setter - VetEntityQueryService. Inyección de dependencias
   * 
   * @param vetEntityQueryService Servicio de consultas a entidades veterinarias
   */
  @Autowired
  public void setVetEntityQueryService(IVetEntityQueryService vetEntityQueryService) {
    this.vetEntityQueryService = vetEntityQueryService;
  }

  @GetMapping("/find/{id}")
  public VetEntityDto findById(@PathVariable Long id) {

    Optional<VetEntity> foundVetEntity = vetEntityQueryService.findById(id);
    VetEntityDto dto;

    if (foundVetEntity.isPresent()) {
      dto = VetEntityDtoConversor.toVetEntityDto(foundVetEntity.get());

    } else {
      dto = null;
    }

    return dto;
  }

  @GetMapping("/find/name/{name}")
  public List<VetEntityDto> findByNameContaining(@PathVariable String name, Pageable pageable) {

    return vetEntityQueryService
        .findByNameContaining(name, pageable)
        .stream()
        .map(VetEntityDtoConversor::toVetEntityDto)
        .toList();

  }

}
