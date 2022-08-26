package daone.bmap.api;

import daone.bmap.dto.park.ParkAddrSearchDto;
import daone.bmap.dto.park.ParkDto;
import daone.bmap.dto.park.ParkLocSearchDto;
import daone.bmap.service.ParkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/park")
@RestController
public class ParkController {
    private final ParkService parkService;

    @PutMapping
    public ResponseEntity<String> patchParkingData(){
        try {
            parkService.saveParkData();
            return ResponseEntity.ok("주차장 데이터 패치 완료");
        } catch (Exception e){
            log.error("::ERROR:: ParkController.java -> patchParkingData");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllParkingData(){
        try {
            List<ParkDto> parkList = parkService.findParkingLotAll();
            if(parkList.isEmpty())
                return new ResponseEntity<>("데이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
            else
                return ResponseEntity.ok(parkList);
        } catch (Exception e){
            log.error("::ERROR:: ParkController.java -> findAllParkingData");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //주소,위도,경도로 주차장 찾기
    @GetMapping("/address")
    public ResponseEntity<?> findParkingDataByAddress(@RequestBody ParkAddrSearchDto data){
        try {
            List<ParkDto> parkList = parkService.findParkingLotByAddr(data.getAddress(), data.getLatitude(), data.getLongitude());
            if(parkList.isEmpty())
                return new ResponseEntity<>("데이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
            return ResponseEntity.ok(parkList);
        } catch (Exception e){
            log.error("::ERROR:: ParkController.java -> findParkingDataByAddress");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //위도,경도로 주차장 찾기
    @GetMapping("/location")
    public ResponseEntity<?> findParkingDataByLocation(@RequestBody ParkLocSearchDto data) {
        try {
            List<ParkDto> parkList = parkService.findParkingLotByLoc(data.getLatitude(), data.getLongitude());
            if(parkList.isEmpty())
                return new ResponseEntity<>("데이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
            else
                return ResponseEntity.ok(parkList);
        } catch (Exception e){
            log.error("::ERROR:: ParkController.java -> findParkingDataByLocation");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
