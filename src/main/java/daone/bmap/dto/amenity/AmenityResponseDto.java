package daone.bmap.dto.amenity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AmenityResponseDto {
    private boolean elevator;   // 엘리베이터
    private boolean wideExit;   //넓은 출입구
    private boolean ramp;   // 경사로
    private boolean accessRoads;    // 접근로
    private boolean wheelchairLift;    // 휠체어 리프트
    private boolean brailleBlock;    // 점자 블록
    private boolean exGuidance;    // 시각장애인 유도 안내
    private boolean exTicketOffice;    // 장애인 전용 매표소
    private boolean exRestroom;   // 장애인 전용 화장실

}
