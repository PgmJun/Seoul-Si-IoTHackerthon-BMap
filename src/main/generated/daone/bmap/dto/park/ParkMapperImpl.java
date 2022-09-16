package daone.bmap.dto.park;

import daone.bmap.domain.park.Park;
import daone.bmap.domain.park.Park.ParkBuilder;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-13T20:25:23+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16.1 (Oracle Corporation)"
)
public class ParkMapperImpl implements ParkMapper {

    @Override
    public Park parkDtoToEntity(ParkDto parkDto) {
        if ( parkDto == null ) {
            return null;
        }

        ParkBuilder park = Park.builder();

        park.prkplceNo( parkDto.getPrkplceNo() );
        park.prkplceNm( parkDto.getPrkplceNm() );
        park.prkplceSe( parkDto.getPrkplceSe() );
        park.prkplceType( parkDto.getPrkplceType() );
        park.rdnmadr( parkDto.getRdnmadr() );
        park.lnmadr( parkDto.getLnmadr() );
        park.prkcmprt( parkDto.getPrkcmprt() );
        park.feedingSe( parkDto.getFeedingSe() );
        park.enforceSe( parkDto.getEnforceSe() );
        park.operDay( parkDto.getOperDay() );
        park.weekdayOperOpenHhmm( parkDto.getWeekdayOperOpenHhmm() );
        park.weekdayOperCloseHhmm( parkDto.getWeekdayOperCloseHhmm() );
        park.satOperOperOpenHhmm( parkDto.getSatOperOperOpenHhmm() );
        park.satOperCloseHhmm( parkDto.getSatOperCloseHhmm() );
        park.holidayOperOpenHhmm( parkDto.getHolidayOperOpenHhmm() );
        park.holidayCloseOpenHhmm( parkDto.getHolidayCloseOpenHhmm() );
        park.parkingchrgeInfo( parkDto.getParkingchrgeInfo() );
        park.basicTime( parkDto.getBasicTime() );
        park.basicCharge( parkDto.getBasicCharge() );
        park.dayCmmtktAdjTime( parkDto.getDayCmmtktAdjTime() );
        park.dayCmmtkt( parkDto.getDayCmmtkt() );
        park.monthCmmtkt( parkDto.getMonthCmmtkt() );
        park.metpay( parkDto.getMetpay() );
        park.spcmnt( parkDto.getSpcmnt() );
        park.institutionNm( parkDto.getInstitutionNm() );
        park.phoneNumber( parkDto.getPhoneNumber() );
        park.latitude( parkDto.getLatitude() );
        park.longitude( parkDto.getLongitude() );
        park.referenceDate( parkDto.getReferenceDate() );
        park.insttCode( parkDto.getInsttCode() );
        park.insttNm( parkDto.getInsttNm() );

        return park.build();
    }

    @Override
    public ParkDto parkEntityToDto(Park park) {
        if ( park == null ) {
            return null;
        }

        ParkDto parkDto = new ParkDto();

        parkDto.setPrkplceNo( park.getPrkplceNo() );
        parkDto.setPrkplceNm( park.getPrkplceNm() );
        parkDto.setPrkplceSe( park.getPrkplceSe() );
        parkDto.setPrkplceType( park.getPrkplceType() );
        parkDto.setRdnmadr( park.getRdnmadr() );
        parkDto.setLnmadr( park.getLnmadr() );
        parkDto.setPrkcmprt( park.getPrkcmprt() );
        parkDto.setFeedingSe( park.getFeedingSe() );
        parkDto.setEnforceSe( park.getEnforceSe() );
        parkDto.setOperDay( park.getOperDay() );
        parkDto.setWeekdayOperOpenHhmm( park.getWeekdayOperOpenHhmm() );
        parkDto.setWeekdayOperCloseHhmm( park.getWeekdayOperCloseHhmm() );
        parkDto.setSatOperOperOpenHhmm( park.getSatOperOperOpenHhmm() );
        parkDto.setSatOperCloseHhmm( park.getSatOperCloseHhmm() );
        parkDto.setHolidayOperOpenHhmm( park.getHolidayOperOpenHhmm() );
        parkDto.setHolidayCloseOpenHhmm( park.getHolidayCloseOpenHhmm() );
        parkDto.setParkingchrgeInfo( park.getParkingchrgeInfo() );
        parkDto.setBasicTime( park.getBasicTime() );
        parkDto.setBasicCharge( park.getBasicCharge() );
        parkDto.setAddUnitTime( park.getAddUnitTime() );
        parkDto.setAddUnitCharge( park.getAddUnitCharge() );
        parkDto.setDayCmmtktAdjTime( park.getDayCmmtktAdjTime() );
        parkDto.setDayCmmtkt( park.getDayCmmtkt() );
        parkDto.setMonthCmmtkt( park.getMonthCmmtkt() );
        parkDto.setMetpay( park.getMetpay() );
        parkDto.setSpcmnt( park.getSpcmnt() );
        parkDto.setInstitutionNm( park.getInstitutionNm() );
        parkDto.setPhoneNumber( park.getPhoneNumber() );
        parkDto.setLatitude( park.getLatitude() );
        parkDto.setLongitude( park.getLongitude() );
        parkDto.setReferenceDate( park.getReferenceDate() );
        parkDto.setInsttCode( park.getInsttCode() );
        parkDto.setInsttNm( park.getInsttNm() );

        return parkDto;
    }
}
