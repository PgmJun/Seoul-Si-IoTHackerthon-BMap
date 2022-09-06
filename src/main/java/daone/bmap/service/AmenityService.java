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

    //    public List<ParkDto> findParkDataByAmenityData(AmenityRequestDto data) {
//        List<ParkDto> result = new ArrayList<>();
//        try {
//            // 요청받은 장애인 편의시설을 가진 주차장의 편의시설 데이터 불러오기
//            List<Amenity> amenityList = amenityRepository.findByElevatorAndRampAndAccessRoadsAndWheelchairLiftAndBrailleBlockAndExGuidanceAndExTicketOffice(data.isElevator(), data.isRamp(), data.isAccessRoads(), data.isWheelchairLift(), data.isBrailleBlock(), data.isExGuidance(), data.isExTicketOffice());
//
//            // 편의시설 데이터를 이용해 parkDto 불러오기
//            for (Amenity amenity : amenityList) {
//                Park parkData = parkService.findParkingLotByNo(amenity.getPark().getPrkplceNo()).get();
//                ParkDto parkDto = ParkMapper.mapper.parkEntityToDto(parkData);
//                result.add(parkDto);
//            }
//        } catch (Exception e) {
//            log.error("::ERROR:: AmenityService.java -> findAmenityData");
//        }
//        return result;
//    }
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
            List<ParkDto> parkList = parkService.findParkingLotByLoc(data.getLatitude(), data.getLongitude());
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
                Park park = parkService.findParkingLotByNo(amenity.getPark().getPrkplceNo());
                ParkDto parkDto = ParkMapper.mapper.parkEntityToDto(park);
                result.add(parkDto);

            }
            if (result.isEmpty()) {
                throw new NoSuchElementException("Parking lot with latitude: "+data.getLatitude()+", longitude: "+data.getLongitude()+" does not exist");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("::ERROR:: AmenityService.java -> findAmenityData");
        }
        return result;
    }

    //더미데이터 넣기
    public void injectDummyData() {
        try {

            em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            em.createQuery("DELETE FROM Amenity").executeUpdate();
            em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();

            Random r = new Random();
            r.setSeed(20220826);
            List<Park> parkList = parkRepository.findAll();
            for (Park park : parkList) {
                amenityRepository.save(new Amenity(park, r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), r.nextBoolean(), r.nextBoolean()));
            }
        } catch (Exception e) {
            throw new NullPointerException("injectDummyData() Error");
        }
    }

    public AmenityResponseDto findAmenityDataByPrkplceNo(String prkplceNo) {
        return amenityRepository.findByPark(parkService.findParkingLotByNo(prkplceNo))
                .orElseThrow(()-> new NoSuchElementException("Amenity with prkplceNo:" + prkplceNo + " does not exist"))
                .toResponseDto();
    }
}
