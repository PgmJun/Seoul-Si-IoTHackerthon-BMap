package daone.bmap.domain.amenity;

import static jakarta.persistence.FetchType.LAZY;

import daone.bmap.domain.park.Park;
import daone.bmap.dto.amenity.AmenityResponseDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long amenityId;

    @OneToOne(fetch = LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "prkplceNo")
    private Park park;

    @Column(name = "elevator")
    private Boolean hasElevator;   // 엘리베이터

    @Column(name = "wideExit")
    private Boolean hasWideExit;

    @Column(name = "ramp")
    private Boolean hasRamp;   // 경사로

    @Column(name = "accessRoads")
    private Boolean hasAccessRoads;    // 접근로

    @Column(name = "wheelchairLift")
    private Boolean hasWheelchairLift;    // 휠체어 리프트

    @Column(name = "brailleBlock")
    private Boolean hasBrailleBlock;    // 점자 블록

    @Column(name = "exGuidance")
    private Boolean hasExGuidance;    // 시각장애인 유도 안내

    @Column(name = "exTicketOffice")
    private Boolean hasExTicketOffice;    // 장애인 전용 매표소

    @Column(name = "exRestroom")
    private Boolean hasExRestroom;

    public AmenityResponseDto toResponseDto() {
        return new AmenityResponseDto(hasElevator, hasWideExit, hasRamp, hasAccessRoads, hasWheelchairLift,
                hasBrailleBlock, hasExGuidance, hasExTicketOffice, hasExRestroom);
    }

    public static Amenity of(Park park, boolean hasElevator, boolean hasWideExit, boolean hasRamp,
                             boolean hasAccessRoads, boolean hasWheelchairLift, boolean hasBrailleBlock, boolean hasExGuidance, boolean hasExTicketOffice, boolean hasExRestroom) {
        return builder()
                .park(park)
                .hasElevator(hasElevator)
                .hasWideExit(hasWideExit)
                .hasRamp(hasRamp)
                .hasAccessRoads(hasAccessRoads)
                .hasWheelchairLift(hasWheelchairLift)
                .hasBrailleBlock(hasBrailleBlock)
                .hasExGuidance(hasExGuidance)
                .hasExTicketOffice(hasExTicketOffice)
                .hasExRestroom(hasExRestroom)
                .build();
    }
}
