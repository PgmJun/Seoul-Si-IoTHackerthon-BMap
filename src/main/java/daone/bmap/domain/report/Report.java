package daone.bmap.domain.report;

import daone.bmap.domain.park.Park;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reportId")
    private Long reportId;

    @ManyToOne(fetch = LAZY)
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

    @Builder
    public Report(Park park, ReportType reportType, String reportTitle, String reportText, String reportCarNm) {
        this.park = park;
        this.reportType = reportType;
        this.reportTitle = reportTitle;
        this.reportText = reportText;
        this.reportCarNm = reportCarNm;
    }
}
