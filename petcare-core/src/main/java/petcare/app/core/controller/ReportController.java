package petcare.app.core.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import petcare.app.core.service.IReportService;
import petcare.app.domain.dto.ReportDto;
import petcare.app.domain.entity.Report;
import petcare.app.domain.utils.conversor.ReportDtoConversor;

/** Controlador para la gestión de informes veterinarios */
@RestController
@RequestMapping("/reports")
public class ReportController {

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

  @GetMapping("/find/id/{id}")
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

  @GetMapping("/find/pet-id/{petId}")
  public List<ReportDto> findByPetId(@PathVariable Long petId, Pageable pageable) {

    return reportService.findByPetId(petId, pageable).stream().map(ReportDtoConversor::toReportDto).toList();

  }

  @GetMapping("/find/client-id/{clientId}")
  public List<ReportDto> findByClientId(@PathVariable Long clientId, Pageable pageable) {

    return reportService.findByClientId(clientId, pageable).stream().map(ReportDtoConversor::toReportDto).toList();
  }

  @GetMapping("/find/client-national-id-document/{clientNationalIdDocument}")
  public List<ReportDto> findByClientId(@PathVariable String clientNationalIdDocument, Pageable pageable) {

    return reportService
        .findByClientNationalIdDocument(clientNationalIdDocument, pageable)
        .stream()
        .map(ReportDtoConversor::toReportDto)
        .toList();
  }

  @GetMapping("/find/vet-id/{vetId}")
  public List<ReportDto> findByVetId(@PathVariable Long vetId, Pageable pageable) {

    return reportService.findByVetId(vetId, pageable).stream().map(ReportDtoConversor::toReportDto).toList();
  }

  @GetMapping("/find/vet-entity-id/{vetEntityId}")
  public List<ReportDto> findByVetEntityId(@PathVariable Long vetEntityId, Pageable pageable) {

    return reportService
        .findByVetEntityId(vetEntityId, pageable)
        .stream()
        .map(ReportDtoConversor::toReportDto)
        .toList();
  }

}
