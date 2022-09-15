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
            List<String> trueList = new ArrayList<>();
            if (data.isElevator()) trueList.add("a.elevator");
            if (data.isWideExit()) trueList.add("a.wideExit");
            if (data.isRamp()) trueList.add("a.ramp");
            if (data.isAccessRoads()) trueList.add("a.accessRoads");
            if (data.isWheelchairLift()) trueList.add("a.wheelchairLift");
            if (data.isBrailleBlock()) trueList.add("a.brailleBlock");
            if (data.isExGuidance()) trueList.add("a.exGuidance");
            if (data.isExTicketOffice()) trueList.add("a.exTicketOffice");

            String where = " ";
            for (int i = 0; i < trueList.size(); i++) {
                where += (trueList.get(i) + "=1 AND ");
            }
            where += "a.park IN";
            List<ParkDto> parkList = parkService.findParkingLotDtoByLoc(data.getLatitude(), data.getLongitude());
            where += "(";
            for (int i = 0; i < parkList.size(); i++) {
                if (i == parkList.size() - 1)
                    where += ("'" + parkList.get(i).getPrkplceNo()+"'");
                else
                    where += ("'" + parkList.get(i).getPrkplceNo() + "',");
            }
            where += ")";
            String jsql = "SELECT a FROM Amenity a WHERE" + where;
            System.out.println("jsql = " + jsql);
            List<Amenity> amenityList = em.createQuery(jsql).getResultList();
            for (Amenity amenity : amenityList) {
                System.out.println("amenity.getAmenityId() = " + amenity.getAmenityId());
                System.out.println("amenity.getPark().getPrkplceNo() = " + amenity.getPark().getPrkplceNo());
            }

            for (Amenity amenity : amenityList) {
                Park Park = parkService.findParkingLotByNo(amenity.getPark().getPrkplceNo());
                ParkDto parkDto = ParkMapper.mapper.parkEntityToDto(Park);
                result.add(parkDto);

            }
            if (result.isEmpty()) {
                log.error("::INFO:: AmenityService.java -> findParkDataByAmenityData / 검색 조건에 해당하는 주차장 데이터가 존재하지 않습니다");
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("::ERROR:: AmenityService.java -> findAmenityData");
        }
        return result;
    }

    private String createSelectQuery(List<String> ExistAmenityList) {
        String query = " ";
        for (int i = 0; i < ExistAmenityList.size(); i++)
                query += (ExistAmenityList.get(i) + "=1 AND ");

       return query;
    }
    private List<Park> getParkListNearbyLatAndLng(Double lat, Double lng){
        List<Park> parkList = parkService.findParkingLotByLoc(lat, lng);
        //parkList가 isEmpty면 lat long에 해당하는 주차장이 없다는 예외 던지기
        if(parkList.isEmpty())
            throw new NoSuchElementException("Parking lot does not exist nearby at latitude:"+lat+", longitude:"+lng);
        return parkList;
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
