package daone.bmap.api;

import daone.bmap.dto.amenity.AmenityResponseDto;
import daone.bmap.service.AmenityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "Amenity")
@RequestMapping("/amenity")
@RestController
public class AmenityController {
    private final AmenityService amenityService;

    @Operation(summary = "주차장 시설 데이터 검색")
    @GetMapping("/find/{prkplceNo}")
    public ResponseEntity<AmenityResponseDto> findAmenityDataByPrkplceNo(@PathVariable String prkplceNo) {
        AmenityResponseDto amenityList = amenityService.findAmenityDataByPrkplceNo(prkplceNo);
        return new ResponseEntity(amenityList, HttpStatus.OK);
    }

    @PutMapping("/save")
    @Operation(summary = "주차장 시설 데이터 저장")
    public ResponseEntity<String> saveAmenityData() {
        amenityService.injectDummyData();
        return new ResponseEntity("장애인 편의 시설 데이터 저장 완료", HttpStatus.CREATED);
    }
}
