package petcare.app.core.service.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import petcare.app.core.model.repository.VetEntityRepository;
import petcare.app.domain.entity.VetEntity;

/** Implementación del servicio de acceso a datos referentes a una entidad veterinaria */
@Service
public class VetEntityQueryServiceImpl implements IVetEntityQueryService {

  /** Repositorio de entidades veterinarias */
  private VetEntityRepository vetEntityRepository;

  /**
   * Setter - VetEntityRepository. Inyección de dependencias
   * 
   * @param vetEntityRepository - Repositorio de entidades veterinarias
   */
  @Autowired
  public void setVetEntityRepository(VetEntityRepository vetEntityRepository) {
    this.vetEntityRepository = vetEntityRepository;
  }

  @Override
  public Optional<VetEntity> findById(Long id) {

    return vetEntityRepository.findById(id);
  }

  @Override
  public List<VetEntity> findByNameContaining(String name, Pageable pageable) {

    return vetEntityRepository.findByNameContaining(name, pageable);

  }

}
