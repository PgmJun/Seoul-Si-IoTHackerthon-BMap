package daone.bmap.service;

import daone.bmap.domain.park.Park;
import daone.bmap.domain.park.ParkRepository;
import daone.bmap.dto.park.ParkDto;
import daone.bmap.tools.JsonParser;
import jakarta.persistence.EntityManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        List<Park> parks = parkRepository.findByAddr("%" + address + "%", lat, lng);
        return parks.stream()
                .map(ParkDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ParkDto> findParkingLotDtoByLoc(Double lat, Double lng) {
        List<Park> parks = parkRepository.findByLocation(lat, lng);
        return parks.stream()
                .map(ParkDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<Park> findParkingLotByLoc(Double lat, Double lng) {
        return parkRepository.findByLocation(lat, lng);
    }

    public List<ParkDto> findParkingLotAll() {
        List<Park> parks = parkRepository.findAll();
        return parks.stream()
                .map(ParkDto::fromEntity)
                .collect(Collectors.toList());
    }

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
}
