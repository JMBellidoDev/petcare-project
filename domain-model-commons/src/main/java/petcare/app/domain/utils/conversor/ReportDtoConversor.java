package petcare.app.domain.utils.conversor;

import petcare.app.domain.dto.PetDto;
import petcare.app.domain.dto.ReportDto;
import petcare.app.domain.entity.Pet;
import petcare.app.domain.entity.Report;
import petcare.app.domain.utils.conversor.simple.VetDtoSimpleConversor;

/** Conversor de entidades entre un objeto de la clase Report y un objeto de la clase ReportDto */
public class ReportDtoConversor {

  /** Constructor privado para evitar inicialización. Clase de métodos estáticos */
  private ReportDtoConversor() {

  }

  /**
   * Conversor de un objeto Report a un objeto ReportDto
   * 
   * @param report Report a convertir
   * @return ReportDto
   */
  public static ReportDto toReportDto(Report report) {

    ReportDto reportDto = new ReportDto();

    reportDto.setId(report.getId());
    reportDto.setReason(report.getReason());
    reportDto.setTreatment(report.getTreatment());
    reportDto.setDiagnosis(report.getDiagnosis());
    reportDto.setReportDate(report.getReportDate());

    if (report.getPet() != null) {

      PetDto pet = new PetDto();
      pet.setId(report.getPet().getId());
      pet.setName(report.getPet().getName());

      reportDto.setPetDto(pet);
    }

    if (report.getVet() != null) {
      reportDto.setVetDtoSimple(VetDtoSimpleConversor.toVetDtoSimple(report.getVet()));
    }

    return reportDto;
  }

  /**
   * Conversor de un objeto ReportDto a un objeto Report
   * 
   * @param reportDto ReportDto a convertir
   * @return Report
   */
  public static Report toReport(ReportDto reportDto) {

    Report report = new Report();

    report.setId(reportDto.getId());
    report.setReason(reportDto.getReason());
    report.setDiagnosis(reportDto.getDiagnosis());
    report.setTreatment(reportDto.getTreatment());
    report.setReportDate(reportDto.getReportDate());

    if (reportDto.getPetDto() != null) {

      Pet pet = new Pet();
      pet.setId(reportDto.getPetDto().getId());
      pet.setName(reportDto.getPetDto().getName());

      report.setPet(pet);
    }

    if (reportDto.getVetDtoSimple() != null) {
      report.setVet(VetDtoSimpleConversor.toVet(reportDto.getVetDtoSimple()));
    }

    return report;

  }

}
