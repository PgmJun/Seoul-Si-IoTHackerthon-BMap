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

            String where = " ";
            for (int i = 0; i < ExistAmenityList.size(); i++) {
                where += (ExistAmenityList.get(i) + "=1 AND ");
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

            List<Amenity> amenityList = em.createQuery(jsql).getResultList();
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
        r.setSeed(20220826);
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
                .orElseThrow(()-> new NoSuchElementException("Amenity with prkplceNo:" + prkplceNo + " does not exist"))
                .toResponseDto();
    }
}
