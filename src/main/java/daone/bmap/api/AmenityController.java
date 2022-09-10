package daone.bmap.api;

import daone.bmap.dto.amenity.AmenityResponseDto;
import daone.bmap.service.AmenityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/amenity")
@RestController
public class AmenityController {
    private final AmenityService amenityService;

    @GetMapping("/find/{prkplceNo}")
    public ResponseEntity<AmenityResponseDto> findAmenityDataByPrkplceNo(@PathVariable String prkplceNo) {
        AmenityResponseDto amenityList = amenityService.findAmenityDataByPrkplceNo(prkplceNo);
        return new ResponseEntity(amenityList, HttpStatus.OK);
    }

    @PutMapping("/save")
    public ResponseEntity<String> saveAmenityData() {
        amenityService.injectDummyData();
        return new ResponseEntity("장애인 편의 시설 데이터 저장 완료", HttpStatus.CREATED);
    }
}
