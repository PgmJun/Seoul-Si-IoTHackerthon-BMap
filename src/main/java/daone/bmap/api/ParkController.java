package daone.bmap.api;

import com.opencsv.exceptions.CsvValidationException;
import daone.bmap.dto.amenity.AmenityRequestDto;
import daone.bmap.dto.park.ParkAddrSearchDto;
import daone.bmap.dto.park.ParkDto;
import daone.bmap.dto.park.ParkLocSearchDto;
import daone.bmap.service.AmenityService;
import daone.bmap.service.ParkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/park")
@RestController
public class ParkController {
    private final ParkService parkService;
    private final AmenityService amenityService;

    @PutMapping("/save")
    public ResponseEntity<String> patchParkingData() throws CsvValidationException, IOException {
        parkService.saveParkData();
        return new ResponseEntity("주차장 데이터 패치 완료", HttpStatus.CREATED);
    }

    @GetMapping("/find/all")
    public ResponseEntity<?> findAllParkingData() {
        List<ParkDto> parkList = parkService.findParkingLotAll();
        return (!parkList.isEmpty()) ?
                ResponseEntity.ok(parkList) : new ResponseEntity<>("Parking lot data does not exist", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/find/amenity")   // 검색파라미터가 너무 많으므로 @PostMapping,@RequestBody 를 사용하여 데이터 받기
    public ResponseEntity<?> findParkingDataByAmenity(@RequestBody AmenityRequestDto data) {
        List<ParkDto> parkList = amenityService.findParkDataByAmenityData(data);
        return (!parkList.isEmpty()) ?
                ResponseEntity.ok(parkList) : new ResponseEntity<>("Parking lot with latitude: "+data.getLatitude()+", longitude: "+data.getLongitude()+" does not exist", HttpStatus.NOT_FOUND);

    }

    //주소,위도,경도로 주차장 찾기
    @GetMapping("/find/address")
    public ResponseEntity<?> findParkingDataByAddress(@ModelAttribute ParkAddrSearchDto data) {
        List<ParkDto> parkList = parkService.findParkingLotByAddr(data.getAddress(), data.getLatitude(), data.getLongitude());
        return (!parkList.isEmpty()) ?
                ResponseEntity.ok(parkList) : new ResponseEntity<>("Parking lot data does not exist", HttpStatus.NOT_FOUND);
    }

    //위도,경도로 주차장 찾기
    @GetMapping("/find/location")
    public ResponseEntity<?> findParkingDataByLocation(@ModelAttribute ParkLocSearchDto data) {
        List<ParkDto> parkList = parkService.findParkingLotByLoc(data.getLatitude(), data.getLongitude());
        return (!parkList.isEmpty()) ?
                ResponseEntity.ok(parkList) : new ResponseEntity<>("Parking lot data does not exist", HttpStatus.NOT_FOUND);
    }
}
