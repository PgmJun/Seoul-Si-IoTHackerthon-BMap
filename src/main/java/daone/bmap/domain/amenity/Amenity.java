package daone.bmap.domain.amenity;

import daone.bmap.domain.park.Park;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@Getter
@Table(name = "amenity")
@Entity
public class Amenity {

    @Id
    @GeneratedValue
    private Long amenityId;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "prkplceNo")
    private Park park;

    @Column(name = "elevator")
    private boolean elevator;   // 엘리베이터

    @Column(name = "wideExit")
    private boolean wideExit;
    @Column(name = "ramp")
    private boolean ramp;   // 경사로

    @Column(name = "accessRoads")
    private boolean accessRoads;    // 접근로

    @Column(name = "wheelchairLift")
    private boolean wheelchairLift;    // 휠체어 리프트

    @Column(name = "brailleBlock")
    private boolean brailleBlock;    // 점자 블록

    @Column(name = "exGuidance")
    private boolean exGuidance;    // 시각장애인 유도 안내

    @Column(name = "exTicketOffice")
    private boolean exTicketOffice;    // 장애인 전용 매표소

    @Column(name = "exRestroom")
    private boolean exRestroom;

    @Builder
    public Amenity(Park park, boolean elevator, boolean wideExit, boolean ramp, boolean accessRoads, boolean wheelchairLift, boolean brailleBlock, boolean exGuidance, boolean exTicketOffice, boolean exRestroom) {
        this.park = park;
        this.elevator = elevator;
        this.wideExit = wideExit;
        this.ramp = ramp;
        this.accessRoads = accessRoads;
        this.wheelchairLift = wheelchairLift;
        this.brailleBlock = brailleBlock;
        this.exGuidance = exGuidance;
        this.exTicketOffice = exTicketOffice;
        this.exRestroom = exRestroom;
    }
}
