package daone.bmap.service;


import daone.bmap.domain.amenity.Amenity;
import daone.bmap.domain.amenity.AmenityRepository;
import daone.bmap.domain.park.Park;
import daone.bmap.dto.amenity.AmenityRequestDto;
import daone.bmap.dto.park.ParkDto;
import daone.bmap.dto.park.ParkMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class AmenityService {
    private final AmenityRepository amenityRepository;
    private final ParkService parkService;

    public List<ParkDto> findAmenityData(AmenityRequestDto data){
        List<ParkDto> result = new ArrayList<>();
        try {
            // 요청받은 장애인 편의시설을 가진 주차장의 편의시설 데이터 불러오기
            List<Amenity> amenityList = amenityRepository.findByElevatorAndRampAndAccessRoadsAndWheelchairLiftAndBrailleBlockAndExGuidanceAndExTicketOffice(data.isElevator(), data.isRamp(), data.isAccessRoads(), data.isWheelchairLift(), data.isBrailleBlock(), data.isExGuidance(), data.isExTicketOffice());

            // 편의시설 데이터를 이용해 parkDto 불러오기
            for (Amenity amenity : amenityList) {
                Park parkData = parkService.findParkingLotByNo(amenity.getPark().getPrkplceNo()).get();
                ParkDto parkDto = ParkMapper.mapper.parkEntityToDto(parkData);
                result.add(parkDto);
            }
        }catch (Exception e){
            log.error("::ERROR:: AmenityService.java -> findAmenityData");
        }
        return result;
    }

}
