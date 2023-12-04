package daone.bmap.service;


import daone.bmap.domain.amenity.Amenity;
import daone.bmap.domain.amenity.AmenityRepository;
import daone.bmap.domain.park.Park;
import daone.bmap.domain.park.ParkRepository;
import daone.bmap.dto.amenity.AmenityRequestDto;
import daone.bmap.dto.amenity.AmenityResponseDto;
import daone.bmap.dto.park.ParkDto;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class AmenityService {
    private final AmenityRepository amenityRepository;
    private final ParkService parkService;
    private final ParkRepository parkRepository;
    private final EntityManager em;

    // 요청받은 AmenityData를 가진 근처 주차장 찾기
    public List<ParkDto> findParkDataByAmenityData(AmenityRequestDto data) {
        List<ParkDto> result = new ArrayList<>();

        try {
            List<String> existAmenityList = getExistAmenityList(data);
            List<Park> parkList = parkService.findParkingLotByLoc(data.getLatitude(), data.getLongitude());

            List<Amenity> amenityList = em.createQuery(createWhereQuery(existAmenityList)).setParameter("parks",parkList).getResultList();
            amenityList.forEach(amenity ->{
                Park park = parkService.findParkingLotByNo(amenity.getPark().getPrkplceNo());
                result.add(ParkDto.fromEntity(park));
            });

        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    private String createWhereQuery(List<String> existAmenityList) {
        String where = "";
        for(String existAmenityData : existAmenityList)
            where += (existAmenityData + "=1 AND ");
        where += "a.park IN :parks";

        return "SELECT a FROM Amenity a WHERE " + where;
    }

    private List<String> getExistAmenityList(AmenityRequestDto data) {
        List<String> trueList = data.toMap()
                .entrySet()
                .stream()
                .filter(Map.Entry::getValue) //m -> m.getValue().equals(true)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return trueList;
    }


    //더미데이터 넣기
    public void injectDummyData() {
        Random r = new Random();
        r.setSeed(2022);
        try {
            em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            em.createQuery("DELETE FROM Amenity").executeUpdate();
            em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();

            List<Park> parkList = parkRepository.findAll();
            parkList.forEach(p -> amenityRepository.save(Amenity.of(p, r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), r.nextBoolean())));
        } catch (Exception e) {
            throw e;
        }
    }

    public AmenityResponseDto findAmenityDataByPrkplceNo(String prkplceNo) {
        return amenityRepository.findByPark(parkService.findParkingLotByNo(prkplceNo))
                .orElseThrow(() -> new NoSuchElementException("Amenity with prkplceNo:" + prkplceNo + " does not exist"))
                .toResponseDto();


    }
}
