package petcare.app.domain.utils.conversor;

import petcare.app.domain.dto.ClientDto;
import petcare.app.domain.entity.Client;

/** Conversor de entidades entre un objeto de la clase Client y un objeto de la clase ClientDto */
public class ClientDtoConversor {

  /** Constructor privado para evitar inicialización. Clase de métodos estáticos */
  private ClientDtoConversor() {

  }

  /**
   * Conversor de un objeto Client a un objeto ClientDto
   * 
   * @param Client client a convertir
   * @return ClientDto
   */
  public static ClientDto toClientDto(Client client) {

    ClientDto clientDto = new ClientDto();

    clientDto.setId(client.getId());

    clientDto.setName(client.getName());
    clientDto.setNationalIdDocument(client.getNationalIdDocument());
    clientDto.setAddress(client.getAddress());
    clientDto.setPhoneNumber(client.getPhoneNumber());
    clientDto.setBirthdate(client.getBirthdate());

    return clientDto;

  }

  /**
   * Conversor de un objeto ClientDto a un objeto Client
   * 
   * @param clientDto
   * @return Client
   */
  public static Client toClient(ClientDto clientDto) {

    Client client = new Client();

    client.setId(clientDto.getId());

    client.setName(clientDto.getName());
    client.setNationalIdDocument(client.getNationalIdDocument());
    client.setAddress(clientDto.getAddress());
    client.setPhoneNumber(client.getPhoneNumber());
    client.setBirthdate(clientDto.getBirthdate());

    return client;

  }

}
