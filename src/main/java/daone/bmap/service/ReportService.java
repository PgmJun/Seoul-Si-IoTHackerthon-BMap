package daone.bmap.service;


import daone.bmap.domain.park.Park;
import daone.bmap.domain.report.Report;
import daone.bmap.domain.report.ReportRepository;
import daone.bmap.dto.park.ParkDto;
import daone.bmap.dto.park.ParkMapper;
import daone.bmap.dto.report.ReportMapper;
import daone.bmap.dto.report.ReportResponseDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReportService {
    private final ReportRepository reportRepository;


    public void save(Report report) {
        reportRepository.save(report);
    }

    public Optional<Report> findReportByreportId(Long reportId) {
        Optional<Report> opReport = reportRepository.findReportByreportId(reportId);
        if (opReport.isEmpty())
            log.error("::INFO:: ReportService.java -> findReportByreportId / reportId 가진 신고 데이터 찾지 못함. ");

        return opReport;
    }

    public List<ReportResponseDto> findReportAll() {
        List<ReportResponseDto> result = new ArrayList<>();
        try {
            List<Report> reportList = reportRepository.findAll();
            for (Report r : reportList) {
                result.add(ReportMapper.mapper.reportEntityToDto(r));
            }
        } catch (Exception e) {
            log.error("::ERROR:: ReportService.java -> findReportAll");
        }
        return result;
    }

}
