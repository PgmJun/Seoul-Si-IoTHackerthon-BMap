package daone.bmap.dto.park;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkAddrSearchDto {
    String address;
    String lat;
    String lng;
}
