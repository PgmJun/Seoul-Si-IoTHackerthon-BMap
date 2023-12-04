package daone.bmap.domain.amenity;

import daone.bmap.domain.park.Park;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
    Optional<Amenity> findByPark(Park park);
}
