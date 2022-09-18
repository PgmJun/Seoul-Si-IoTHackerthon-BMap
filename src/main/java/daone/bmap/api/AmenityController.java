package daone.bmap.api;

import daone.bmap.dto.amenity.AmenityResponseDto;
import daone.bmap.service.AmenityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Api(tags = { "주차장 시설 관련 API" })
@RequestMapping("/amenity")
@RestController
public class AmenityController {
    private final AmenityService amenityService;

    @ApiOperation(value = "주차장 시설 데이터 검색", notes = "PK가 {prkplceNo}인 주차장의 시설 데이터 찾기")
    @ApiImplicitParam()
    @GetMapping("/find/{prkplceNo}")
    public ResponseEntity<AmenityResponseDto> findAmenityDataByPrkplceNo(@PathVariable String prkplceNo) {
        AmenityResponseDto amenityList = amenityService.findAmenityDataByPrkplceNo(prkplceNo);
        return new ResponseEntity(amenityList, HttpStatus.OK);
    }

    @PutMapping("/save")
    @ApiOperation(value = "주차장 시설 데이터 저장", notes = "주차장 시설 더미 데이터 저장")
    public ResponseEntity<String> saveAmenityData() {
        amenityService.injectDummyData();
        return new ResponseEntity("장애인 편의 시설 데이터 저장 완료", HttpStatus.CREATED);
    }
}
