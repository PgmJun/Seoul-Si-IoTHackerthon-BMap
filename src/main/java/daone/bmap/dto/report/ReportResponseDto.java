package daone.bmap.dto.report;

import daone.bmap.domain.report.Report;
import daone.bmap.domain.report.ReportType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
@Getter
public class ReportResponseDto {

    private Long reportId;
    private String prkplceNo;
    private ReportType reportType;
    private String reportTitle;
    private String reportText;
    private String reportCarNm;

    public static ReportResponseDto fromEntity(Report report) {
        return builder()
                .reportId(report.getReportId())
                .prkplceNo(report.getPark().getPrkplceNo())
                .reportTitle(report.getReportTitle())
                .reportText(report.getReportText())
                .reportCarNm(report.getReportCarNm())
                .build();
    }
}
