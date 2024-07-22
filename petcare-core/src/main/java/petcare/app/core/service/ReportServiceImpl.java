package petcare.app.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import petcare.app.core.model.repository.ReportRepository;
import petcare.app.domain.entity.Report;

/** Implementación del servicio de informes veterinarios */
@Service
public class ReportServiceImpl implements IReportService {

  /** Repositorio de informes veterinarios */
  private ReportRepository reportRepository;

  /**
   * Setter - ReportRepository. Inyección de dependencias
   * 
   * @param reportRepository Repositorio de Informes
   */
  @Autowired
  public void setReportRepository(ReportRepository reportRepository) {
    this.reportRepository = reportRepository;
  }

  @Override
  public Optional<Report> findById(Long reportId) {

    return reportRepository.findById(reportId);
  }

  @Override
  public List<Report> findByPetId(Long petId, Pageable pageable) {
    return reportRepository.findByPetId(petId, pageable);
  }

  @Override
  public List<Report> findByClientId(Long clientId, Pageable pageable) {
    return reportRepository.findByClientId(clientId, pageable);
  }

  @Override
  public List<Report> findByClientNationalIdDocument(String clientNationalIdDocument, Pageable pageable) {
    return reportRepository.findByClientNationalIdDocument(clientNationalIdDocument, pageable);
  }

  @Override
  public List<Report> findByVetId(Long vetId, Pageable pageable) {
    return reportRepository.findByVetId(vetId, pageable);
  }

  @Override
  public List<Report> findByVetEntityId(Long vetEntityId, Pageable pageable) {
    return reportRepository.findByVetId(vetEntityId, pageable);
  }

}
