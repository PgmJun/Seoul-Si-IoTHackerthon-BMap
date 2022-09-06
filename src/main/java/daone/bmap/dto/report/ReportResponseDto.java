package daone.bmap.dto.report;

import daone.bmap.domain.park.Park;
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

    //Report DTO와 Entity의 prkplceNo의 자료형변환해주는 메서드
    public static ReportResponseDto getReportResponseDto(Report report) {
        ReportResponseDto result = ReportMapper.mapper.reportEntityToDto(report);
        result.setPrkplceNo(report.getPark().getPrkplceNo());
        return result;
    }
}
