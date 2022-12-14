package daone.bmap.dto.amenity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
public class AmenityRequestDto {
    private boolean elevator;   // 엘리베이터
    private boolean wideExit;   //넓은 출입구
    private boolean ramp;   // 경사로
    private boolean accessRoads;    // 접근로
    private boolean wheelchairLift;    // 휠체어 리프트
    private boolean brailleBlock;    // 점자 블록
    private boolean exGuidance;    // 시각장애인 유도 안내
    private boolean exTicketOffice;    // 장애인 전용 매표소
    private boolean wideRestroom;   // 장애인 전용 화장실
    private Double longitude;
    private Double latitude;

    public Map<String, Boolean> toMap(){
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap result = objectMapper.convertValue(this, HashMap.class);
        result.remove("longitude");
        result.remove("latitude");
        return result;
    }
}
