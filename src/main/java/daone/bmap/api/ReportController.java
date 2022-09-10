package daone.bmap.api;

import daone.bmap.domain.report.Report;
import daone.bmap.domain.report.ReportType;
import daone.bmap.dto.report.ReportRequestDto;
import daone.bmap.dto.report.ReportResponseDto;
import daone.bmap.service.ParkService;
import daone.bmap.service.ReportService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static daone.bmap.dto.report.ReportResponseDto.getReportResponseDto;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/report")
@RestController
public class ReportController {
    private final ReportService reportService;
    private final ParkService parkService;

    @PostMapping("/add")
    @ApiOperation(value = "신고 데이터 저장", notes = "입력받은 내용의 신고 데이터 저장")
    public ResponseEntity<String> saveReport(@RequestBody ReportRequestDto data) {
        reportService.save(new Report(parkService.findParkingLotByNo(data.getPrkplceNo()), ReportType.checkTypeCode(data.getReportType()), data.getReportTitle(), data.getReportText(), data.getReportCarNm()));
        return ResponseEntity.ok("신고 접수가 완료되었습니다!");
    }

    @GetMapping("/find/{reportId}")
    @ApiOperation(value = "신고 데이터 검색(ID)", notes = "PK가 {reportId}인 신고 데이터 검색")
    public ResponseEntity<ReportResponseDto> findReportDataByPno(@PathVariable Long reportId) {
        return new ResponseEntity<>(getReportResponseDto(reportService.findReportByReportId(reportId)), HttpStatus.OK);

    }

    @GetMapping("/find/all")
    @ApiOperation(value = "신고 데이터 검색(전체)", notes = "DB에 저장된 모든 신고 데이터 찾기")
    public ResponseEntity<?> findAllReportData() {
        List<ReportResponseDto> reportList = reportService.findReportAll();
        return (!reportList.isEmpty()) ?
                ResponseEntity.ok(reportList) : new ResponseEntity<>("Report data does not exist", HttpStatus.NOT_FOUND);
    }

}
