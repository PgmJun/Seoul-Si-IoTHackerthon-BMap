package daone.bmap.service;


import daone.bmap.domain.park.Park;
import daone.bmap.domain.park.ParkRepository;
import daone.bmap.domain.report.Report;
import daone.bmap.domain.report.ReportRepository;
import daone.bmap.dto.report.ReportRequestDto;
import daone.bmap.dto.report.ReportResponseDto;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final ParkRepository parkRepository;


    public void saveReport(ReportRequestDto request) {
        Park park = parkRepository.findByPrkplceNo(request.getPrkplceNo())
                .orElseThrow(() -> new NoSuchElementException(
                        "Parking lot with prkplceNo:" + request.getPrkplceNo() + " does not exist"));
        Report report = Report.ofEntityAndDto(park, request);

        reportRepository.save(report);
    }

    public ReportResponseDto findReportByReportId(Long reportId) {
        Report report = reportRepository.findReportByreportId(reportId)
                .orElseThrow(() -> new NoSuchElementException("Report with reportId:" + reportId + " does not exist"));

        return ReportResponseDto.fromEntity(report);
    }

    public List<ReportResponseDto> findReportAll() {
        return setPrkplceNo(reportRepository.findAll());
    }

    private List<ReportResponseDto> setPrkplceNo(List<Report> reports) {
        return reports.stream()
                .map(ReportResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

}
