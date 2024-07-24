package petcare.app.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import petcare.app.domain.dto.ReportDto;
import petcare.app.domain.entity.Pet;
import petcare.app.domain.entity.Report;
import petcare.app.domain.entity.Vet;
import petcare.app.domain.repository.PetRepository;
import petcare.app.domain.repository.ReportRepository;
import petcare.app.domain.repository.VetRepository;
import petcare.app.domain.utils.ExceptionMessages;
import petcare.app.domain.utils.conversor.ReportDtoConversor;
import petcare.app.domain.utils.exceptions.ResourceNotFoundException;

/** Implementación del servicio de informes veterinarios */
@Service
public class ReportServiceImpl implements IReportService {

  /** Repositorio de informes veterinarios */
  private ReportRepository reportRepository;

  /** Repositorio de veterinarios */
  private VetRepository vetRepository;

  /** Repositorio de mascotas */
  private PetRepository petRepository;

  /**
   * Setter - ReportRepository. Inyección de dependencias
   * 
   * @param reportRepository Repositorio de Informes
   */
  @Autowired
  public void setReportRepository(ReportRepository reportRepository) {
    this.reportRepository = reportRepository;
  }

  /**
   * Setter - VetRepository. Inyección de dependencias
   * 
   * @param vetRepository Repositorio de veterinarios
   */
  public void setVetRepository(VetRepository vetRepository) {
    this.vetRepository = vetRepository;
  }

  /**
   * Setter - PetRepository. Inyección de dependencias
   * 
   * @param petRepository Repositorio de mascotas
   */
  public void setPetRepository(PetRepository petRepository) {
    this.petRepository = petRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Report> findById(Long reportId) {

    return reportRepository.findById(reportId);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Report> findByPetId(Long petId, Pageable pageable) {
    return reportRepository.findByPetId(petId, pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Report> findByClientId(Long clientId, Pageable pageable) {
    return reportRepository.findByClientId(clientId, pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Report> findByClientNationalIdDocument(String clientNationalIdDocument, Pageable pageable) {
    return reportRepository.findByClientNationalIdDocument(clientNationalIdDocument, pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Report> findByVetId(Long vetId, Pageable pageable) {
    return reportRepository.findByVetId(vetId, pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Report> findByVetEntityId(Long vetEntityId, Pageable pageable) {
    return reportRepository.findByVetId(vetEntityId, pageable);
  }

  @Override
  @Transactional
  public ReportDto save(ReportDto reportDto) throws ResourceNotFoundException {

    Report report = ReportDtoConversor.toReport(reportDto);

    // Se obtienen el veterinario y mascota completos
    Vet vet = vetRepository
        .findById(reportDto.getVetDtoSimple().getId())
        .orElseThrow(() -> new ResourceNotFoundException(
            String.format(ExceptionMessages.VET_NOT_FOUND_BY_ID, reportDto.getVetDtoSimple().getId())));

    Pet pet = petRepository
        .findById(reportDto.getPetDto().getId())
        .orElseThrow(() -> new ResourceNotFoundException(
            String.format(ExceptionMessages.PET_NOT_FOUND_BY_ID, reportDto.getPetDto().getId())));

    // Se asignan las variables y se almacenan
    pet.getReports().add(report);

    report.setPet(pet);
    report.setVet(vet);

    Report savedReport = reportRepository.save(report);
    return ReportDtoConversor.toReportDto(savedReport);

  }

  @Override
  @Transactional
  public ReportDto update(ReportDto reportDto, Long id) throws ResourceNotFoundException {

    // Se busca el informe en el repositorio
    Optional<Report> optReport = reportRepository.findById(id);

    // Si se encuentra
    if (optReport.isPresent()) {

      // Se asignan los atributos base
      Report report = optReport.get();

      report.setReason(reportDto.getReason());
      report.setDiagnosis(reportDto.getDiagnosis());
      report.setTreatment(reportDto.getTreatment());
      report.setReportDate(reportDto.getReportDate());

      // Se obtienen el veterinario y mascota completos
      Vet vet = vetRepository
          .findById(reportDto.getVetDtoSimple().getId())
          .orElseThrow(() -> new ResourceNotFoundException(
              String.format(ExceptionMessages.VET_NOT_FOUND_BY_ID, reportDto.getVetDtoSimple().getId())));

      Pet pet = petRepository
          .findById(reportDto.getPetDto().getId())
          .orElseThrow(() -> new ResourceNotFoundException(
              String.format(ExceptionMessages.PET_NOT_FOUND_BY_ID, reportDto.getPetDto().getId())));

      // Se asignan las variables y se almacenan
      pet.getReports().add(report);

      report.setPet(pet);
      report.setVet(vet);

      Report savedReport = reportRepository.save(report);
      return ReportDtoConversor.toReportDto(savedReport);

    } else {
      throw new ResourceNotFoundException(String.format(ExceptionMessages.REPORT_NOT_FOUND_BY_ID, id));
    }

  }

  @Override
  @Transactional
  public void delete(Long id) throws ResourceNotFoundException {

    // Se busca el informe en el repositorio
    Optional<Report> optReport = reportRepository.findById(id);

    // Si se encuentra
    if (optReport.isPresent()) {

      // Se elimina de los informes de la mascota y luego se elimina el informe
      Pet pet = petRepository
          .findById(optReport.get().getPet().getId())
          .orElseThrow(() -> new ResourceNotFoundException(
              String.format(ExceptionMessages.PET_NOT_FOUND_BY_ID, optReport.get().getPet().getId())));

      pet.getReports().remove(optReport.get());
      petRepository.save(pet);

      reportRepository.deleteById(id);

    } else {
      throw new ResourceNotFoundException(String.format(ExceptionMessages.REPORT_NOT_FOUND_BY_ID, id));
    }

  }

}
