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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
            // 요청받은 장애인 편의시설을 가진 주차장의 편의시설 데이터 불러오기
            List<Amenity> amenityList = amenityRepository.findByElevatorAndRampAndAccessRoadsAndWheelchairLiftAndBrailleBlockAndExGuidanceAndExTicketOffice(data.isElevator(), data.isRamp(), data.isAccessRoads(), data.isWheelchairLift(), data.isBrailleBlock(), data.isExGuidance(), data.isExTicketOffice());

            // 편의시설 데이터를 이용해 parkDto 불러오기
            for (Amenity amenity : amenityList) {
                Park parkData = parkService.findParkingLotByNo(amenity.getPark().getPrkplceNo()).get();
                ParkDto parkDto = ParkMapper.mapper.parkEntityToDto(parkData);
                result.add(parkDto);
            }
        } catch (Exception e) {
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
            log.error("::ERROR:: AmenityService.java -> injectDummyData");
        }
    }

    public AmenityResponseDto findAmenityDataByPrkplceNo(String prkplceNo) {
        Optional<Park> opPark = parkService.findParkingLotByNo(prkplceNo);
        if (opPark.isEmpty()) {
            log.error("::INFO:: ReportService.java -> findAmenityDataByPrkplceNo / 존재하지 않는 prkplceNo");
            return null;
        }
        Optional<Amenity> opAmenity = amenityRepository.findByPark(opPark.get());
        if(opAmenity.isEmpty()){
            log.error("::INFO:: ReportService.java -> findAmenityDataByPrkplceNo / 존재하지 않는 Amenity 데이터");
            return null;
        }
        return opAmenity.get().toResponseDto();
    }
}
