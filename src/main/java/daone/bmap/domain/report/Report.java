package daone.bmap.domain.report;


import static jakarta.persistence.FetchType.LAZY;

import daone.bmap.domain.park.Park;
import daone.bmap.dto.report.ReportRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reportId")
    private Long reportId;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "prkplceNo")
    private Park park;

    @Column(name = "reportType")
    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @Column(name = "reportTitle")
    private String reportTitle;

    @Column(name = "reportText")
    private String reportText;

    @Column(name = "reportCarNm")
    private String reportCarNm;

    public static Report ofEntityAndDto(Park park, ReportRequestDto request) {
        return builder()
                .park(park)
                .reportType(request.getReportType())
                .reportTitle(request.getReportTitle())
                .reportText(request.getReportText())
                .reportCarNm(request.getReportCarNm())
                .build();
    }
}
