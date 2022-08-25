package daone.bmap.domain.report;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Report save(Report report);

    //reportId로 Report 찾기
    Optional<Report> findReportByreportId(Long reportId);

    List<Report> findReportByReportType(ReportType reportType);

    //모든 Report 찾기
    List<Report> findAll();
}
