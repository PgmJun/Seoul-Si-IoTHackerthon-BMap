package daone.bmap.domain.amenity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {

    List<Amenity> findByElevatorAndRampAndAccessRoadsAndWheelchairLiftAndBrailleBlockAndExGuidanceAndExTicketOffice(boolean elevator, boolean ramp, boolean accessRoads, boolean wheelchairLift, boolean brailleBlock, boolean exGuidance, boolean exTicketOffice);
}
