package daone.bmap.dto.park;

import daone.bmap.domain.park.Park;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class ParkDto {

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

    public static ParkDto fromEntity(Park park) {
        return builder()
                .prkplceNo(park.getPrkplceNo())
                .prkplceNm(park.getPrkplceNm())
                .prkplceSe(park.getPrkplceSe())
                .prkplceType(park.getPrkplceType())
                .rdnmadr(park.getRdnmadr())
                .lnmadr(park.getLnmadr())
                .prkcmprt(park.getPrkcmprt())
                .feedingSe(park.getFeedingSe())
                .enforceSe(park.getEnforceSe())
                .operDay(park.getOperDay())
                .weekdayOperOpenHhmm(park.getWeekdayOperOpenHhmm())
                .weekdayOperCloseHhmm(park.getWeekdayOperCloseHhmm())
                .satOperOperOpenHhmm(park.getSatOperOperOpenHhmm())
                .satOperCloseHhmm(park.getSatOperCloseHhmm())
                .holidayOperOpenHhmm(park.getHolidayOperOpenHhmm())
                .holidayCloseOpenHhmm(park.getHolidayCloseOpenHhmm())
                .parkingchrgeInfo(park.getParkingchrgeInfo())
                .basicTime(park.getBasicTime())
                .basicCharge(park.getBasicCharge())
                .addUnitCharge(park.getAddUnitCharge())
                .dayCmmtktAdjTime(park.getDayCmmtktAdjTime())
                .dayCmmtkt(park.getDayCmmtkt())
                .monthCmmtkt(park.getMonthCmmtkt())
                .metpay(park.getMetpay())
                .spcmnt(park.getSpcmnt())
                .institutionNm(park.getInstitutionNm())
                .phoneNumber(park.getPhoneNumber())
                .latitude(park.getLatitude().toString())
                .longitude(park.getLongitude().toString())
                .referenceDate(park.getReferenceDate())
                .insttCode(park.getInsttCode())
                .insttNm(park.getInsttNm())
                .build();
    }
}
