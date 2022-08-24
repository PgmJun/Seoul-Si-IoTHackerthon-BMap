package daone.bmap.domain.report;

import daone.bmap.domain.park.Park;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Table(name = "REPORT")
@Entity
public class Report {

    @Id
    @GeneratedValue
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

}