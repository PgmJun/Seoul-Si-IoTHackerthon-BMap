package daone.bmap.dto.park;

import daone.bmap.domain.park.Park;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ParkDto {

    public ParkDto() {
    }

    private String prkplceNo;
    private String prkplceNm;
    private String prkplceSe;
    private String prkplceType;
    private String rdnmadr;
    private String lnmadr;
    private String prkcmprt;
    private String feedingSe;
    private String enforceSe;
    private String operDay;
    private String weekdayOperOpenHhmm;
    private String weekdayOperCloseHhmm;
    private String satOperOperOpenHhmm;
    private String satOperCloseHhmm;
    private String holidayOperOpenHhmm;
    private String holidayCloseOpenHhmm;
    private String parkingchrgeInfo;
    private String basicTime;
    private String basicCharge;
    private String addUnitTime;
    private String addUnitCharge;
    private String dayCmmtktAdjTime;
    private String dayCmmtkt;
    private String monthCmmtkt;
    private String metpay;
    private String spcmnt;
    private String institutionNm;
    private String phoneNumber;
    private String latitude;
    private String longitude;
    private String referenceDate;
    private String insttCode;
    private String insttNm;
}
