package daone.bmap.domain.report;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ReportType {
    ERROR, PARKING;

    @JsonCreator
    public static ReportType from(String s){
        return ReportType.valueOf(s.toUpperCase());
    }

    public static ReportType checkTypeCode(int code){
        switch (code){
            case 0:
                return ReportType.ERROR;
            case 1:
                return ReportType.PARKING;
            default:
                return null;
        }
    }
}
