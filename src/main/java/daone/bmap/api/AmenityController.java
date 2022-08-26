package daone.bmap.api;

import daone.bmap.domain.amenity.Amenity;
import daone.bmap.domain.amenity.AmenityRepository;
import daone.bmap.domain.park.Park;
import daone.bmap.dto.amenity.AmenityRequestDto;
import daone.bmap.dto.amenity.AmenityResponseDto;
import daone.bmap.dto.park.ParkDto;
import daone.bmap.dto.park.ParkMapper;
import daone.bmap.service.AmenityService;
import daone.bmap.service.ParkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/amenity")
@RestController
public class AmenityController {
    private final AmenityService amenityService;

    @GetMapping("/find")
    public ResponseEntity<?> findParkingDataByAmenity(@RequestBody AmenityRequestDto data){
        try {
            List<ParkDto> result = amenityService.findAmenityData(data);
            if(result.isEmpty())
                return new ResponseEntity<>("데이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            log.error("::ERROR:: AmenityController.java -> findParkingDataByAmenity");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find/prkplceNo")
    public ResponseEntity<?> findAmenityDataByPrkplceNo(@RequestParam String prkplceNo){
        try{
            AmenityResponseDto result = amenityService.findAmenityDataByPrkplceNo(prkplceNo);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            log.error("::ERROR:: AmenityController.java -> findAmenityDataByPrkplceNo");
            return new ResponseEntity<>("편의시설 데이터 찾기 실패", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/save")
    public ResponseEntity<?> saveAmenityData() {
        try {
            amenityService.injectDummyData();
            return ResponseEntity.ok("장애인 편의 시설 데이터 저장 완료");
        } catch (Exception e){
            log.error("::ERROR:: AmenityController.java -> saveAmenityData");
            return new ResponseEntity<>("장애인 편의 시설 데이터 저장 실패",HttpStatus.BAD_REQUEST);
        }
    }
}
