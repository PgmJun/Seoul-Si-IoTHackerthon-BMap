package daone.bmap.dto.report;

import daone.bmap.domain.report.Report;
import daone.bmap.domain.report.Report.ReportBuilder;
import daone.bmap.dto.report.ReportResponseDto.ReportResponseDtoBuilder;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-25T21:54:46+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
public class ReportMapperImpl implements ReportMapper {

    @Override
    public Report reportDtoToEntity(ReportResponseDto reportResponseDto) {
        if ( reportResponseDto == null ) {
            return null;
        }

        ReportBuilder report = Report.builder();

        report.reportType( reportResponseDto.getReportType() );
        report.reportTitle( reportResponseDto.getReportTitle() );
        report.reportText( reportResponseDto.getReportText() );
        report.reportCarNm( reportResponseDto.getReportCarNm() );

        return report.build();
    }

    @Override
    public ReportResponseDto reportEntityToDto(Report report) {
        if ( report == null ) {
            return null;
        }

        ReportResponseDtoBuilder reportResponseDto = ReportResponseDto.builder();

        reportResponseDto.reportId( report.getReportId() );
        reportResponseDto.reportType( report.getReportType() );
        reportResponseDto.reportTitle( report.getReportTitle() );
        reportResponseDto.reportText( report.getReportText() );
        reportResponseDto.reportCarNm( report.getReportCarNm() );

        return reportResponseDto.build();
    }
}
