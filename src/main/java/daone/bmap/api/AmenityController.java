package daone.bmap.api;

import daone.bmap.dto.amenity.AmenityRequestDto;
import daone.bmap.dto.amenity.AmenityResponseDto;
import daone.bmap.dto.park.ParkDto;
import daone.bmap.service.AmenityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/amenity")
@RestController
public class AmenityController {
    private final AmenityService amenityService;

    @GetMapping("/find")
    public ResponseEntity<?> findParkingDataByAmenity(@RequestBody AmenityRequestDto data){
        try {
            List<ParkDto> result = amenityService.findParkDataByAmenityData(data);
            if(result.isEmpty())
                return new ResponseEntity<>("데이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            log.error("::ERROR:: AmenityController.java -> findParkingDataByAmenity");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find/{prkplceNo}")
    public ResponseEntity<?> findAmenityDataByPrkplceNo(@PathVariable String prkplceNo){
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
