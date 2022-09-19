package daone.bmap.dto.report;

import daone.bmap.domain.report.Report;
import daone.bmap.domain.report.ReportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Setter
@Getter
public class ReportResponseDto {

    private Long reportId;
    private String prkplceNo;
    private ReportType reportType;
    private String reportTitle;
    private String reportText;
    private String reportCarNm;

    // Report Entity를 DTO로 변환시키는 메서드
    // MapStruct로 변환 시에 prkplceNo가 제대로 저장되지 않는 문제가 있어 생성한 메서드
    public static ReportResponseDto getReportResponseDto(Report report) {
        ReportResponseDto result = ReportMapper.mapper.reportEntityToDto(report);
        result.setPrkplceNo(report.getPark().getPrkplceNo());
        return result;
    }
}
