package daone.bmap.service;


import daone.bmap.domain.amenity.Amenity;
import daone.bmap.domain.amenity.AmenityRepository;
import daone.bmap.domain.park.Park;
import daone.bmap.domain.park.ParkRepository;
import daone.bmap.dto.amenity.AmenityRequestDto;
import daone.bmap.dto.amenity.AmenityResponseDto;
import daone.bmap.dto.park.ParkDto;
import daone.bmap.dto.park.ParkMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class AmenityService {
    private final AmenityRepository amenityRepository;
    private final ParkService parkService;
    private final ParkRepository parkRepository;
    private final EntityManager em;

    public List<ParkDto> findParkDataByAmenityData(AmenityRequestDto data) {
        List<ParkDto> result = new ArrayList<>();
        try {
            List<String> ExistAmenityList = getExistAmenityList(data);
            List<Amenity> amenityList = em.createQuery("SELECT a FROM Amenity a WHERE "+createSelectQuery(data, ExistAmenityList)).getResultList();
            amenityList.forEach(a -> {
                Park park = parkService.findParkingLotByNo(a.getPark().getPrkplceNo());
                ParkDto parkDto = ParkMapper.mapper.parkEntityToDto(park);
                result.add(parkDto);
            });

            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    private String createSelectQuery(AmenityRequestDto data, List<String> ExistAmenityList) {
        String query = "";
        for (int i = 0; i < ExistAmenityList.size(); i++)
            query += (ExistAmenityList.get(i) + "=1 AND ");

        System.out.println("query1 = " + query);


        List<ParkDto> parkList = parkService.findParkingLotByLoc(data.getLatitude(), data.getLongitude());
        //parkList가 isEmpty면 lat long에 해당하는 주차장이 없다는 예외 던지기
        query += "a.park IN(";
        System.out.println("query2 = " + query);
        for (int i = 0; i < parkList.size(); i++) {
            if (i == parkList.size() - 1)
                query += ("'" + parkList.get(i) + "'");
            else
                query += ("'" + parkList.get(i) + "',");
        }
        query += ")";
        System.out.println("query3 = " + query);
        return query;
    }

    private List<String> getExistAmenityList(AmenityRequestDto data) {
        List<String> trueList = new ArrayList<>();
        if (data.isElevator()) trueList.add("a.elevator");
        if (data.isWideExit()) trueList.add("a.wideExit");
        if (data.isRamp()) trueList.add("a.ramp");
        if (data.isAccessRoads()) trueList.add("a.accessRoads");
        if (data.isWheelchairLift()) trueList.add("a.wheelchairLift");
        if (data.isBrailleBlock()) trueList.add("a.brailleBlock");
        if (data.isExGuidance()) trueList.add("a.exGuidance");
        if (data.isExTicketOffice()) trueList.add("a.exTicketOffice");
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
            parkList.forEach(p -> amenityRepository.save(new Amenity(p, r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), r.nextBoolean())));
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
