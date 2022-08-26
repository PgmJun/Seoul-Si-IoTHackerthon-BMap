package daone.bmap.domain.park;

import daone.bmap.domain.report.Report;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@Table(name = "park")
@Entity
public class Park {

    //주차장ID
    @Id
    @Column(name = "prkplceNo")
    private String prkplceNo;

    //주차장명
    @Column(name = "prkplceNm")
    private String prkplceNm;

    //주차장구분
    @Column(name = "prkplceSe")
    private String prkplceSe;

    //주차장유형
    @Column(name = "prkplceType")
    private String prkplceType;

    //소재지도로명주소
    @Column(name = "rdnmadr")
    private String rdnmadr;

    //소재지지번주소
    @Column(name = "lnmadr")
    private String lnmadr;

    //주차구획수
    @Column(name = "prkcmprt")
    private String prkcmprt;

    //급지구분
    @Column(name = "feedingSe")
    private String feedingSe;

    //부제시행구분
    @Column(name = "enforceSe")
    private String enforceSe;

    //운영요일
    @Column(name = "operDay")
    private String operDay;

    //평일운영시작시각
    @Column(name = "weekdayOperOpenHhmm")
    private String weekdayOperOpenHhmm;

    //평일운영종료시각
    @Column(name = "weekdayOperCloseHhmm")
    private String weekdayOperCloseHhmm;

    //토요일운영시작시각
    @Column(name = "satOperOperOpenHhmm")
    private String satOperOperOpenHhmm;

    //토요일운영종료시각
    @Column(name = "satOperCloseHhmm")
    private String satOperCloseHhmm;

    //공휴일운영시작시각
    @Column(name = "holidayOperOpenHhmm")
    private String holidayOperOpenHhmm;

    //공휴일운영종료시각
    @Column(name = "holidayCloseOpenHhmm")
    private String holidayCloseOpenHhmm;

    //요금정보
    @Column(name = "parkingchrgeInfo")
    private String parkingchrgeInfo;

    //주차기본시간
    @Column(name = "basicTime")
    private String basicTime;

    //주차기본요금
    @Column(name = "basicCharge")
    private String basicCharge;

    //추가단위시간
    @Column(name = "addUnitTime")
    private String addUnitTime;

    //추가단위요금
    @Column(name = "addUnitCharge")
    private String addUnitCharge;

    //1일주차권요금적용시간
    @Column(name = "dayCmmtktAdjTime")
    private String dayCmmtktAdjTime;

    //1일주차권요금
    @Column(name = "dayCmmtkt")
    private String dayCmmtkt;

    //월정기권요금
    @Column(name = "monthCmmtkt")
    private String monthCmmtkt;

    //결제방법
    @Column(name = "metpay")
    private String metpay;

    //특기사항
    @Column(name = "spcmnt")
    private String spcmnt;

    //관리기관명
    @Column(name = "institutionNm")
    private String institutionNm;

    //전화번호
    @Column(name = "phoneNumber")
    private String phoneNumber;

    //위도
    @Column(name = "latitude")
    private String latitude;

    //경도
    @Column(name = "longitude")
    private String longitude;

    //데이터기준일자
    @Column(name = "referenceDate")
    private String referenceDate;

    //제공기관코드
    @Column(name = "insttCode")
    private String insttCode;

    //제공기관명
    @Column(name = "insttNm")
    private String insttNm;

    @OneToMany(mappedBy = "park")
    List<Report> reportList = new ArrayList<>();

    @Builder
    public Park(String prkplceNo, String prkplceNm, String prkplceSe, String prkplceType, String rdnmadr, String lnmadr, String prkcmprt, String feedingSe, String enforceSe, String operDay, String weekdayOperOpenHhmm, String weekdayOperCloseHhmm, String satOperOperOpenHhmm, String satOperCloseHhmm, String holidayOperOpenHhmm, String holidayCloseOpenHhmm, String parkingchrgeInfo, String basicTime, String basicCharge, String addUnitTime, String addUnitCharge, String dayCmmtktAdjTime, String dayCmmtkt, String monthCmmtkt, String metpay, String spcmnt, String institutionNm, String phoneNumber, String latitude, String longitude, String referenceDate, String insttCode, String insttNm) {
        this.prkplceNo = prkplceNo;
        this.prkplceNm = prkplceNm;
        this.prkplceSe = prkplceSe;
        this.prkplceType = prkplceType;
        this.rdnmadr = rdnmadr;
        this.lnmadr = lnmadr;
        this.prkcmprt = prkcmprt;
        this.feedingSe = feedingSe;
        this.enforceSe = enforceSe;
        this.operDay = operDay;
        this.weekdayOperOpenHhmm = weekdayOperOpenHhmm;
        this.weekdayOperCloseHhmm = weekdayOperCloseHhmm;
        this.satOperOperOpenHhmm = satOperOperOpenHhmm;
        this.satOperCloseHhmm = satOperCloseHhmm;
        this.holidayOperOpenHhmm = holidayOperOpenHhmm;
        this.holidayCloseOpenHhmm = holidayCloseOpenHhmm;
        this.parkingchrgeInfo = parkingchrgeInfo;
        this.basicTime = basicTime;
        this.basicCharge = basicCharge;
        this.addUnitTime = addUnitTime;
        this.addUnitCharge = addUnitCharge;
        this.dayCmmtktAdjTime = dayCmmtktAdjTime;
        this.dayCmmtkt = dayCmmtkt;
        this.monthCmmtkt = monthCmmtkt;
        this.metpay = metpay;
        this.spcmnt = spcmnt;
        this.institutionNm = institutionNm;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.referenceDate = referenceDate;
        this.insttCode = insttCode;
        this.insttNm = insttNm;
    }
}
