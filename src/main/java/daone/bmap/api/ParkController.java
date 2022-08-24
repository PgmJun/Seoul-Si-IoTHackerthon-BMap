package daone.bmap.api;

import daone.bmap.domain.park.Park;
import daone.bmap.dto.park.ParkAddrSearchDto;
import daone.bmap.dto.park.ParkDto;
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
    public ResponseEntity<List<ParkDto>> findAllParkingData(){
        try {
            List<ParkDto> parkList = parkService.findParkingLotAll();
            return ResponseEntity.ok(parkList);
        } catch (Exception e){
            log.error("::ERROR:: ParkController.java -> findAllParkingData");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //검색으로
    @GetMapping("/address")
    public ResponseEntity<List<ParkDto>> findParkingDataByAddress(ParkAddrSearchDto data){
        try {
            List<ParkDto> parkList = parkService.findParkingLotByAddr(data.getAddress(), data.getLat(), data.getLng());
            return ResponseEntity.ok(parkList);
        } catch (Exception e){
            log.error("::ERROR:: ParkController.java -> findAllParkingData");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
