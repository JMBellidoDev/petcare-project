package petcare.app.core.service.query;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import petcare.app.domain.entity.VetEntity;

/**
 * Interfaz del Servicio de acceso a los datos referentes a una entidad veterinaria (clínica, hospital, protectora de
 * animales, etc.)
 */
@Service
public interface IVetEntityQueryService {

  /**
   * Busca una entidad veterinaria dado su ID
   * 
   * @param id ID de la entidad
   * @return Optional(VetEntity) - Un objeto de la clase Optional que podrá contener la entidad veterinaria en caso de
   *         encontrarla en el sistema
   */
  @Transactional(readOnly = true)
  Optional<VetEntity> findById(Long id);

  /**
   * Busca entidades dado una parte de su nombre
   * 
   * @param name     Parte del nombre que debe tener la entidad
   * @param pageable Sistema de paginación
   * @return List(VetEntity) - Una lista con todas las entidades que cumplan la condición
   */
  @Transactional(readOnly = true)
  List<VetEntity> findByNameContaining(String name, Pageable pageable);

}
