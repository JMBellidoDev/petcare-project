package petcare.app.core.service.query;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import petcare.app.domain.entity.Client;

/** Interfaz del Servicio de acceso a los datos referentes a cliente de la aplicación o usuario */
@Service
public interface IClientQueryService {

  /**
   * Busca un cliente o usuario dado su ID
   * 
   * @param id ID del cliente o usuario
   * @return Optional(Client) - Un objeto de la clase Optional que podrá contener el usuario en caso de encontrarlo en
   *         el sistema
   */
  @Transactional(readOnly = true)
  public Optional<Client> findById(Long id);

}
