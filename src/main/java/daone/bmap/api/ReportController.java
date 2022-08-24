package daone.bmap.api;

import daone.bmap.domain.report.Report;
import daone.bmap.domain.report.ReportType;
import daone.bmap.dto.report.ReportMapper;
import daone.bmap.dto.report.ReportRequestDto;
import daone.bmap.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/report")
@RestController
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/add")
    public ResponseEntity<String> saveReport(@RequestBody ReportRequestDto data){
        try {
            ReportType reportType;
            if (data.getReportType() == 0) reportType = ReportType.ERROR;
            else if (data.getReportType() == 1) reportType = ReportType.PARKING;
            else {
                return new ResponseEntity<>("잘못된 reportType", HttpStatus.BAD_REQUEST);
                log.error("::ERROR:: ReportController.java -> saveReport / 잘못된 reportType");
            }
            Report report;
            reportService.save(report);
            return ResponseEntity.ok("신고 접수가 완료되었습니다!");
        } catch (Exception e){
            log.error("::ERROR:: ReportController.java -> saveReport");
            return new ResponseEntity<>("신고 실패", HttpStatus.NOT_FOUND);
        }
    }

}
