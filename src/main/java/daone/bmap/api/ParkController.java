package daone.bmap.api;

import daone.bmap.domain.park.Park;
import daone.bmap.dto.park.ParkDto;
import daone.bmap.service.ParkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/park")
@RestController
public class ParkController {
    private final ParkService parkService;

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

    @GetMapping("/address")
    public ResponseEntity<List<ParkDto>> findParkingDataByAddress(){
        try {
            List<ParkDto> parkList = parkService.findParkingLotAll();
            return ResponseEntity.ok(parkList);
        } catch (Exception e){
            log.error("::ERROR:: ParkController.java -> findAllParkingData");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
