package petcare.app.core.service.query;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import petcare.app.domain.entity.Vet;
import petcare.app.domain.repository.VetRepository;

/** Implementación del servicio de acceso a datos referentes al veterinario */
@Service
public class VetQueryServiceImpl implements IVetQueryService {

  /** Repositorio de veterinarios */
  private VetRepository vetRepository;

  /**
   * Setter - vetRepository. Inyección de dependencias
   * 
   * @param vetRepository Repositorio de veterinarios
   */
  @Autowired
  public void setVetRepository(VetRepository vetRepository) {
    this.vetRepository = vetRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Vet> findById(Long id) {
    return vetRepository.findById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Vet> findByNameAndVetEntityId(String name, Long vetEntityId, Pageable pageable) {
    return vetRepository.findByNameAndVetEntityId(name, vetEntityId, pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Vet> findByVetEntityId(Long vetEntityId, Pageable pageable) {
    return vetRepository.findByVetEntityId(vetEntityId, pageable);
  }

}
