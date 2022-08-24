package daone.bmap.dto.report;


import daone.bmap.domain.report.Report;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReportMapper {
    ReportMapper mapper = Mappers.getMapper(ReportMapper.class);

    ReportRequestDto reportEntityToRequestDto(Report report);

    Report reportRequestDtoToEntity(ReportRequestDto requestDto);
}
