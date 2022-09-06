package daone.bmap.api;

import daone.bmap.domain.park.Park;
import daone.bmap.domain.report.Report;
import daone.bmap.domain.report.ReportType;
import daone.bmap.dto.report.ReportMapper;
import daone.bmap.dto.report.ReportRequestDto;
import daone.bmap.dto.report.ReportResponseDto;
import daone.bmap.service.ParkService;
import daone.bmap.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static daone.bmap.dto.report.ReportResponseDto.getReportResponseDto;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/report")
@RestController
public class ReportController {
    private final ReportService reportService;
    private final ParkService parkService;

    @PostMapping("/add")
    public ResponseEntity<String> saveReport(@RequestBody ReportRequestDto data) {
        try {
            reportService.save(new Report(parkService.findParkingLotByNo(data.getPrkplceNo()), ReportType.checkTypeCode(data.getReportType()), data.getReportTitle(), data.getReportText(), data.getReportCarNm()));
            return ResponseEntity.ok("신고 접수가 완료되었습니다!");
        } catch (Exception e) {
            log.error("::ERROR:: ReportController.java -> saveReport");
            return new ResponseEntity<>("신고 실패", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find/{reportId}")
    public ResponseEntity<ReportResponseDto> findReportDataByPno(@PathVariable Long reportId) {
        return new ResponseEntity<>(getReportResponseDto(reportService.findReportByReportId(reportId)), HttpStatus.OK);

    }

    //Report DTO와 Entity의 prkplceNo의 자료형변환해주는 메서드


    @GetMapping("/find/all")
    public ResponseEntity<?> findAllReportData() {
        try {
            List<ReportResponseDto> reportList = reportService.findReportAll();
            return ResponseEntity.ok(reportList);
        } catch (Exception e) {
            log.error("::ERROR:: ReportController.java -> findAllReportData");
            return new ResponseEntity<>("신고 데이터 불러오기 실패", HttpStatus.BAD_REQUEST);
        }
    }

}
