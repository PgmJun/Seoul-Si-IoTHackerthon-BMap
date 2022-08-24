package daone.bmap.domain.report;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ReportType {
    ERROR, PARKING;

    @JsonCreator
    public static ReportType from(String s){
        return ReportType.valueOf(s.toUpperCase());
    }
}
