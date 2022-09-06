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
        List<String> findAmenityList = new ArrayList<>();

        try {
            checkAmenity(data, findAmenityList);
            String jsql = createJsqlToFindParkingLotsByAmenityList(data, findAmenityList);
            List<Amenity> amenityList = em.createQuery(jsql).getResultList();

            ParkEntityToDto(result, amenityList);

            if (result.isEmpty()) {
                log.error("::INFO:: AmenityService.java -> findParkDataByAmenityData / 검색 조건에 해당하는 주차장 데이터가 존재하지 않습니다");
                return null;
            }

        } catch (Exception e) {
            log.error("::ERROR:: AmenityService.java -> findParkDataByAmenityData");
        }
        return result;
    }

    private void ParkEntityToDto(List<ParkDto> result, List<Amenity> amenityList) {
        for (Amenity amenity : amenityList) {
            Optional<Park> opPark = parkService.findParkingLotByNo(amenity.getPark().getPrkplceNo());
            ParkDto parkDto = ParkMapper.mapper.parkEntityToDto(opPark.get());
            result.add(parkDto);

        }
    }

    // findParkDataByAmenityData() -
    // Data에서 True로 표시된 주차장의 편의시설 List를 findAmenityList에 JSQL 형식으로 추가
    private void checkAmenity(AmenityRequestDto data, List<String> findAmenityList) {
        if (data.isElevator()) findAmenityList.add("a.elevator");
        if (data.isWideExit()) findAmenityList.add("a.wideExit");
        if (data.isRamp()) findAmenityList.add("a.ramp");
        if (data.isAccessRoads()) findAmenityList.add("a.accessRoads");
        if (data.isWheelchairLift()) findAmenityList.add("a.wheelchairLift");
        if (data.isBrailleBlock()) findAmenityList.add("a.brailleBlock");
        if (data.isExGuidance()) findAmenityList.add("a.exGuidance");
        if (data.isExTicketOffice()) findAmenityList.add("a.exTicketOffice");
    }

    // findParkDataByAmenityData() -
    // findAmenityList에 포함된 편의시설이 포함된 주차장 데이터를 찾도록 SELECT 구문 생성 후
    // 그 중에 근처에 있는 주차장만 추줄하도록 findParkingLotByLoc()에 return된 주차장 데이터만 IN 구문으로 검색하는 jsql 구문 생성
    private String createJsqlToFindParkingLotsByAmenityList(AmenityRequestDto data, List<String> findAmenityList) {
        String query = " ";
        for (String amenity : findAmenityList) {
            query += (amenity + "=1 AND ");
        }
        query += "a.park IN";

        List<ParkDto> parkList = parkService.findParkingLotByLoc(data.getLatitude(), data.getLongitude());
        query += "(";
        for (int i = 0; i < parkList.size(); i++) {
            if (i == parkList.size() - 1)
                query += ("'" + parkList.get(i).getPrkplceNo()+"'");
            else
                query += ("'" + parkList.get(i).getPrkplceNo() + "',");
        }
        query += ")";
        String jsql = "SELECT a FROM Amenity a WHERE" + query;
        return jsql;
    }

    // Amenity 더미데이터 넣기
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
            log.error("::ERROR:: AmenityService.java -> injectDummyData");
        }
    }

    public AmenityResponseDto findAmenityDataByPrkplceNo(String prkplceNo) {
        Optional<Park> opPark = parkService.findParkingLotByNo(prkplceNo);
        if (opPark.isEmpty()) {
            log.error("::INFO:: AmenityService.java -> findAmenityDataByPrkplceNo / 존재하지 않는 prkplceNo");
            return null;
        }
        Optional<Amenity> opAmenity = amenityRepository.findByPark(opPark.get());
        if (opAmenity.isEmpty()) {
            log.error("::INFO:: AmenityService.java -> findAmenityDataByPrkplceNo / 존재하지 않는 Amenity 데이터");
            return null;
        }
        return opAmenity.get().toResponseDto();
    }
}
