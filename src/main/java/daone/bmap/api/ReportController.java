package daone.bmap.api;


import daone.bmap.dto.report.ReportRequestDto;
import daone.bmap.dto.report.ReportResponseDto;
import daone.bmap.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Report")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/report/add")
    @Operation(summary = "입력받은 내용의 신고 데이터 저장")
    public ResponseEntity<String> saveReport(@RequestBody ReportRequestDto data) {
        reportService.saveReport(data);
        return ResponseEntity.ok("신고 접수가 완료되었습니다!");
    }

    @GetMapping("/reports/find/{reportId}")
    @Operation(summary = "신고 데이터 검색(ID)")
    public ResponseEntity<ReportResponseDto> findReportDataByPno(@PathVariable Long reportId) {
        return new ResponseEntity<>(reportService.findReportByReportId(reportId), HttpStatus.OK);

    }

    @GetMapping("/reports/find/all")
    @Operation(summary = "신고 데이터 검색(전체)")
    public ResponseEntity<?> findAllReportData() {
        List<ReportResponseDto> reportList = reportService.findReportAll();
        return (!reportList.isEmpty()) ?
                ResponseEntity.ok(reportList) : new ResponseEntity<>("Report data does not exist", HttpStatus.NOT_FOUND);
    }
}
