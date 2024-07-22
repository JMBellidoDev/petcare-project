package petcare.app.core.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import petcare.app.core.service.query.IClientQueryService;
import petcare.app.domain.dto.ClientDto;
import petcare.app.domain.entity.Client;
import petcare.app.domain.utils.conversor.ClientDtoConversor;

/** Controlador de acceso a la información de un cliente */
@RestController
@RequestMapping("/clients")
public class ClientQueryController {

  /** Servicio de consultas sobre clientes */
  private IClientQueryService clientQueryService;

  /**
   * Setter - clientQueryService. Inyección de dependencias
   * 
   * @param clientQueryService Servicio de consultas sobre clientes
   */
  @Autowired
  public void setClientQueryService(IClientQueryService clientQueryService) {
    this.clientQueryService = clientQueryService;
  }

  @GetMapping("/find/{id}")
  public ClientDto findById(@PathVariable Long id) {

    Optional<Client> optClient = clientQueryService.findById(id);
    ClientDto dto;

    if (optClient.isPresent()) {
      dto = ClientDtoConversor.toClientDto(optClient.get());

    } else {
      dto = null;
    }

    return dto;
  }

}
