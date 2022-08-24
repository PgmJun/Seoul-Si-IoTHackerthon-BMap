package daone.bmap.service;


import daone.bmap.domain.report.Report;
import daone.bmap.domain.report.ReportRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ReportService {
    private final ReportRepository reportRepository;


    public void save(Report report){
        reportRepository.save(report);
    }
//
//    List<Report> findReportByreportId(Long reportId){
//
//    }
//
//    List<Report> findAll(){
//
//    }

}
