package daone.bmap.api;

import daone.bmap.domain.report.Report;
import daone.bmap.dto.report.ReportMapper;
import daone.bmap.dto.report.ReportRequestDto;
import daone.bmap.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/report")
@RestController
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<String> saveReport(ReportRequestDto requestDto){
        try {
            Report report = ReportMapper.mapper.reportRequestDtoToEntity(requestDto);
            reportService.save(report);
            return ResponseEntity.ok("신고 등록 완료");
        } catch (Exception e){
            log.error("::ERROR:: ReportController.java -> saveReport");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
