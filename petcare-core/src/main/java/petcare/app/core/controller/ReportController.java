package petcare.app.core.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import petcare.app.core.service.IReportService;
import petcare.app.domain.dto.ReportDto;
import petcare.app.domain.entity.Report;
import petcare.app.domain.utils.conversor.ReportDtoConversor;
import petcare.app.domain.utils.exceptions.ResourceNotFoundException;

/** Controlador para la gestión de informes veterinarios */
@RestController
@RequestMapping("/reports")
public class ReportController {

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

  /** Servicio de informes veterinarios */
  private IReportService reportService;

  /**
   * Setter - ReportService. Inyección de dependencias
   * 
   * @param reportService Servicio de informes veterinarios
   */
  @Autowired
  public void setReportService(IReportService reportService) {
    this.reportService = reportService;
  }

  /**
   * Busca un informe dado su ID
   * 
   * @param id       ID del informe a buscar
   * @param pageable Sistema de paginación
   * @return ReportDto
   */
  @GetMapping("/find/{id}")
  public ReportDto findById(@PathVariable Long id, Pageable pageable) {

    Optional<Report> optReport = reportService.findById(id);
    ReportDto dto;

    if (optReport.isPresent()) {
      dto = ReportDtoConversor.toReportDto(optReport.get());

    } else {
      dto = null;
    }

    return dto;
  }

  /**
   * Busca los informes referentes a una mascota del sistema
   * 
   * @param petId    ID de la mascota
   * @param pageable Sistema de paginación
   * @return List(ReportDto) - Lista con todos los informes encontrados
   */
  @GetMapping("/find/pet-id/{petId}")
  public List<ReportDto> findByPetId(@PathVariable Long petId, Pageable pageable) {

    return reportService.findByPetId(petId, pageable).stream().map(ReportDtoConversor::toReportDto).toList();

  }

  /**
   * Busca todos los informes referentes a mascotas asociadas con el cliente aportado
   * 
   * @param clientId ID del cliente
   * @param pageable Sistema de paginación
   * @return List(ReportDto) - Lista con todos los informes encontrados
   */
  @GetMapping("/find/client-id/{clientId}")
  public List<ReportDto> findByClientId(@PathVariable Long clientId, Pageable pageable) {

    return reportService.findByClientId(clientId, pageable).stream().map(ReportDtoConversor::toReportDto).toList();
  }

  /**
   * Busca todos los informes referentes a mascotas asociadas con el cliente aportado
   * 
   * @param clientNationalIdDocument Documento nacional de identidad del cliente (NIF/NIE)
   * @param pageable                 Sistema de paginación
   * @return List(ReportDto) - Lista con todos los informes encontrados
   */
  @GetMapping("/find/client-national-id-document/{clientNationalIdDocument}")
  public List<ReportDto> findByClientId(@PathVariable String clientNationalIdDocument, Pageable pageable) {

    return reportService
        .findByClientNationalIdDocument(clientNationalIdDocument, pageable)
        .stream()
        .map(ReportDtoConversor::toReportDto)
        .toList();
  }

  /**
   * Busca todos los informes emitidos por un veterinario
   * 
   * @param vetId    ID del veterinario
   * @param pageable Sistema de paginación
   * @return List(ReportDto) - Lista con todos los informes encontrados
   */
  @GetMapping("/find/vet-id/{vetId}")
  public List<ReportDto> findByVetId(@PathVariable Long vetId, Pageable pageable) {

    return reportService.findByVetId(vetId, pageable).stream().map(ReportDtoConversor::toReportDto).toList();
  }

  /**
   * Busca todos los informes emitidos por veterinarios de una entidad concreta
   * 
   * @param vetEntityId ID de la entidad veterinaria
   * @param pageable    Sistema de paginación
   * @return List(ReportDto) - Lista con todos los informes encontrados
   */
  @GetMapping("/find/vet-entity-id/{vetEntityId}")
  public List<ReportDto> findByVetEntityId(@PathVariable Long vetEntityId, Pageable pageable) {

    return reportService
        .findByVetEntityId(vetEntityId, pageable)
        .stream()
        .map(ReportDtoConversor::toReportDto)
        .toList();
  }

  /**
   * Almacena un nuevo informe en el sistema
   * 
   * @param reportDto DTO del informe a almacenar
   * @return ResponseEntity(ReportDto) - Respuesta con el reporte almacenado en formato DTO
   */
  @PostMapping
  public ResponseEntity<ReportDto> save(@RequestBody ReportDto reportDto) {

    ResponseEntity<ReportDto> result;

    try {
      ReportDto savedReportDto = reportService.save(reportDto);
      result = ResponseEntity.status(HttpStatus.CREATED).body(savedReportDto);

    } catch (ResourceNotFoundException e) {

      LOGGER.error(e.getMessage());
      result = ResponseEntity.notFound().build();
    }

    return result;
  }

  /**
   * Modifica un informe del sistema
   * 
   * @param reportDto DTO del informe ya modificado
   * @param id        ID del informe almacenado en el sistema
   * @return ResponseEntity(ReportDto) - Respuesta con el reporte modificado en formato DTO
   */
  @PutMapping("/{id}")
  public ResponseEntity<ReportDto> update(@RequestBody ReportDto reportDto, @PathVariable Long id) {

    ResponseEntity<ReportDto> result;

    try {
      ReportDto modifiedReportDto = reportService.update(reportDto, id);
      result = ResponseEntity.ok(modifiedReportDto);

    } catch (ResourceNotFoundException e) {

      LOGGER.error(e.getMessage());
      result = ResponseEntity.notFound().build();
    }

    return result;

  }

  /**
   * Elimina un informe del sistema
   * 
   * @param id ID del informe a eliminar
   * @return ResponeEntity(ReportDto) - Respuesta que indica si se ha podido realizar la operación
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<ReportDto> delete(@PathVariable Long id) {

    ResponseEntity<ReportDto> result;

    try {
      reportService.delete(id);
      result = ResponseEntity.ok().build();

    } catch (ResourceNotFoundException e) {

      LOGGER.error(e.getMessage());
      result = ResponseEntity.notFound().build();
    }

    return result;
  }

}
