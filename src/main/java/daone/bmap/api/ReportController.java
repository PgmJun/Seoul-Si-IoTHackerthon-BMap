package daone.bmap.api;

import daone.bmap.domain.park.Park;
import daone.bmap.domain.report.Report;
import daone.bmap.domain.report.ReportType;
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
                log.error("::ERROR:: ReportController.java -> saveReport / The requested data does not exist!");
                return new ResponseEntity<>("잘못된 prkplceNo", HttpStatus.NOT_FOUND);
            }
            // 신고 데이터 저장
            Park park = opPark.get();
            Report report = new Report(park, reportType, data.getReportTitle(), data.getReportText(), data.getReportCarNm());
            reportService.save(report);

            return ResponseEntity.ok("신고 접수가 완료되었습니다!");
        } catch (Exception e) {
            log.error("::ERROR:: ReportController.java -> saveReport");
            return new ResponseEntity<>("신고 실패", HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/find/{reportId}")
//    public ResponseEntity<?> findReportDataByPno(@PathVariable Long reportId){
//        try {
//            // Id가 reportId와 일치하는 신고 데이터 조회
//            Optional<Report> opReport = reportService.findReportByreportId(reportId);
//
//            // 신고 데이터를 찾지 못하면 NOT_FOUND 발생
//            if(opReport.isEmpty()){
//                log.error("::ERROR:: ReportController.java -> findReportDataByPno / The requested data does not exist");
//                return new ResponseEntity<>("잘못된 reportId", HttpStatus.NOT_FOUND);
//            }
//
//            return new ResponseEntity<>(opReport.get(), HttpStatus.OK);
//        }catch (Exception e){
//            log.error("::ERROR:: ReportController.java -> saveReport");
//            return new ResponseEntity<>("ReportData 조회 실패", HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping("/find/all")
    public ResponseEntity<?> findAllReportData(){
        try{
            List<ReportResponseDto> reportList = reportService.findReportAll();
            return ResponseEntity.ok(reportList);
        } catch (Exception e){
            log.error("::ERROR:: ReportController.java -> findAllReportData");
            return new ResponseEntity<>("신고 데이터 불러오기 실패", HttpStatus.BAD_REQUEST);
        }
    }

}
