package daone.bmap.api;

import daone.bmap.domain.park.Park;
import daone.bmap.domain.report.Report;
import daone.bmap.domain.report.ReportType;
import daone.bmap.dto.report.ReportRequestDto;
import daone.bmap.service.ParkService;
import daone.bmap.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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

            // 입력받은 reportType 코드를 통해 ReportType 생성
            ReportType reportType = ReportType.checkTypeCode(data.getReportType());

            // reportType이 null일 경우 BAD_REQUEST 발생
            if(reportType == null) {
                log.error("::ERROR:: ReportController.java -> saveReport / 잘못된 reportType 코드");
                return new ResponseEntity<>("잘못된 reportType", HttpStatus.BAD_REQUEST);
            }

            // 입력받은 PrkplceNo에 해당하는 주차장 데이터가 존재하는 지 Check
            Optional<Park> opPark = parkService.findParkingLotByNo(data.getPrkplceNo());
            if (opPark.isEmpty()) {
                log.error("::ERROR:: ReportController.java -> saveReport / park is NULL!");
                return new ResponseEntity<>("잘못된 PrkplceNo", HttpStatus.NOT_FOUND);
            }
            Park park = opPark.get();
            Report report = new Report(park, reportType, data.getReportTitle(), data.getReportText(), data.getReportCarNm());
            reportService.save(report);

            return ResponseEntity.ok("신고 접수가 완료되었습니다!");
        } catch (Exception e) {
            log.error("::ERROR:: ReportController.java -> saveReport");
            return new ResponseEntity<>("신고 실패", HttpStatus.NOT_FOUND);
        }
    }

}
