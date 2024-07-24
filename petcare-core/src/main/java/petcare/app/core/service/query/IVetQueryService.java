package petcare.app.core.service.query;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import petcare.app.domain.entity.Vet;

/** Interfaz del Servicio de acceso a los datos referentes a un veterinario */
@Service
public interface IVetQueryService {

  /**
   * Busca un veterinario dado su ID
   * 
   * @param id ID del veterinario a buscar
   * @return Optional(Vet) - Un objeto de la clase Optional que podrá contener el veterinario en caso de encontrarlo en
   *         el sistema
   */
  @Transactional(readOnly = true)
  Optional<Vet> findById(Long id);

  /**
   * Busca todos los veterinarios pertenecientes a la misma entidad o clínica cuyo nombre contenga al aportado <br/>
   * El objeto obtenido será paginable a través de los parámetros del buscador
   * 
   * @param name        Nombre del veterinario
   * @param vetEntityId ID asociado a la entidad o clínica veterinaria
   * @param pageable    Sistema de paginación
   * @return List(Vet) - Una lista con todos los veterinarios que cumplen la condición
   */
  @Transactional(readOnly = true)
  List<Vet> findByNameAndVetEntityId(String name, Long vetEntityId, Pageable pageable);

  /**
   * Busca todos los veterinarios pertenecientes a la misma entidad o clínica veterinaria <br/>
   * El objeto obtenido será paginable a través de los parámetros del buscador
   * 
   * @param vetEntityId ID asociado a la entidad o clínica veterinaria
   * @param pageable    Sistema de paginación
   * @return List(Vet) - Una lista con todos los veterinarios que cumplen la condición
   */
  @Transactional(readOnly = true)
  List<Vet> findByVetEntityId(Long vetEntityId, Pageable pageable);

}
