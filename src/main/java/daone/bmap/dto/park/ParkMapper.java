package daone.bmap.dto.park;

import daone.bmap.domain.park.Park;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParkMapper {
    ParkMapper mapper = Mappers.getMapper(ParkMapper.class);
    Park parkDtoToEntity(ParkDto parkDto);
    ParkDto parkEntityToDto(Park park);
}
