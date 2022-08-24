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
public class ReportDto {

    private String prkplceNo;
    private ReportType reportType;
    private String reportTitle;
    private String reportText;
    private String reportCarNm;

    public Report toEntity(){
        return builder()
                .prkplceNo(prkplceNo)
                .reportType(reportType)
                .reportTitle(reportTitle)
                .reportText(reportText)
                .reportCarNm(reportCarNm)
                .build().toEntity();

    }
}
