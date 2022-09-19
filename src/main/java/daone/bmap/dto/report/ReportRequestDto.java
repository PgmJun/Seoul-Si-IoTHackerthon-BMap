package daone.bmap.dto.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Setter
@Getter
public class ReportRequestDto {

    private String prkplceNo;
    private Integer reportType;
    private String reportTitle;
    private String reportText;
    private String reportCarNm;
}
