package daone.bmap.service;

import daone.bmap.csv.CSVParser;
import daone.bmap.domain.park.Park;
import daone.bmap.domain.park.ParkRepository;
import daone.bmap.dto.park.ParkDto;
import daone.bmap.dto.park.ParkMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ParkService {
    private final ParkRepository parkRepository;
    private final EntityManager em;

    public void saveData(Park park) {
        parkRepository.save(park);
    }

    public List<ParkDto> findParkingLotByAddr(String address, String lat, String lng) {
        List<ParkDto> result = new ArrayList<>();
        try {
            String sqlAddress = "%"+address+"%";
            List<Park> parkList = parkRepository.findByAddr(sqlAddress, lat, lng);

            for (Park p : parkList) {
                ParkDto parkDto = ParkMapper.mapper.parkEntityToDto(p);
                result.add(parkDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("::ERROR:: ParkService.java -> findParkingLotByAddr");
        }
        return result;
    }

    public List<ParkDto> findParkingLotByLoc(Double lat, Double lng){
        List<ParkDto> result = new ArrayList<>();
        try {
            List<Park> parkList = parkRepository.findByLocation(lat, lng);
            for (Park p : parkList) {
                ParkDto parkDto = ParkMapper.mapper.parkEntityToDto(p);
                result.add(parkDto);
            }
            return result;
        } catch (Exception e) {
            log.error("::ERROR:: ParkService.java -> findParkingLotByLoc");
        }
        return result;

    }

    public List<ParkDto> findParkingLotAll() {
        List<ParkDto> result = new ArrayList<>();
        try {
            List<Park> parkList = parkRepository.findAll();
            for (Park p : parkList) {
                ParkDto parkDto = ParkMapper.mapper.parkEntityToDto(p);
                result.add(parkDto);
            }
        } catch (Exception e) {
            log.error("::ERROR:: ParkService.java -> findParkingLotAll");
        }
        return result;
    }

    public void saveParkData() {
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        em.createQuery("DELETE FROM Park").executeUpdate();
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();

        CSVParser csvParser = new CSVParser(em);
        csvParser.read();
    }


}
