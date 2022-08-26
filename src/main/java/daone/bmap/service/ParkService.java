package daone.bmap.service;

import com.opencsv.exceptions.CsvValidationException;
import daone.bmap.domain.park.Park;
import daone.bmap.domain.park.ParkRepository;
import daone.bmap.dto.park.ParkDto;
import daone.bmap.dto.park.ParkMapper;
import daone.bmap.tools.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ParkService {
    private final ParkRepository parkRepository;
    private final JsonParser jsonParser;
    private final EntityManager em;


    public Park findParkingLotByNo(String prkplceNo) {
        return parkRepository.findByPrkplceNo(prkplceNo)
                .orElseThrow(() -> new NoSuchElementException("Parking lot with prkplceNo:" + prkplceNo + " does not exist"));
    }

    public List<ParkDto> findParkingLotByAddr(String address, String lat, String lng) {
        return getParkDtoList(parkRepository.findByAddr("%" + address + "%", lat, lng));
    }

    public List<ParkDto> findParkingLotDtoByLoc(Double lat, Double lng) {
        return getParkDtoList(parkRepository.findByLocation(lat, lng));
    }

    public List<Park> findParkingLotByLoc(Double lat, Double lng) {
        return parkRepository.findByLocation(lat, lng);
    }

    public List<ParkDto> findParkingLotAll() {
        return getParkDtoList(parkRepository.findAll());
    }

    @Transactional
    public void saveParkData() throws IOException {
        try {
            em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            em.createQuery("DELETE FROM Park").executeUpdate();
            em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();

            jsonParser.parseJsonParkData();
        } catch (Exception e) {
            throw e;
        }
    }

    //parkList 받아온 뒤 parkDtoList로 만들어 반환
    private List<ParkDto> getParkDtoList(List<Park> parkList) {
        try {
            List<ParkDto> result = new ArrayList<>();
            parkList.forEach(p -> result.add(ParkMapper.mapper.parkEntityToDto(p)));
            return result;
        } catch (Exception e) {
            throw e;
        }
    }


}
