package daone.bmap.api;

import com.opencsv.exceptions.CsvValidationException;
import daone.bmap.dto.amenity.AmenityRequestDto;
import daone.bmap.dto.park.ParkAddrSearchDto;
import daone.bmap.dto.park.ParkDto;
import daone.bmap.dto.park.ParkLocSearchDto;
import daone.bmap.service.AmenityService;
import daone.bmap.service.ParkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "Park")
@RequestMapping("/park")
@RestController
public class ParkController {
    private final ParkService parkService;
    private final AmenityService amenityService;

    @PutMapping("/save")
    @Operation(summary = "주차장 데이터 저장")
    public ResponseEntity<String> patchParkingData() throws CsvValidationException, IOException {
        parkService.saveParkData();
        return new ResponseEntity("주차장 데이터 패치 완료", HttpStatus.CREATED);
    }

    @GetMapping("/find/all")
    @Operation(summary = "주차장 데이터 검색(전체)")
    public ResponseEntity<?> findAllParkingData() {
        List<ParkDto> parkList = parkService.findParkingLotAll();
        return (!parkList.isEmpty()) ?
                ResponseEntity.ok(parkList)
                : new ResponseEntity<>("Parking lot data does not exist", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/find/amenity")   // 검색파라미터가 너무 많으므로 @PostMapping,@RequestBody 를 사용하여 데이터 받기
    @Operation(summary = "주차장 데이터 검색(주차장시설)")
    public ResponseEntity<?> findParkingDataByAmenity(@RequestBody AmenityRequestDto data) {
        List<ParkDto> parkList = amenityService.findParkDataByAmenityData(data);
        return (!parkList.isEmpty()) ?
                ResponseEntity.ok(parkList) : new ResponseEntity<>(
                "Parking lot does not exist with AmenityData And nearby latitude: " + data.getLatitude()
                        + ", longitude: " + data.getLongitude(), HttpStatus.NOT_FOUND);

    }

    //
    @GetMapping("/find/address")
    @Operation(summary = "주차장 데이터 검색(주소)")
    public ResponseEntity<?> findParkingDataByAddress(@ModelAttribute ParkAddrSearchDto data) {
        List<ParkDto> parkList = parkService.findParkingLotByAddr(data.getAddress(), data.getLatitude(),
                data.getLongitude());
        return (!parkList.isEmpty()) ?
                ResponseEntity.ok(parkList)
                : new ResponseEntity<>("Parking lot data does not exist", HttpStatus.NOT_FOUND);
    }

    //위도,경도로 주차장 찾기
    @GetMapping("/find/location")
    @Operation(summary = "주차장 데이터 검색(내위치중심)")
    public ResponseEntity<?> findParkingDataByLocation(@ModelAttribute ParkLocSearchDto data) {
        List<ParkDto> parkList = parkService.findParkingLotDtoByLoc(data.getLatitude(), data.getLongitude());
        return (!parkList.isEmpty()) ?
                ResponseEntity.ok(parkList)
                : new ResponseEntity<>("Parking lot data does not exist", HttpStatus.NOT_FOUND);
    }
}
