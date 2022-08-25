package daone.bmap.api;

import daone.bmap.domain.amenity.Amenity;
import daone.bmap.domain.amenity.AmenityRepository;
import daone.bmap.domain.park.Park;
import daone.bmap.dto.amenity.AmenityRequestDto;
import daone.bmap.dto.park.ParkDto;
import daone.bmap.dto.park.ParkMapper;
import daone.bmap.service.ParkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/amenity")
@RestController
public class AmenityController {
    private final AmenityRepository amenityRepository;
    private final ParkService parkService;

    @GetMapping("/find")
    public ResponseEntity<?> findParkingDataByAmenity(@RequestBody AmenityRequestDto data){
        List<Amenity> amenityList = amenityRepository.findByElevatorAndRampAndAccessRoadsAndWheelchairLiftAndBrailleBlockAndExGuidanceAndExTicketOffice(data.isElevator(), data.isRamp(), data.isAccessRoads(), data.isWheelchairLift(), data.isBrailleBlock(), data.isExGuidance(), data.isExTicketOffice());
        List<ParkDto> result = new ArrayList<>();

        for (Amenity amenity : amenityList) {
            Park parkData = parkService.findParkingLotByNo(amenity.getPark().getPrkplceNo()).get();
            ParkDto parkDto = ParkMapper.mapper.parkEntityToDto(parkData);
            result.add(parkDto);
        }
        return ResponseEntity.ok(result);
    }
}
