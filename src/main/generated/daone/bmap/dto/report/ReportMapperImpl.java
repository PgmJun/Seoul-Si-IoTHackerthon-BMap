package daone.bmap.dto.report;

import daone.bmap.domain.report.Report;
import daone.bmap.dto.report.ReportRequestDto.ReportRequestDtoBuilder;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-25T00:21:44+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
public class ReportMapperImpl implements ReportMapper {

    @Override
    public ReportRequestDto reportEntityToRequestDto(Report report) {
        if ( report == null ) {
            return null;
        }

        ReportRequestDtoBuilder reportRequestDto = ReportRequestDto.builder();

        reportRequestDto.reportType( report.getReportType() );
        reportRequestDto.reportTitle( report.getReportTitle() );
        reportRequestDto.reportText( report.getReportText() );
        reportRequestDto.reportCarNm( report.getReportCarNm() );

        return reportRequestDto.build();
    }

    @Override
    public Report reportRequestDtoToEntity(ReportRequestDto requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        Report report = new Report();

        return report;
    }
}
