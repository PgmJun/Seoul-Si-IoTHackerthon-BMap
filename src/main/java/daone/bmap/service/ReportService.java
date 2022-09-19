package daone.bmap.service;


import daone.bmap.domain.report.Report;
import daone.bmap.domain.report.ReportRepository;
import daone.bmap.dto.report.ReportMapper;
import daone.bmap.dto.report.ReportResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReportService {
    private final ReportRepository reportRepository;


    public void save(Report report) {
        try {
            reportRepository.save(report);
        } catch (Exception e) {
            throw e;
        }
    }

    public Report findReportByReportId(Long reportId) {
        return reportRepository.findReportByreportId(reportId)
                .orElseThrow(() -> new NoSuchElementException("Report with reportId:" + reportId + " does not exist"));
    }

    public List<ReportResponseDto> findReportAll() {
        return setPrkplceNo(reportRepository.findAll());
    }

    private List<ReportResponseDto> setPrkplceNo(List<Report> reportList) {
        try {
            List<ReportResponseDto> result = new ArrayList<>();
            for (Report r : reportList) {
                ReportResponseDto reportResponseDto = ReportMapper.mapper.reportEntityToDto(r);
                reportResponseDto.setPrkplceNo(r.getPark().getPrkplceNo());
                result.add(reportResponseDto);
            }
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

}
