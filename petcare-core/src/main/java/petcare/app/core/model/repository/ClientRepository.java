package petcare.app.core.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import petcare.app.domain.entity.Client;

/** Repositorio de acceso a los datos de un cliente */
public interface ClientRepository extends CrudRepository<Client, Long> {

  /**
   * Busca un cliente dado su documento nacional de identidad (NIF/NIE)
   * 
   * @param nationalIdDocument Documento nacional de identidad (NIF/NIE)
   * @return Optional(Client) - Un objeto del tipo Optional que podrá contener el cliente en caso de ser encontrado
   */
  Optional<Client> findByNationalIdDocument(String nationalIdDocument);

  /**
   * Busca todos los clientes asociados con una mascota
   * 
   * @param petId ID de la mascota
   * @return List(Client) - Todos los clientes que cumplen con los requisitos
   */
  List<Client> findByPetsId(Long petId);

}
