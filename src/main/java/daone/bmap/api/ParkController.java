package daone.bmap.api;

import com.opencsv.exceptions.CsvValidationException;
import daone.bmap.dto.amenity.AmenityRequestDto;
import daone.bmap.dto.park.ParkAddrSearchDto;
import daone.bmap.dto.park.ParkDto;
import daone.bmap.dto.park.ParkLocSearchDto;
import daone.bmap.service.AmenityService;
import daone.bmap.service.ParkService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/park")
@RestController
public class ParkController {
    private final ParkService parkService;
    private final AmenityService amenityService;

    @PutMapping("/save")
    @ApiOperation(value = "주차장 데이터 저장", notes = "주차장 데이터 최신화(data.go.kr 기준)")
    public ResponseEntity<String> patchParkingData() throws CsvValidationException, IOException {
        parkService.saveParkData();
        return new ResponseEntity("주차장 데이터 패치 완료", HttpStatus.CREATED);
    }

    @GetMapping("/find/all")
    @ApiOperation(value = "주차장 데이터 검색(전체)", notes = "DB에 저장된 모든 주차장 데이터 찾기")
    public ResponseEntity<?> findAllParkingData() {
        List<ParkDto> parkList = parkService.findParkingLotAll();
        return (!parkList.isEmpty()) ?
                ResponseEntity.ok(parkList) : new ResponseEntity<>("Parking lot data does not exist", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/find/amenity")   // 검색파라미터가 너무 많으므로 @PostMapping,@RequestBody 를 사용하여 데이터 받기
    @ApiOperation(value = "주차장 데이터 검색(주차장시설)", notes = "입력받은 주차장 시설을 갖춘 주차장 데이터 찾기(현재 위도,경도 기준 가까운 순서대로 정렬)")
    public ResponseEntity<?> findParkingDataByAmenity(@RequestBody AmenityRequestDto data) {
        List<ParkDto> parkList = amenityService.findParkDataByAmenityData(data);
        return (!parkList.isEmpty()) ?
                ResponseEntity.ok(parkList) : new ResponseEntity<>("Parking lot does not exist with AmenityData And nearby latitude: "+data.getLatitude()+", longitude: "+data.getLongitude(), HttpStatus.NOT_FOUND);

    }

    //
    @GetMapping("/find/address")
    @ApiOperation(value = "주차장 데이터 검색(주소)", notes = "입력받은 주소 근처의 주차장 데이터 찾기(현재 위도,경도 기준 가까운 순서대로 정렬)")
    public ResponseEntity<?> findParkingDataByAddress(@ModelAttribute ParkAddrSearchDto data) {
        List<ParkDto> parkList = parkService.findParkingLotByAddr(data.getAddress(), data.getLatitude(), data.getLongitude());
        return (!parkList.isEmpty()) ?
                ResponseEntity.ok(parkList) : new ResponseEntity<>("Parking lot data does not exist", HttpStatus.NOT_FOUND);
    }

    //위도,경도로 주차장 찾기
    @GetMapping("/find/location")
    @ApiOperation(value = "주차장 데이터 검색(내위치중심)", notes = "위도,경도 기준 근처 주차장 데이터 찾기(가까운 순서대로 정렬)")
    public ResponseEntity<?> findParkingDataByLocation(@ModelAttribute ParkLocSearchDto data) {
        List<ParkDto> parkList = parkService.findParkingLotDtoByLoc(data.getLatitude(), data.getLongitude());
        return (!parkList.isEmpty()) ?
                ResponseEntity.ok(parkList) : new ResponseEntity<>("Parking lot data does not exist", HttpStatus.NOT_FOUND);
    }
}
