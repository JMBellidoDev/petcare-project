package petcare.app.domain.utils.conversor.simple;

import petcare.app.domain.dto.simple.ClientDtoSimple;
import petcare.app.domain.entity.Client;

/** Conversor de entidades entre un objeto de la clase Client y un objeto de la clase ClientDtoSimple */
public class ClientDtoSimpleConversor {

  /** Constructor privado para evitar inicialización. Clase de métodos estáticos */
  private ClientDtoSimpleConversor() {

  }

  /**
   * Conversor de un objeto Client a un objeto ClientDtoSimple
   * 
   * @param Client client a convertir
   * @return ClientDtoSimple
   */
  public static ClientDtoSimple toClientDtoSimple(Client client) {

    ClientDtoSimple clientDtoSimple = new ClientDtoSimple();

    clientDtoSimple.setId(client.getId());
    clientDtoSimple.setName(client.getName());

    return clientDtoSimple;

  }

  /**
   * Conversor de un objeto ClientDtoSimple a un objeto Client
   * 
   * @param ClientDtoSimple ClientDtoSImple a convertir
   * @return Client
   */
  public static Client toClient(ClientDtoSimple clientDtoSimple) {

    Client client = new Client();

    client.setId(clientDtoSimple.getId());
    client.setName(clientDtoSimple.getName());

    return client;

  }

}
