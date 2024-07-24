package petcare.app.core.service.query;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import petcare.app.domain.entity.Client;
import petcare.app.domain.repository.ClientRepository;

/** Implementación del servicio de acceso a datos referentes a un cliente o usuario */
@Service
public class ClientServiceQueryImpl implements IClientQueryService {

  /** Repositorio de clientes */
  private ClientRepository clientRepository;

  /**
   * Setter - ClientRepository. Inyección de dependencias
   * 
   * @param clientRepository Repositorio de clientes
   */
  @Autowired
  public void setClientRepository(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Client> findById(Long id) {

    return clientRepository.findById(id);
  }

}
